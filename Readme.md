# Automatización API Wompi - Reto Técnico

Este proyecto contiene la automatización de pruebas para el proceso de creación de transacciones vía Nequi en el Sandbox de Wompi.

## 🛠️ Tecnologías y Requisitos
* **Java 17 (LTS)**: Lenguaje base para la automatización.
* **Cucumber / BDD**: Definición de escenarios en lenguaje Gherkin.
* **RestAssured**: Cliente para el consumo de servicios REST.
* **JUnit**: Orquestador de la ejecución (Runner).

## 🏗️ Arquitectura
Se aplicó un patrón **Screenplay simplificado** para garantizar escalabilidad:
* **Tasks**: Acciones de interacción con la API (POST/GET).
* **Models**: POJOs para la serialización de datos con Jackson.
* **Questions**: Lógica de validación de estados y respuestas.
* **Utils**: Manejo de seguridad, incluyendo la generación de la **Firma de Integridad (SHA-256)**.

## 🚀 Ejecución
Para correr las pruebas, ejecuta la clase:
`com.wompi.api.runners.MainRunner`

## 📊 Escenarios Cubiertos
1. **Transacción Exitosa**: Validación de flujo completo y estado PENDING.
2. **Monto Inválido**: Validación de error 422 por reglas de negocio.
3. **Token Inválido**: Validación de error 422 por fallos en el acceptance_token.