package com.mirage.poetry.Domains;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity(name="poets")
@NoArgsConstructor
@Getter
@Setter
public class Poet {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String penName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "poet",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Poem> poems;


    public Poet(String penName, User newUser) {
        this.penName = penName;
        this.user = newUser;
    }
}
