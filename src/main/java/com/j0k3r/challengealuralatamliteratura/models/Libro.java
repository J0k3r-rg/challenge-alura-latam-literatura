package com.j0k3r.challengealuralatamliteratura.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "libros")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Libro {

    @Id
    private Long id;

    private String title;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "libro_lang",joinColumns = @JoinColumn(name = "id_libro"),inverseJoinColumns = @JoinColumn(name = "id_lang"))
    private List<Lang> languages;

    private Long download_count;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "libro_autor",joinColumns = @JoinColumn(name = "id_libro"),inverseJoinColumns = @JoinColumn(name = "id_autor"))
    private List<Autor> authors;

}
