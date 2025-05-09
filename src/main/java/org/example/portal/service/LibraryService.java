package org.example.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LibraryService {

    @Autowired
    private RestTemplate restTemplate;

    private final String libraryServiceUrl = "http://library-service/api";

    public boolean hasOverdueBooks(String studentId) {
        try {
            String response = restTemplate.getForObject(
                libraryServiceUrl + "/books/overdue/" + studentId,
                String.class
            );
            return Boolean.parseBoolean(response);
        } catch (Exception e) {
            // Log the error and return false as a fallback
            return false;
        }
    }
} 