package com.mephi.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Table(
        name = "myRole",
        uniqueConstraints = {
                @UniqueConstraint(name = "role_name_unique", columnNames = "name")}
)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    private Long idRole;

    @NotBlank(message = "Поле \"firstName\" не может быть пустым")
    @Column(name = "name")
    private String name;

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnore
    private List<User> users;
}
