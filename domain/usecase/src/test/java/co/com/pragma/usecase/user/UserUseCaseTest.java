package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserUseCaseTest {
    private UserRepository userRepository;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userUseCase = new UserUseCase(userRepository);
    }

    private static User validUser() {
        User u = new User();
        u.setName("Juan");
        u.setLastName("Pérez");
        u.setEmail("juan.perez@acme.com");
        u.setSalary(1_000_000.0);
        return u;
    }

    @Test
    void shouldErrorWhenValidationFails() {
        // given: un usuario inválido (por ejemplo, sin email)
        User invalid = validUser();
        invalid.setEmail(" "); // fuerza fallo de UserValidator

        // when
        Mono<User> result = userUseCase.saveUser(invalid);

        // then
        StepVerifier.create(result)
                .expectErrorSatisfies(err -> {
                    assertTrue(err instanceof IllegalArgumentException);
                    assertEquals("El correo electrónico es requerido", err.getMessage());
                })
                .verify();

        // y no debe consultar al repositorio
        verifyNoInteractions(userRepository);
    }

    @Test
    void shouldErrorWhenEmailAlreadyExists() {
        // given
        User u = validUser();
        when(userRepository.existsByEmail(u.getEmail())).thenReturn(Mono.just(true));

        // when
        Mono<User> result = userUseCase.saveUser(u);

        // then
        StepVerifier.create(result)
                .expectErrorSatisfies(err -> {
                    assertTrue(err instanceof IllegalArgumentException);
                    assertEquals("El correo 'juan.perez@acme.com' ya está registrado", err.getMessage());
                })
                .verify();

        // se llamó a existsByEmail pero NO a saveUser
        verify(userRepository, times(1)).existsByEmail(u.getEmail());
        verify(userRepository, never()).saveUser(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldSaveWhenEmailDoesNotExist() {
        // given
        User u = validUser();
        when(userRepository.existsByEmail(u.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.saveUser(u)).thenReturn(Mono.just(u));

        // when
        Mono<User> result = userUseCase.saveUser(u);

        // then
        StepVerifier.create(result)
                .expectNextMatches(saved ->
                        saved.getEmail().equals(u.getEmail()) &&
                                saved.getName().equals(u.getName()))
                .verifyComplete();

        // verifica el flujo de llamadas y el argumento pasado a saveUser
        verify(userRepository, times(1)).existsByEmail(u.getEmail());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).saveUser(captor.capture());
        assertEquals(u.getEmail(), captor.getValue().getEmail());

        verifyNoMoreInteractions(userRepository);
    }
}
