package com.wompi.api.questions;

import io.restassured.response.Response;
import java.util.logging.Logger;

public class EstadoTransaccionQuestion {
    private static final Logger LOGGER = Logger.getLogger(EstadoTransaccionQuestion.class.getName());

    public static String delPago(Response response) {
        LOGGER.info("Consultando la Question: ¿Cuál es el estado de la transacción en la respuesta?");

        // Extraemos el campo data.status del JSON
        String status = response.jsonPath().getString("data.status");

        LOGGER.info("El estado detectado es: " + status);
        return status;
    }
}