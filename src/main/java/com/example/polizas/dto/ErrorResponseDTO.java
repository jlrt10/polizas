package com.example.polizas.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO {

    private String mensaje;
    private int status;

    public ErrorResponseDTO(String mensaje, int status) {
        this.mensaje = mensaje;
        this.status = status;
    }

}
