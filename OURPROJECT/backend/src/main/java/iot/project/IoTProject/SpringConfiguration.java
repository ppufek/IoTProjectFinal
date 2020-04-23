package iot.project.IoTProject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").hasAnyRole("admin")
                .anyRequest().authenticated()
                .and()
                //.formLogin().and()
                .httpBasic()
        .and()
        .cors();

        http.headers().frameOptions().and().cacheControl();
    }

//
//        @Autowired
//        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//            auth.inMemoryAuthentication()
//                    .withUser("root")
//                    .password(encoder.encode("root"))
//                    .roles("admin");
//        }




    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
