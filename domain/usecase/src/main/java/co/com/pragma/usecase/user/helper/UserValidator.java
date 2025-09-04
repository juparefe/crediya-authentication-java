package co.com.pragma.usecase.user.helper;

import co.com.pragma.model.user.User;

public class UserValidator {

    public UserValidator() {}

    public static void validate(User u) {
        if (u == null) {
            throw new IllegalArgumentException("El usuario es requerido");
        }
        if (isBlank(u.getName())) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (isBlank(u.getLastName())) {
            throw new IllegalArgumentException("El apellido es requerido");
        }
        if (isBlank(u.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico es requerido");
        }
        if (u.getSalary() == null) {
            throw new IllegalArgumentException("El salario base es requerido");
        }
        if (u.getSalary() <= 0) {
            throw new IllegalArgumentException("El salario base debe ser mayor a 0");
        }
        if (!u.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
