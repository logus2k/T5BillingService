package ai.tech5.t5billing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
public class SecurityManager extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").denyAll()
                .antMatchers("/version").permitAll()
                .antMatchers("/transaction").authenticated()
                .antMatchers("/transaction").hasAuthority("SERVICE")
                .and()
                .x509()
                //.subjectPrincipalRegex("CN=(.*?),")
                //.subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                .userDetailsService(userDetailsServiceManager());
    }

    @Bean
    public UserDetailsService userDetailsServiceManager() {

        return username -> {

            if (username.equals("clienttech5billing.com")) {

                return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("SERVICE"));

            } else if (username.equals("John Doe")) {

                return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("SYSADMIN"));

            } else {

                throw new UsernameNotFoundException("User: " + username + " not found.");
            }

        };
    }
}
