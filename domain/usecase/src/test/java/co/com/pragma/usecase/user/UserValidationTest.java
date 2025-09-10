package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.helper.UserValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {
    @Test
    void shouldThrowWhenUserIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validate(null)
        );
        assertEquals("El usuario es requerido", ex.getMessage());
    }

    @Test
    void shouldThrowWhenNameIsBlank() {
        User u = new User();
        u.setName(" ");
        u.setLastName("Perez");
        u.setEmail("correo@valido.com");
        u.setSalary(1000.0);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El nombre es requerido", ex.getMessage());
    }

    @Test
    void shouldThrowWhenLastNameIsBlank() {
        User u = new User();
        u.setName("Juan");
        u.setLastName("");
        u.setEmail("correo@valido.com");
        u.setSalary(1000.0);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El apellido es requerido", ex.getMessage());
    }
    @Test
    void shouldThrowWhenEmailIsBlank() {
        User u = new User();
        u.setName("Juan");
        u.setLastName("Perez");
        u.setEmail(null);
        u.setSalary(1000.0);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El correo electrónico es requerido", ex.getMessage());
    }

    @Test
    void shouldThrowWhenSalaryIsNull() {
        User u = new User();
        u.setName("Juan");
        u.setLastName("Perez");
        u.setEmail("correo@valido.com");
        u.setSalary(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El salario base es requerido", ex.getMessage());
    }

    @Test
    void shouldThrowWhenSalaryIsZeroOrNegative() {
        User u = new User();
        u.setName("Juan");
        u.setLastName("Perez");
        u.setEmail("correo@valido.com");
        u.setSalary(0.0);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El salario base debe ser mayor a 0", ex.getMessage());
    }

    @Test
    void shouldThrowWhenEmailIsInvalid() {
        User u = new User();
        u.setName("Juan");
        u.setLastName("Perez");
        u.setEmail("correo@invalido");
        u.setSalary(1000.0);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> UserValidator.validate(u)
        );
        assertEquals("El correo electrónico no tiene un formato válido", ex.getMessage());
    }

    @Test
    void shouldPassWhenUserIsValid() {
        User u = new User();
        u.setName("Juan");
        u.setLastName("Perez");
        u.setEmail("correo@valido.com");
        u.setSalary(1000.0);

        assertDoesNotThrow(() -> UserValidator.validate(u));
    }
}
