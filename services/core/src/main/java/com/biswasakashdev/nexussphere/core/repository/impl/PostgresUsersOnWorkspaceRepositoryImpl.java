package com.biswasakashdev.nexussphere.core.repository.impl;

import com.biswasakashdev.nexussphere.common.exceptions.DataSourceOperationFailedException;
import com.biswasakashdev.nexussphere.common.response.Page;
import com.biswasakashdev.nexussphere.core.dtos.response.UsersOnWorkspaceDTO;
import com.biswasakashdev.nexussphere.core.dtos.response.WorkspaceResponse;
import com.biswasakashdev.nexussphere.core.models.UsersOnWorkspace;
import com.biswasakashdev.nexussphere.core.repository.UsersOnWorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostgresUsersOnWorkspaceRepositoryImpl implements UsersOnWorkspaceRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Mono<UsersOnWorkspace> save(UsersOnWorkspace usersOnWorkspace) {
        return databaseClient
                .sql("""
                           INSERT INTO users_on_workspaces(user_id, workspace_id, write_permission, last_active, joined_on)
                                                   VALUES (:user_id, :workspace_id, :write_permission , :last_active , :joined_on)
                        """)
                .bind("user_id", usersOnWorkspace.getUserId())
                .bind("workspace_id", usersOnWorkspace.getWorkspaceId())
                .bind("write_permission", usersOnWorkspace.getWritePermission())
                .bind("joined_on", LocalDate.now())
                .bind("last_active", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .flatMap((count) -> {
                    if (count == 0) {
                        String msg = String.format("Failed to add user %s to the workspace %s", usersOnWorkspace.getUserId(), usersOnWorkspace.getWorkspaceId());
                        return Mono.error(new DataSourceOperationFailedException(msg));
                    }
                    return Mono.just(usersOnWorkspace);
                });
    }


    @Override
    public Mono<Page<UsersOnWorkspaceDTO>> findAllUsersByWorkspaceId(
            String workspaceId,
            Page.PageDetails pageDetails
    ) {

        int requiredPage = pageDetails.page();
        int pageSize = pageDetails.size();

        Mono<Long> userCountMono = databaseClient
                .sql("""
                        SELECT COUNT(*) FROM users_on_workspaces WHERE workspace_id = :workspace_id
                        """)
                .bind("workspace_id", workspaceId)
                .map((row) -> Objects.requireNonNull(row.get(0, Long.class)))
                .one();


        return userCountMono.flatMap((count) -> {

            int page = Page.getRequiredPage(requiredPage, pageSize, count);

            int offSet = (page - 1) * pageSize;


            Mono<List<UsersOnWorkspaceDTO>> contentMono = databaseClient
                    .sql(""" 
                            SELECT
                                uw.user_id AS user_id,
                                u.first_name AS first_name,
                                u.last_name AS last_name,
                                uw.write_permission AS write_permission,
                                uw.last_active AS last_active
                            FROM users_on_workspaces uw JOIN nex_users u ON uw.user_id = u.id
                            WHERE uw.workspace_id = :workspace_id
                            LIMIT :page_size OFFSET :off_set
                            """)
                    .bind("workspace_id", workspaceId)
                    .bind("page_size", pageSize)
                    .bind("off_set", offSet)
                    .map((row, metadata) -> new UsersOnWorkspaceDTO(
                            row.get("user_id", String.class),
                            row.get("first_name", String.class),
                            row.get("last_name", String.class),
                            row.get("write_permission", Boolean.class),
                            row.get("last_active", LocalDateTime.class)
                    ))
                    .all()
                    .collectList();

            return contentMono.map(userList -> {
                int pageCount = (int) Math.ceil((double) count / pageSize);
                return new Page<>(
                        page,
                        pageSize,
                        pageCount,
                        count,
                        userList
                );
            });
        });
    }

    @Override
    public Mono<Page<WorkspaceResponse>> findAllWorkspacesByUserId(
            String userId,
            Page.PageDetails pageDetails
    ) {
        Mono<Long> workspaceCountMono = databaseClient
                .sql("SELECT COUNT(*) FROM users_on_workspaces WHERE user_id = :userId")
                .bind("userId", userId)
                .map((row) -> Objects.requireNonNull(row.get(0, Long.class)))
                .one();

        int requiredPage = pageDetails.page();
        int pageSize = pageDetails.size();

        return workspaceCountMono.flatMap((count) -> {

            int page = Page.getRequiredPage(requiredPage, pageSize, count);

            int offSet = (page - 1) * pageSize;

            Mono<List<WorkspaceResponse>> workspaceIdListMono = databaseClient
                    .sql("""
                            SELECT
                                w.id AS workspace_id,
                                w.workspace_name AS workspace_name,
                                w.owner_id AS owner_id,
                                uw.last_active AS last_active,
                                uw.joined_on AS joined_on,
                                (SELECT COUNT(*) FROM users_on_workspaces WHERE workspace_id = w.id) AS user_count,
                                (SELECT COUNT(*) FROM pages WHERE workspace_id = w.id) AS page_count
                            FROM workspaces w JOIN users_on_workspaces uw ON uw.workspace_id = w.id
                            WHERE uw.user_id = :user_id
                            LIMIT :page_size OFFSET :off_set
                            """
                    )
                    .bind("user_id", userId)
                    .bind("page_size", pageSize)
                    .bind("off_set", offSet)
                    .map((row) -> WorkspaceResponse.builder()
                            .id(row.get("workspace_id", String.class))
                            .name(row.get("workspace_name", String.class))
                            .ownedBy(row.get("owner_id", String.class))
                            .lastActive(row.get("last_active", LocalDateTime.class))
                            .joinedOn(row.get("joined_on", LocalDate.class))
                            .userCount(row.get("user_count", Long.class))
                            .groupCount(row.get("page_count", Long.class))
                            .build())
                    .all()
                    .collectList();

            return workspaceIdListMono.map(workspaceList -> {
                int pageCount = (int) Math.ceil((double) count / pageSize);
                return new Page<>(
                        page,
                        pageSize,
                        pageCount,
                        count,
                        workspaceList
                );
            });
        });
    }


}
