package com.example.csv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaResponse {

    private String message;
    private String result;
}
