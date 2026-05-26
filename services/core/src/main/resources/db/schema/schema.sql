DROP TABLE IF EXISTS users_on_workspaces;
DROP TABLE IF EXISTS pages;
DROP TABLE IF EXISTS workspaces;
DROP TABLE IF EXISTS nex_users;

CREATE TABLE nex_users
(
    id             VARCHAR(50) PRIMARY KEY,
    email          VARCHAR(100) NOT NULL UNIQUE,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    password       VARCHAR(100) NOT NULL,
    gender         VARCHAR(20)  NOT NULL,
    account_locked BOOLEAN      NOT NULL,
    created_on     DATE         NOT NULL
);


CREATE TABLE workspaces
(
    id             VARCHAR(50) PRIMARY KEY,
    workspace_name VARCHAR(50)  NOT NULL,
    owner_id       VARCHAR(100) NOT NULL,
    created_on     DATE         NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES nex_users (id) ON DELETE CASCADE
);


CREATE TABLE pages
(
    id           VARCHAR(50) PRIMARY KEY,
    page_name    VARCHAR(50) NOT NULL,
    created_on   DATE        NOT NULL,
    workspace_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (workspace_id) REFERENCES workspaces (id)
);

CREATE TABLE users_on_workspaces
(
    user_id          VARCHAR(100) NOT NULL,
    workspace_id     VARCHAR(50)  NOT NULL,
    write_permission BOOLEAN      NOT NULL,
    joined_on        DATE         NOT NULL,
    last_active      TIMESTAMP    NOT NULL,
    PRIMARY KEY (user_id, workspace_id),
    FOREIGN KEY (user_id) REFERENCES nex_users (id),
    FOREIGN KEY (workspace_id) REFERENCES workspaces (id)
);