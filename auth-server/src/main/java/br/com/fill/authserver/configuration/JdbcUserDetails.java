package br.com.fill.authserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.fill.authserver.entity.Credentials;
import br.com.fill.authserver.repository.CredentialRepository;

public class JdbcUserDetails implements UserDetailsService {

	@Autowired
	private CredentialRepository credentialRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Credentials credentials = credentialRepository.findByName(username);

		if (credentials == null) {
			throw new UsernameNotFoundException("User" + username + "can not be found");
		}

		User user = new User(credentials.getName(), credentials.getPassword(), credentials.isEnabled(), true, true,
				true, credentials.getAuthorities());

		return user;
	}
	
}
