package com.joao.empresa.dto.error;

import java.time.LocalDateTime;

public record ErrorResponse(

        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path

) {
}

/* Esse DTO representa o JSON de erro que a API vai devolver:

       {
        "timestamp": "2026-10-27T11:40:00",
        "status": 404,
        "error": "Not Found",
        "message": "Empresa com ID 999 não encontrada.",
        "path": "/api/empresas/999"
        }

*/