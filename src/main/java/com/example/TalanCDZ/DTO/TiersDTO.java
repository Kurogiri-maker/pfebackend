package com.example.TalanCDZ.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TiersDTO {

    private String nom;
    private String siren;
    private String refMandat;
}
