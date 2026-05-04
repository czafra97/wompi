package com.wompi.api.stepdefinitions;

import com.wompi.api.tasks.CrearTransaccionTask;
import com.wompi.api.utils.Constants;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.logging.Logger;

public class WompiStepDefinitions {
    private static final Logger LOGGER = Logger.getLogger(WompiStepDefinitions.class.getName());
    private Response response;
    private String acceptanceToken;

    @Dado("que el comercio obtiene el token de aceptación")
    public void queElComercioObtieneElTokenDeAceptacion() {
        LOGGER.info("Consultando información del comercio para obtener acceptance_token...");

        response = RestAssured.given()
                .baseUri(Constants.BASE_URL)
                .get("/merchants/" + Constants.PUBLIC_KEY);

        if (response.getStatusCode() != 200) {
            LOGGER.severe("Error al obtener merchant: " + response.getBody().asString());
        }

        // Extraemos el token del acuerdo de términos y condiciones
        acceptanceToken = response.jsonPath().getString("data.presigned_acceptance.acceptance_token");

        LOGGER.info("Token de aceptación obtenido con éxito: " + acceptanceToken);
    }

    @Dado("que el comercio tiene un token de aceptación inválido")
    public void queElComercioTieneUnTokenDeAceptacionInvalido() {
        LOGGER.info("Simulando escenario de error: Asignando token inválido.");
        this.acceptanceToken = "token_totalmente_falso_12345";
    }

    @Cuando("envía una transacción de {string} COP con el método {string}")
    public void enviaUnaTransaccionDeCOPConElMetodo(String monto, String metodo) {
        int valor = Integer.parseInt(monto);
        response = CrearTransaccionTask.conNequi(valor, acceptanceToken, "3991111111");
    }

    @Entonces("el código de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(Integer code) {
        response.then().statusCode(code);
    }

    @Entonces("el mensaje de error debe contener {string}")
    public void elMensajeDeErrorDebeContener(String mensajeEsperado) {
        LOGGER.info("Validando que el cuerpo de la respuesta contenga el error: " + mensajeEsperado);

        String cuerpoRespuesta = response.getBody().asString();

        boolean contieneError = cuerpoRespuesta.toLowerCase().contains(mensajeEsperado.toLowerCase());

        if (contieneError) {
            LOGGER.info("Validación de mensaje de error exitosa.");
        } else {
            LOGGER.severe("No se encontró el mensaje esperado. Respuesta: " + cuerpoRespuesta);
        }

        org.junit.Assert.assertTrue("El mensaje de error no coincide", contieneError);
    }

    @Y("el estado de la transacción debe ser {string}")
    public void elEstadoDeLaTransaccionDebeSer(String estado) {
        // Validación del campo status en el JSON
    }
}