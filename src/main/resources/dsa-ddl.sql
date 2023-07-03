DROP TABLE IF EXISTS app_user;
DROP SEQUENCE IF EXISTS users_sequence;
CREATE SEQUENCE users_sequence START 1;
CREATE TABLE app_user (
                          id BIGINT DEFAULT nextval('users_sequence') NOT NULL,
                          version INTEGER NOT NULL DEFAULT 0,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE,
                          username VARCHAR(255) UNIQUE,
                          password VARCHAR(255),
                          mobile VARCHAR(255) UNIQUE,
                          email_verified BOOLEAN DEFAULT false,
                          mobile_verified BOOLEAN DEFAULT false,
                          profile_picture VARCHAR(255),
                          oauth_provider VARCHAR(255) DEFAULT 'system',
                          password_applicable BOOLEAN DEFAULT true,
                          created_at TIMESTAMP DEFAULT LOCALTIMESTAMP,
                          updated_at TIMESTAMP DEFAULT LOCALTIMESTAMP,
                          assigned_role VARCHAR(255) DEFAULT 'GUEST',
                          role VARCHAR(255) DEFAULT 'GUEST',
                          locked BOOLEAN DEFAULT false,
                          enabled BOOLEAN DEFAULT true
);