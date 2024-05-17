package com.j0k3r.challengealuralatamliteratura.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autores")
@EqualsAndHashCode(of = "name")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long birth_year;

    private Long death_year;
}
