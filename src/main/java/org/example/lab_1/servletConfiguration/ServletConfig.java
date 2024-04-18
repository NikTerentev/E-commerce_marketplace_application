package org.example.lab_1.servletConfiguration;

import org.example.lab_1.servlet.*;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {
    @Bean
    public ServletRegistrationBean loginServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new LoginServlet(), "/user-login");
        return bean;
    }

    @Bean
    public ServletRegistrationBean logoutServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new LogoutServlet(), "/log-out");
        return bean;
    }


    @Bean
    public ServletRegistrationBean cartServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new CartServlet(), "/add-to-cart/*");
        return bean;
    }

    @Bean
    public ServletRegistrationBean quantityServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new QuantityIncDecServlet(), "/quantity-inc-dec/*"
        );
        return bean;
    }

    @Bean
    public ServletRegistrationBean orderNowServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new OrderNowServlet(), "/order-now/*"
        );
        return bean;
    }

    @Bean
    public ServletRegistrationBean checkOutServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new CheckOutServlet(), "/cart-check-out"
        );
        return bean;
    }

    @Bean
    public ServletRegistrationBean removeFromCartServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new RemoveFromCartServlet(), "/remove-from-cart/*"
        );
        return bean;
    }

    @Bean
    public ServletRegistrationBean cancelOrderServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new CancelOrderServlet(), "/cancel-order/*"
        );
        return bean;
    }
}


