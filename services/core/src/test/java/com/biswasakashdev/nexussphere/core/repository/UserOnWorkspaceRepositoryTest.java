package com.biswasakashdev.nexussphere.core.repository;

import com.biswasakashdev.nexussphere.common.response.Page;
import com.biswasakashdev.nexussphere.core.models.User;
import com.biswasakashdev.nexussphere.core.models.UserOnWorkspace;
import com.biswasakashdev.nexussphere.core.models.Workspaces;
import com.biswasakashdev.nexussphere.core.repository.impl.PostgresUserRepositoryImpl;
import com.biswasakashdev.nexussphere.core.repository.impl.PostgresUsersOnWorkspaceRepositoryImpl;
import com.biswasakashdev.nexussphere.core.repository.impl.PostgresWorkspaceRepositoryImpl;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(value = {
        PostgresWorkspaceRepositoryImpl.class,
        PostgresUserRepositoryImpl.class,
        PostgresUsersOnWorkspaceRepositoryImpl.class
})
class UserOnWorkspaceRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UsersOnWorkspaceRepository usersOnWorkspaceRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private ConnectionFactory connectionFactory;


    @BeforeEach
    void setupDatabase() {
        populateDB();
    }



    /**
     * Tests the save operation of the UsersOnWorkspaceRepository
     */
    @Test
    void shouldSaveUsersOnWorkspace() {


        User user = User.builder()
                .email("jhonsmith@gmail.com")
                .password("password")
                .firstName("John")
                .lastName("Smith")
                .gender("Male")
                .accountLocked(false)
                .createdOn(LocalDate.now())
                .build();


        usersRepository
                .saveUser(user)
                .flatMap(savedUser -> {
                    Workspaces workspaces = Workspaces
                            .builder()
                            .name("Test Workspace")
                            .ownedId(savedUser.getId())
                            .createdOn(LocalDate.now())
                            .build();
                    return workspaceRepository
                            .save(workspaces)
                            .flatMap(savedWorkspace -> {
                                UserOnWorkspace userOnWorkspace =
                                        UserOnWorkspace
                                                .builder()
                                                .workspaceId(savedWorkspace.getId())
                                                .userId(savedUser.getId())
                                                .writePermission(true)
                                                .lastActive(LocalDateTime.now())
                                                .joinedOn(LocalDate.now())
                                                .build();

                                Page.PageDetails pageDetails = new Page.PageDetails(
                                        1,
                                        10,
                                        new HashMap<>()
                                );
                                return usersOnWorkspaceRepository
                                        .save(userOnWorkspace)
                                        .then(usersOnWorkspaceRepository.findAllUsersByWorkspaceId(savedWorkspace.getId(), pageDetails));
                            });
                })
                .as(StepVerifier::create)
                .consumeNextWith((page) -> {
                    assertEquals(1, page.totalElements());
                })
                .expectComplete()
                .verify();
    }

    /**
     * Tests the findAllWorkspacesByUserId operation of the UsersOnWorkspaceRepository
     */
    @Test
    void shouldFindAllTheWorkspacesUserMemberOf() {
        Page.PageDetails pageDetails = new Page.PageDetails(
                1,
                10,
                new HashMap<>()
        );
        usersOnWorkspaceRepository
                .findAllWorkspacesByUserId("usr_001", pageDetails)
                .as(StepVerifier::create)
                .consumeNextWith((page) -> {
                    assertEquals(2, page.totalElements());
                })
                .expectComplete()
                .verify();

        usersOnWorkspaceRepository
                .findAllWorkspacesByUserId("usr_004", pageDetails)
                .as(StepVerifier::create)
                .consumeNextWith((page) -> {
                    assertEquals(1, page.totalElements());
                })
                .expectComplete()
                .verify();

    }

    /**
     * Tests the findAllUsersByWorkspaceId operation of the UsersOnWorkspaceRepository
     */

    @Test
    void shouldFindAllTheUsersWithWorkspaceId() {
        Page.PageDetails pageDetails = new Page.PageDetails(
                1,
                10,
                new HashMap<>()
        );
        usersOnWorkspaceRepository
                .findAllUsersByWorkspaceId("wrk_001", pageDetails)
                .as(StepVerifier::create)
                .consumeNextWith((page) -> {
                    assertEquals(3, page.totalElements());
                })
                .expectComplete()
                .verify();
    }


}