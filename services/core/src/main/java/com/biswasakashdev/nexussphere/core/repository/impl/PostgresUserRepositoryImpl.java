package com.biswasakashdev.nexussphere.core.repository.impl;

import com.biswasakashdev.nexussphere.common.exceptions.DataSourceOperationFailedException;
import com.biswasakashdev.nexussphere.core.models.Users;
import com.biswasakashdev.nexussphere.core.repository.UsersRepository;
import com.biswasakashdev.nexussphere.core.repository.r2dbc.UsersReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class PostgresUserRepositoryImpl implements UsersRepository {

    private final DatabaseClient databaseClient;
    private final UsersReactiveRepository usersReactiveRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Users> saveUser(Users users) {
        String id = UUID.randomUUID().toString();
        users.setId(id);
        return r2dbcEntityTemplate
                .insert(Users.class)
                .using(users);
/*
        return databaseClient
                .sql("""
                        INSERT INTO nex_users(id, email, password, first_name, last_name, gender, created_on, account_locked)
                        VALUES(:id, :email, :password, :first_name, :last_name, :gender, :created_on, :account_locked)
                        """).bind("id", id)
                .bind("email", users.getEmail())
                .bind("password", users.getPassword())
                .bind("first_name", users.getFirstName())
                .bind("last_name", users.getLastName())
                .bind("gender", users.getGender())
                .bind("created_on", users.getCreatedOn())
                .bind("account_locked", users.getAccountLocked())
                .fetch()
                .rowsUpdated()
                .flatMap((count) -> {
                    if (count == 0) {
                        String msg = String.format("Failed to add user with email %s", users.getEmail());
                        return Mono.error(new DataSourceOperationFailedException(msg));
                    }
                    users.setId(id);
                    return Mono.just(users);
                });*/

    }


    @Override
    public Mono<Users> findByEmail(String email) {
        return usersReactiveRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Mono<Users> findById(String userId) {
        return usersReactiveRepository.findById(userId);
    }

    @Override
    public Mono<Boolean> isUserExistsByEmail(String email) {
        return usersReactiveRepository.existsByEmailIgnoreCase(email);
    }


/*
    private static <T> Update getUpdates(String parent, T updates, boolean ignoreNull) {
        BeanWrapper profileUpdates = new BeanWrapperImpl(updates);
        Update update = new Update();

        for (PropertyDescriptor fields : profileUpdates.getPropertyDescriptors()) {
            String fieldName = fields.getName();
            if ("class".equals(fieldName)) continue;

            Object fieldValue = profileUpdates.getPropertyValue(fieldName);
            if (ignoreNull && fieldValue == null) continue;
            String dbFieldName = parent != null ? parent + "." + fieldName : fieldName;

            update.set(dbFieldName, fieldValue);
        }
        return update;
    }

    */


}
