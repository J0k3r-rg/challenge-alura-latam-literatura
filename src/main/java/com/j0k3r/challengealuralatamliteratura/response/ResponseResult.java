package com.j0k3r.challengealuralatamliteratura.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j0k3r.challengealuralatamliteratura.models.Libro;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseResult(
        List<Libro> results
) {
}
