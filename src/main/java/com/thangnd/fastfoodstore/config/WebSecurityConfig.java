package com.thangnd.fastfoodstore.config;

import com.thangnd.fastfoodstore.entity.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //CSRF ( Cross Site Request Forgery) là kĩ thuật tấn công bằng cách sử dụng quyền chứng thực của người sử dụng đối với 1 website khác
        // Các trang không yêu cầu login như vậy ai cũng có thể vào được admin hay user hoặc guest có thể vào các trang
        http.authorizeRequests().antMatchers("/", "/login", "/register", "/food", "/food/{id}").permitAll();

        http.authorizeRequests()
                .antMatchers("/dashboard",
                        "/dashboard/*",
                        "/category", "/category/*",
                        "/customer/{id}")
                .hasAuthority(Role.Staff.name());

        http.authorizeRequests()
                .antMatchers("/cart", "cart/*",
                        "/order", "/order/checkout"
                )
                .hasAuthority(Role.Customer.name());

        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/processLogin")
                .loginPage("/login")
                .defaultSuccessUrl("/redirect")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                // Cấu hình cho Logout Page. Khi logout mình trả về trang
                .and().logout().logoutUrl("/processLogout").logoutSuccessUrl("/logout");

    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
