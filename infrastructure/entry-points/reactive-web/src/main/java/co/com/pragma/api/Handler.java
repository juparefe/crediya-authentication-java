package co.com.pragma.api;

import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.UserUseCase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler {
    private final UserUseCase userUseCase;

    public Handler(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    public Mono<ServerResponse> saveUserCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
                .flatMap(userUseCase::saveUser)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user)
                )
                .onErrorResume(e ->
                        ServerResponse.badRequest().bodyValue(e.getMessage())
                );
    }
}
