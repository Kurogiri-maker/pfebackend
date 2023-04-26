package com.example.TalanCDZ.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String message;
    private String type;
    private Map<String,String> legacyAttributes;
    private Map<String,String> additionalAttributes;

}
