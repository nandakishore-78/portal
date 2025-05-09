package org.example.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FinanceService {

    @Autowired
    private RestTemplate restTemplate;

    private final String financeServiceUrl = "http://finance-service/api";

    public boolean hasOutstandingInvoices(String studentId) {
        try {
            String response = restTemplate.getForObject(
                financeServiceUrl + "/invoices/outstanding/" + studentId,
                String.class
            );
            return Boolean.parseBoolean(response);
        } catch (Exception e) {
            // Log the error and return false as a fallback
            return false;
        }
    }
} 