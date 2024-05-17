package com.j0k3r.challengealuralatamliteratura.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseResult(
        List<LibroResponse> results
) {
}
