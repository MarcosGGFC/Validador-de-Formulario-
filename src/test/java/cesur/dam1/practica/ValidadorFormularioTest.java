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
 

}