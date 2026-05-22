package cesur.dam1.practica;

/**
 * Validador de datos de un formulario de registro.
 * Proyecto: validador-formulario
 * Versión: 1.0
 */
public class ValidadorFormulario {

    // Email válido: contiene '@' y al menos un '.' después de '@'
    public boolean validarEmail(String email) {
        if (email == null) return false;
        return email.contains("@"); // ← ¿es suficiente esta comprobación?
    }

    // Contraseña: mínimo 8 caracteres, al menos 1 número
    public boolean validarPassword(String password) {
        if (password == null || password.length() < 8) return false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) return true;
        }
        return false;
    }

    // Teléfono español: 9 dígitos, empieza por 6, 7 o 9
    public boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.length() != 9) return false;
        char primero = telefono.charAt(0);
        if (primero != '6' && primero != '7' && primero != '9') return false;
        return telefono.matches("[0-9]+");
    }

    // Nombre: solo letras y espacios, entre 2 y 50 caracteres
    public boolean validarNombre(String nombre) {
        if (nombre == null) return false;
        if (nombre.length() < 2 || nombre.length() > 50) return false;
        return nombre.matches("[a-zA-Z ]+"); // ← ¿acepta nombres con tildes?
    }

    // DNI español: 8 dígitos + 1 letra
    public boolean validarDNI(String dni) {
        if (dni == null || dni.length() != 9) return false;
        String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
        try {
            int numero = Integer.parseInt(dni.substring(0, 8));
            char letra = Character.toUpperCase(dni.charAt(8));
            return letrasValidas.charAt(numero % 23) == letra;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}