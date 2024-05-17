package com.j0k3r.challengealuralatamliteratura.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "langs")
@ToString
public class Lang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String lang;

    public Lang(String lang) {
        this.lang = lang;
    }
}
