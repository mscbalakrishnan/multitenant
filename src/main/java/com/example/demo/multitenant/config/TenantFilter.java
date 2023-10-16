package com.example.demo.multitenant.config;


import com.example.demo.multitenant.security.AuthenticationService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        /*String tenantName = req.getHeader("X-TenantID");
        System.out.println("Tenant Name: " + tenantName);
        TenantContext.setCurrentTenant(tenantName);*/
        String tenant = AuthenticationService.getTenant((HttpServletRequest) request);
        TenantContext.setCurrentTenant(tenant);

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }

    }
}
