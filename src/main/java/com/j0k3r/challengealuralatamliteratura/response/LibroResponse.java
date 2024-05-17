package com.j0k3r.challengealuralatamliteratura.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j0k3r.challengealuralatamliteratura.models.Autor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroResponse(
        Long id,

        String title,

        List<String>languages,

        Long download_count,

        List<Autor> authors
) {
}
