package com.mirage.poetry.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Entity
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Poem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Column(columnDefinition = "text")
    private String body;

    @CreatedDate
    private LocalDate creationDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "poet_id")
    private Poet poet;


}
