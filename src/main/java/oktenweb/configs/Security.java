package oktenweb.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter{

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    //securityPart5
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //securityPart6
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/saveClient", "/saveRestaurant",  "/clients" ,"/restaurants").permitAll() // everyone can access on url: "/", "/home"
                .anyRequest().authenticated() // other url need auth
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/admin/goToSecuredPage2").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/successURL")//handle with post mapping in controller
                .failureUrl("/login?error").permitAll()
                //.permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
                logoutSuccessUrl("/")
                .permitAll();
    }

    //securityPart7
    private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryConfigurer() {
        return new InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                AuthenticationProvider provider) throws Exception {
        inMemoryConfigurer()
                .withUser("admin")
                .password("{noop}admin")
                .authorities("ADMIN")
                .and()
                .configure(auth);
        auth.authenticationProvider(provider);

    }
}

