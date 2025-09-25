package br.com.alura.AluraFake.user.adapter.out;

import br.com.alura.AluraFake.user.domain.User;
import br.com.alura.AluraFake.user.port.in.FindUserByEmailUseCase;

import java.util.Optional;

public class UserRepositoryAdapter implements FindUserByEmailUseCase {

    private final UserRepository repository;

    public UserRepositoryAdapter(UserRepository repository) {

        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return repository.findByEmail(email);
    }
}
