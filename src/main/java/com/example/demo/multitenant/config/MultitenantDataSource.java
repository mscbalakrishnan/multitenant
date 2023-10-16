package com.example.demo.multitenant.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {

    @Override
    protected String determineCurrentLookupKey() {
        String currentTenant = TenantContext.getCurrentTenant();
        System.out.println("Current Tenant : " + currentTenant);
        return currentTenant;
    }

}
