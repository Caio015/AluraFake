package br.com.alura.AluraFake.user.port.in;

import br.com.alura.AluraFake.user.domain.User;

import java.util.Optional;

public interface FindUserByEmailUseCase {

    Optional<User> findByEmail(String emailInstructor);
}
