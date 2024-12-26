INSERT INTO role (role_id, role_name) VALUES (1, 'admin') ON CONFLICT DO NOTHING;
INSERT INTO role (role_id, role_name) VALUES (2, 'employee') ON CONFLICT DO NOTHING;

INSERT INTO roles (role_id, role_name) VALUES (1, 'admin') ON CONFLICT DO NOTHING;
INSERT INTO roles (role_id, role_name) VALUES (2, 'employee') ON CONFLICT DO NOTHING;