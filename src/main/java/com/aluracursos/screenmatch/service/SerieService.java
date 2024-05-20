package com.aluracursos.screenmatch.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.ISerieRepository;

@Service
public class SerieService {

    @Autowired
    private ISerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries() {
        return repository.findAll().stream()
            .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getGenero(), s.getSinopsis(), s.getActores(), s.getPoster(), s.getEvaluacion()))
            .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public List<SerieDTO> convierteDatos(List<Serie> serie){
        return serie.stream()
            .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getGenero(), s.getSinopsis(), s.getActores(), s.getPoster(), s.getEvaluacion()))
            .collect(Collectors.toList());
    }

    public SerieDTO obtenerSeriePorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getGenero(), s.getSinopsis(), s.getActores(), s.getPoster(), s.getEvaluacion());
        }else{
            return null;
        }
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
        }else{
            return null;
        }
    }

    public List<EpisodioDTO> obtenerTemporadaEspecifica(Long id, Long numeroTemporada) {
        return repository.obtenerTemporadaEspecifica(id, numeroTemporada).stream()
            .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
            .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorCategoria(String genero) {
        Categoria categoria = Categoria.fromEspanol(genero);
        return convierteDatos(repository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obtenerTop5Episodios(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return repository.top5Episodios(s).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
        }else{
            return null;
        }
    }
}
