-- encoded pass: #@123senhaAleatoriaDoDesafioDeEnquetesVcNaoDescobrirahahaha#@123
-- the prefix {bcrypt} is used like recomended by doc here (https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#pe-dpe)
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity)
  VALUES ('446877048f35dcdf88ddfec890b01bd1', '{bcrypt}$2a$10$Iz0GiTlbj4AsQEGgMhBiP.CJj6/mDCTGcwQvcKXY6OMeY.AtFnb2q', 'read,write', 'password,refresh_token,client_credentials', 'ROLE_CLIENT', 300);

-- pass: passdofill@123 
INSERT INTO users (username, password, enabled) 
	VALUES ('fill', '{bcrypt}$2a$10$MK735PDLbSEweA9YFp0wl.mMNPs/dmzn.F/eQsPccqBgZMBq.ICvi', true);
INSERT INTO authorities (username, authority) VALUES ('fill', 'ROLE_USER');