INSERT INTO authority  VALUES(1,'ROLE_OAUTH_ADMIN');
INSERT INTO authority VALUES(2,'ROLE_RESOURCE_ADMIN');
INSERT INTO credentials VALUES(1,b'1','oauth_admin','$2a$10$1EV5qQHiVGVIyh1O3zdP9.MLlTLXkuZHi0BpbN8IiSO9uu147PMPW','0');
INSERT INTO credentials VALUES(2,b'1','resource_admin','$2a$10$1EV5qQHiVGVIyh1O3zdP9.MLlTLXkuZHi0BpbN8IiSO9uu147PMPW','0');
INSERT INTO credentials_authorities VALUES (1,1);
INSERT INTO credentials_authorities VALUES (2,2);
INSERT INTO credentials_authorities VALUES (3,3);


INSERT INTO oauth_client_details VALUES('survey_client','survey_api', '$2a$10$1EV5qQHiVGVIyh1O3zdP9.MLlTLXkuZHi0BpbN8IiSO9uu147PMPW', 'read,write', 'client_credentials', 'http://127.0.0.1', 'ROLE_SURVEY_ADMIN', 7200, 0, NULL, 'true');
