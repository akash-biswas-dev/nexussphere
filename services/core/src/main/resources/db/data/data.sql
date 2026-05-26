-- =========================================
-- USERS
-- =========================================

INSERT INTO nex_users (
    id,
    email,
    first_name,
    last_name,
    password,
    gender,
    account_locked,
    created_on
)
VALUES
    (
        'usr_001',
        'john.doe@example.com',
        'John',
        'Doe',
        '$2a$10$hashedpassword1',
        'MALE',
        FALSE,
        '2026-01-10'
    ),
    (
        'usr_002',
        'jane.smith@example.com',
        'Jane',
        'Smith',
        '$2a$10$hashedpassword2',
        'FEMALE',
        FALSE,
        '2026-01-11'
    ),
    (
        'usr_003',
        'alice.wilson@example.com',
        'Alice',
        'Wilson',
        '$2a$10$hashedpassword3',
        'FEMALE',
        FALSE,
        '2026-01-12'
    ),
    (
        'usr_004',
        'bob.brown@example.com',
        'Bob',
        'Brown',
        '$2a$10$hashedpassword4',
        'MALE',
        TRUE,
        '2026-01-15'
    ),
    (
        'usr_005',
        'charlie.johnson@example.com',
        'Charlie',
        'Johnson',
        '$2a$10$hashedpassword5',
        'MALE',
        FALSE,
        '2026-01-16'
    );


-- =========================================
-- WORKSPACES
-- =========================================

INSERT INTO workspaces (
    id,
    workspace_name,
    owner_id,
    created_on
)
VALUES
    (
        'wrk_001',
        'Engineering Workspace',
        'usr_001',
        '2026-02-01'
    ),
    (
        'wrk_002',
        'Marketing Workspace',
        'usr_002',
        '2026-02-03'
    ),
    (
        'wrk_003',
        'Private Research',
        'usr_003',
        '2026-02-05'
    ),
    (
        'wrk_004',
        'DevOps Workspace',
        'usr_001',
        '2026-02-06'
    );


-- =========================================
-- PAGES
-- =========================================

INSERT INTO pages (
    id,
    page_name,
    created_on,
    workspace_id
)
VALUES
    (
        'pg_001',
        'System Design Notes',
        '2026-02-02',
        'wrk_001'
    ),
    (
        'pg_002',
        'API Documentation',
        '2026-02-02',
        'wrk_001'
    ),
    (
        'pg_003',
        'Marketing Campaign Q1',
        '2026-02-04',
        'wrk_002'
    ),
    (
        'pg_004',
        'SEO Checklist',
        '2026-02-04',
        'wrk_002'
    ),
    (
        'pg_005',
        'Research Ideas',
        '2026-02-06',
        'wrk_003'
    ),
    (
        'pg_006',
        'CI/CD Pipeline',
        '2026-02-07',
        'wrk_004'
    ),
    (
        'pg_007',
        'Kubernetes Notes',
        '2026-02-07',
        'wrk_004'
    );


-- =========================================
-- USERS ON WORKSPACES
-- =========================================

INSERT INTO users_on_workspaces (
    user_id,
    workspace_id,
    write_permission,
    joined_on,
    last_active
)
VALUES

    -- Workspace 1
    (
        'usr_001',
        'wrk_001',
        TRUE,
        '2026-02-01',
        '2026-05-20 10:15:00'
    ),
    (
        'usr_002',
        'wrk_001',
        TRUE,
        '2026-02-02',
        '2026-05-21 11:30:00'
    ),
    (
        'usr_003',
        'wrk_001',
        FALSE,
        '2026-02-03',
        '2026-05-19 08:45:00'
    ),

    -- Workspace 2
    (
        'usr_002',
        'wrk_002',
        TRUE,
        '2026-02-03',
        '2026-05-22 14:00:00'
    ),
    (
        'usr_005',
        'wrk_002',
        FALSE,
        '2026-02-04',
        '2026-05-20 16:10:00'
    ),

    -- Workspace 3
    (
        'usr_003',
        'wrk_003',
        TRUE,
        '2026-02-05',
        '2026-05-23 09:20:00'
    ),

    -- Workspace 4
    (
        'usr_001',
        'wrk_004',
        TRUE,
        '2026-02-06',
        '2026-05-23 18:40:00'
    ),
    (
        'usr_004',
        'wrk_004',
        TRUE,
        '2026-02-07',
        '2026-05-18 12:00:00'
    ),
    (
        'usr_005',
        'wrk_004',
        FALSE,
        '2026-02-08',
        '2026-05-21 13:25:00'
    );