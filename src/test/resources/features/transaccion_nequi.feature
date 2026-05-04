# language: es
Característica: Automatización de transacciones en Wompi
  Como un comercio afiliado
  Quiero realizar pagos a través de la API
  Para garantizar el flujo correcto con Nequi

  Escenario: Crear transacción exitosa con Nequi
    Dado que el comercio obtiene el token de aceptación
    Cuando envía una transacción de "50000" COP con el método "NEQUI"
    Entonces el código de respuesta debe ser 201

  Escenario: Intento de transacción con monto insuficiente
    Dado que el comercio obtiene el token de aceptación
    Cuando envía una transacción de "1" COP con el método "NEQUI"
    Entonces el código de respuesta debe ser 422
    Y el mensaje de error debe contener "El monto mínimo de una transacción es $1,500 exceptuando impuestos"

  Escenario: Intento de transacción con token de aceptación expirado o inválido
    Dado que el comercio tiene un token de aceptación inválido
    Cuando envía una transacción de "50000" COP con el método "NEQUI"
    Entonces el código de respuesta debe ser 422
    Y el mensaje de error debe contener "acceptance_token"