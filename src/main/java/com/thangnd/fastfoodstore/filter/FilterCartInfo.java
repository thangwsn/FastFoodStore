package com.thangnd.fastfoodstore.filter;

import com.thangnd.fastfoodstore.entity.order.Cart;
import com.thangnd.fastfoodstore.entity.user.Customer;
import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Component
@Order(1)
@WebFilter(filterName = "FilterCartInfo", urlPatterns = {"/*",""})
public class FilterCartInfo implements Filter {
    @Autowired
    private CartService cartService;

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) session.getAttribute("user");
        if ( user != null && user.getRole().name().equals("Customer")) {
            Cart cart = cartService.updateCartFilter((Customer) user);
            ((HttpServletRequest) request).setAttribute("cartTotal", cart.getTotal());
        }
        chain.doFilter(request, response);
    }
}
