package com.mephi.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Поле \"name\" не может быть пустым")
    @Column(name = "name", length = 30)
    @Size(min = 1, max = 30)
    private String name;

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnore
    private List<User> users;
}
