package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.helper.UserValidator;
import reactor.core.publisher.Mono;

public class UserUseCase {
    private final UserRepository userRepository;

    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> saveUser(User user) {
        try {
            UserValidator.validate(user);
        } catch (IllegalArgumentException e) {
            return Mono.error(e);
        }
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.<User>error(
                                new IllegalArgumentException(String.format("El correo '%s' ya está registrado", user.getEmail()))
                        );

                    }
                    return userRepository.saveUser(user);
                });
    }
}
