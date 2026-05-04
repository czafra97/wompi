package com.wompi.api.tasks;

import com.wompi.api.models.TransactionModel;
import com.wompi.api.utils.Constants;
import com.wompi.api.utils.IntegritySigner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.logging.Logger;

public class CrearTransaccionTask {
    private static final Logger LOGGER = Logger.getLogger(CrearTransaccionTask.class.getName());

    public static Response conNequi(int monto, String token, String telefono) {
        LOGGER.info("Iniciando tarea: Creación de transacción con Nequi");

        int centavos = monto * 100;
        String referencia = "REF_RETO_" + System.currentTimeMillis();
        String moneda = "COP";
        String telefonoFinal = (telefono == null || telefono.isEmpty()) ? "3991111111" : telefono;

        LOGGER.info("Generando firma de integridad para la referencia: " + referencia);

        //Generar la firma de integridad (Requisito para evitar el error 422)
        String firma = IntegritySigner.generate(
                referencia,
                centavos,
                moneda,
                "stagtest_integrity_nAIBuqayW70XpUqJS4qf4STYiISd89Fp"
        );

        LOGGER.info("Firma generada exitosamente.");

        TransactionModel data = new TransactionModel(monto, "qa_automation@wompi.dev", token, telefonoFinal);
        data.setReference(referencia);
        data.setSignature(firma);

        LOGGER.info("Enviando petición POST a Wompi UAT Sandbox...");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + Constants.PUBLIC_KEY)
                .contentType(ContentType.JSON)
                .body(data)
                .post(Constants.BASE_URL + "/transactions");

        if (response.getStatusCode() != 201) {
            LOGGER.warning("La API respondió con un error esperado para este escenario.");
            LOGGER.warning("Cuerpo del error: " + response.getBody().asPrettyString());
        }

        return response;
    }
}