package com.wompi.api.models;

import java.util.logging.Logger;

public class TransactionModel {
    private static final Logger LOGGER = Logger.getLogger(TransactionModel.class.getName());

    private int amount_in_cents;
    private String currency;
    private String customer_email;
    private String reference;
    private String signature; // Campo para la firma de integridad
    private PaymentMethod payment_method;
    private String acceptance_token;

    public TransactionModel(int amount, String email, String token, String phone) {
        LOGGER.info("Estructurando modelo de datos para la transacción...");
        this.amount_in_cents = amount * 100;
        this.currency = "COP";
        this.customer_email = email;
        this.acceptance_token = token;
        this.payment_method = new PaymentMethod("NEQUI", phone);
    }

    // --- SETTERS ---

    public void setReference(String reference) {
        LOGGER.info("Asignando referencia única: " + reference);
        this.reference = reference;
    }

    public void setSignature(String signature) {
        LOGGER.info("Asignando firma de integridad al modelo.");
        this.signature = signature;
    }

    public static class PaymentMethod {
        private String type;
        private String phone_number;

        public PaymentMethod(String type, String phone) {
            this.type = type;
            this.phone_number = phone;
        }

    }
}