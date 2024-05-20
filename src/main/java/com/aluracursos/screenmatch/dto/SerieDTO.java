package com.aluracursos.screenmatch.dto;

import com.aluracursos.screenmatch.model.Categoria;

public record SerieDTO(
    Long id,
    String titulo,
    int totalDeTemporadas, 
    Categoria genero,
    String sinopsis,
    String actores,
    String poster,
    Double evaluacion
) {

}
