package blps.labs.config;

import blps.labs.security.jwt.AuthEntryPointJwt;
import blps.labs.security.jwt.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthEntryPointJwt unauthorizedEntryPoint;

    @Autowired
    public WebSecurityConfig(AuthEntryPointJwt unauthorizedHandler, UserDetailsService userDetailsService) {
        this.unauthorizedEntryPoint = unauthorizedHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf()
                .disable().
                authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/auth/**").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Модератор
                .antMatchers(HttpMethod.GET, "/review/approved/false").access("hasAuthority('READ_MODERATOR')")
                .antMatchers(HttpMethod.DELETE, "/review/**").access("hasAuthority('WRITE_MODERATOR')")
                .antMatchers(HttpMethod.PATCH, "/review/approval/**").access("hasAuthority('WRITE_MODERATOR')")
                //Доступ и для Модератора и для Юзера
                .antMatchers(HttpMethod.GET, "/review/authorName").access("hasAuthority('READ_USER')")
                .antMatchers(HttpMethod.POST, "/review/").access("hasAuthority('WRITE_USER')")
                .antMatchers(HttpMethod.GET, "/review/rejected").access("hasAuthority('READ_USER')")
                //Доступ разрешен всем пользователей
                .antMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico").permitAll()
                .antMatchers(HttpMethod.GET, "/review/approved/true").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}
