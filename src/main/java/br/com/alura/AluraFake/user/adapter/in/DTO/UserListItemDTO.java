package br.com.alura.AluraFake.user.adapter.in.DTO;

import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;

import java.io.Serializable;

public class UserListItemDTO implements Serializable {

    private final String name;
    private final String email;
    private final Role role;

    public UserListItemDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

}
