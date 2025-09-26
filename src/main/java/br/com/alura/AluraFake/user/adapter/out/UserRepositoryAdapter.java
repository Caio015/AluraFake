package br.com.alura.AluraFake.user.adapter.out;

import br.com.alura.AluraFake.exceptions.UserNotFoundException;
import br.com.alura.AluraFake.user.domain.User;
import br.com.alura.AluraFake.user.port.out.FindUserByEmailPort;
import br.com.alura.AluraFake.user.port.out.FindUserByIdPort;

import java.util.Optional;

public class UserRepositoryAdapter implements FindUserByEmailPort,
                                              FindUserByIdPort {

    private final UserRepository repository;

    public UserRepositoryAdapter(UserRepository repository) {

        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return repository.findByEmail(email);
    }

    @Override
    public User findById(Long idUser) {

        return repository.findById(idUser).orElseThrow(() -> new UserNotFoundException(idUser));
    }
}
