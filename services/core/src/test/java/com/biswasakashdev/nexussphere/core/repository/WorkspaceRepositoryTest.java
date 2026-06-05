package com.biswasakashdev.nexussphere.core.repository;

import com.biswasakashdev.nexussphere.core.models.User;
import com.biswasakashdev.nexussphere.core.models.Workspaces;
import com.biswasakashdev.nexussphere.core.repository.impl.PostgresUserRepositoryImpl;
import com.biswasakashdev.nexussphere.core.repository.impl.PostgresWorkspaceRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.time.LocalDate;


@Import(value = {
        PostgresWorkspaceRepositoryImpl.class,
        PostgresUserRepositoryImpl.class
})
public class WorkspaceRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Workspaces workspaces;

    @BeforeEach
    void beforeEach() {
        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .firstName("John")
                .lastName("Smith")
                .gender("Male")
                .accountLocked(false)
                .createdOn(LocalDate.now())
                .build();

        usersRepository
                .saveUser(user)
                .doOnNext(savedUser -> {
                    this.workspaces = Workspaces
                            .builder()
                            .name("Test Workspace")
                            .ownedId(savedUser.getId())
                            .createdOn(LocalDate.now())
                            .build();
                })
                .block();
    }

    @Test
    void saveWorkspace() {
        workspaceRepository
                .save(workspaces)
                .flatMap((savedWorkspace) -> workspaceRepository.findById(savedWorkspace.getId()))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

}
