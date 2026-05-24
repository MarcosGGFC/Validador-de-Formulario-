package cesur.dam1.practica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de ValidadorFormulario")
class ValidadorFormularioTest {

    private ValidadorFormulario validador;

    @BeforeEach
    void setUp() {
        validador = new ValidadorFormulario();
    }

    // === VALIDAR EMAIL ===

    @Test
    @DisplayName("Email típico válido debe devolver true")
    void testEmailValido() {
        assertTrue(validador.validarEmail("usuario@gmail.com"));
    }

    @Test
    @DisplayName("Email sin arroba debe devolver false")
    void testEmailSinArroba() {
        assertFalse(validador.validarEmail("usuariogmail.com"));
    }

    @Test
    @DisplayName("Email null debe devolver false")
    void testEmailNull() {
        assertFalse(validador.validarEmail(null));
    }

    @Test
    @DisplayName("Email vacío debe devolver false")
    void testEmailVacio() {
        assertFalse(validador.validarEmail(""));
    }

    @Test
    @DisplayName("Email sin dominio después del arroba debe devolver false (BUG esperado)")
    void testEmailSinDominioTrasBUG() {
        boolean resultado = validador.validarEmail("usuario@");
        assertFalse(resultado, "BUG: 'usuario@' no debería ser un email válido");
    }

    @Test
    @DisplayName("Email sin punto en el dominio debe devolver false (BUG esperado)")
    void testEmailSinPuntoEnDominioBUG() {
        assertFalse(validador.validarEmail("usuario@gmail"),
                "BUG: falta el punto en el dominio, no debería ser válido");
    }

    @Test
    @DisplayName("Solo el símbolo arroba debe devolver false (BUG esperado)")
    void testEmailSoloArrobaBUG() {
        assertFalse(validador.validarEmail("@"),
                "BUG: '@' solo no es un email válido");
    }

    @ParameterizedTest
    @DisplayName("Emails con arroba en distintas posiciones son aceptados por el código (caja blanca)")
    @ValueSource(strings = {
            "a@b",           
            "@dominio.com",  
            "usuario@",      
            "a@b@c"          
    })
    void testEmailsQueElCodigoAceptaPorTenerArroba(String email) {
        assertTrue(validador.validarEmail(email),
                "El código acepta '" + email + "' porque contiene '@' — validación incompleta");
    }

    // === VALIDAR PASSWORD ===

    @Test
    @DisplayName("Contraseña con 8 caracteres y un número debe devolver true")
    void testPasswordValida() {
        assertTrue(validador.validarPassword("Abcdefg1"));
    }
 
    @Test
    @DisplayName("Contraseña null debe devolver false")
    void testPasswordNull() {
        assertFalse(validador.validarPassword(null));
    }
 
    @Test
    @DisplayName("Contraseña con 7 caracteres debe devolver false (valor límite)")
    void testPasswordSieteCaracteres() {
        assertFalse(validador.validarPassword("Abcde1f"));
    }
 
    @Test
    @DisplayName("Contraseña de 8 caracteres sin número debe devolver false")
    void testPasswordSinNumeros() {
        assertFalse(validador.validarPassword("AbcdefGH"));
    }

    // === VALIDAR TELEFONO ===

    @Test
    @DisplayName("Teléfono válido empezando por 6 debe devolver true")
    void testTelefonoValido() {
        assertTrue(validador.validarTelefono("612345678"));
    }
 
    @Test
    @DisplayName("Teléfono null debe devolver false")
    void testTelefonoNull() {
        assertFalse(validador.validarTelefono(null));
    }
 
    @Test
    @DisplayName("Teléfono empezando por 8 debe devolver false")
    void testTelefonoPrefijoInvalido() {
        assertFalse(validador.validarTelefono("812345678"));
    }
 
    @ParameterizedTest
    @DisplayName("Teléfonos válidos empezando por 6, 7 y 9 deben devolver true")
    @ValueSource(strings = { "612345678", "712345678", "912345678" })
    void testTelefonoPrefijoValido(String telefono) {
        assertTrue(validador.validarTelefono(telefono));
    }

    // === VALIDAR NOMBRE ===

     @Test
    @DisplayName("Nombre válido sin tildes debe devolver true")
    void testNombreValido() {
        assertTrue(validador.validarNombre("Juan Garcia"));
    }
 
    @Test
    @DisplayName("Nombre null debe devolver false")
    void testNombreNull() {
        assertFalse(validador.validarNombre(null));
    }
 
    @Test
    @DisplayName("Nombre de 1 carácter debe devolver false (valor límite inferior)")
    void testNombreDemasiadoCorto() {
        assertFalse(validador.validarNombre("A"));
    }
 
    @Test
    @DisplayName("Nombre con tildes debe devolver false (BUG: debería ser true)")
    void testNombreConTildesBUG() {
        // BUG IR-002: el regex [a-zA-Z ] no acepta caracteres acentuados ni ñ
        assertFalse(validador.validarNombre("María José"),
                "BUG: nombres españoles con tildes deberían ser válidos");
    }

    // === VALIDAR DNI ===

    @Test
    @DisplayName("DNI con letra correcta debe devolver true")
    void testDNIValido() {
        assertTrue(validador.validarDNI("12345678Z"));
    }
 
    @Test
    @DisplayName("DNI null debe devolver false")
    void testDNINull() {
        assertFalse(validador.validarDNI(null));
    }
 
    @Test
    @DisplayName("DNI con letra incorrecta debe devolver false")
    void testDNILetraIncorrecta() {
        assertFalse(validador.validarDNI("12345678A"));
    }
 
    @Test
    @DisplayName("DNI con letra en minúscula debe devolver true (el código la convierte)")
    void testDNILetraMinuscula() {
        // Caja blanca: Character.toUpperCase() convierte antes de comparar
        assertTrue(validador.validarDNI("12345678z"));
    }
 

}