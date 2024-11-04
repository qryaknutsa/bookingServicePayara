package com.example.bookingServicePayara.exception.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class ErrorResponseArray {
    private String title;
    private List<String> details;
    private String instance;

    public ErrorResponseArray(String title, List<String> details, String instance) {
        this.title = title;
        this.details = details;
        this.instance = instance;
    }
}
