@sp01
Feature: Compra de un celular con en plan PostPago
Como usuario quiero comprar un nuevo equipo en un plan PostPago

Scenario: Un usuario realiza la compra de un celular en plan postpago con una linea nueva para Lima 

Given estoy en la pagina principal de tienda claro LN
When selecciono el menu celulares, luego el submenu postpago y luego seleccion la opcion linea nueva LN
And selecciono un producto del catalogo LN
And selecciono Aniadir a mi bolsa LN
And observo el resumen de mi compra y selecciono Siguiente LN
And completo el formulario con mis datos personales y seleccion Siguiente LN
And acepto los terminos y condiciones de pago y las condiciones y selecciono Siguiente LN
Then visualizo el checkout LN
