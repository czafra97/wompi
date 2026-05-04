package com.wompi.api.questions;

import io.restassured.response.Response;
import java.util.logging.Logger;

public class EstadoTransaccionQuestion {
    private static final Logger LOGGER = Logger.getLogger(EstadoTransaccionQuestion.class.getName());

    public static Integer deLaPeticion(Response response) {
        Integer statusCode = response.getStatusCode();
        LOGGER.info("Consultando la Question: ¿Cuál es el código de respuesta de la API?");
        LOGGER.info("Código recibido: " + statusCode);
        return statusCode;
    }
}