package me.shreyeschekuru.dsa.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    private static final String LOGIN_URL = "/login";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//
//        List<RequestMatcher> requestMatchers = new ArrayList<>();
//        requestMatchers.add(new AntPathRequestMatcher("/images/**"));
//        requestMatchers.add(new AntPathRequestMatcher("/line-awesome/**"));
//        requestMatchers.add(new AntPathRequestMatcher("/register/**"));
//        requestMatchers.add(new AntPathRequestMatcher("/google/**"));
//        requestMatchers.add(new AntPathRequestMatcher("/oauth2/authorization/google"));
//        requestMatchers.add(new AntPathRequestMatcher("/oauth2/authorization/github"));
//
//        http.authorizeHttpRequests()
//                .requestMatchers(requestMatchers.toArray(new RequestMatcher[0])).permitAll()
//                .and()
//                .oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorization")
//                .and()
//                .defaultSuccessUrl("/student-events")
//                .failureUrl("/login?error")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout");
//
//        super.configure(http);
//
//      //  setLoginView(http, StudentEventsView.class);


        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll();

        // Icons from the line-awesome addon
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll();

        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
        super.configure(http);


    }

}
