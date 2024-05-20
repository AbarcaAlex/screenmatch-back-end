package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
    @JsonAlias("Title") String titulo, 
    @JsonAlias("totalSeasons") int totalDeTemporadas, 
    @JsonAlias("Genre")String genero,
    @JsonAlias("Plot")String sinopsis,
    @JsonAlias("Actors")String actores,
    @JsonAlias("Poster")String poster,
    @JsonAlias("imdbRating") String evaluacion
    ) {

}
