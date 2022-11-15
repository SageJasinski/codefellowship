package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repositories.UserInterface;

@Service
public class SiteUserDetailServiceImpl implements UserDetailsService{

        @Autowired
        UserInterface userInterface;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // TODO: return a SiteUser -> optional csat to UserDetails
            return (UserDetails) userInterface.findUserByUserName(username);
        }
}
