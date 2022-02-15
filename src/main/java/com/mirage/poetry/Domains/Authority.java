package com.mirage.poetry.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn( name = "username" , unique = true , referencedColumnName = "username")
    private User user;

    @NotBlank
    private String role;

    public Authority(User u, String role_user) {
        this.user = u;
        this.role = role_user;
    }
}
