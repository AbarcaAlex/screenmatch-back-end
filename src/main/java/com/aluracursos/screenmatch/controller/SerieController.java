package com.aluracursos.screenmatch.controller;

import org.springframework.web.bind.annotation.RestController;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.service.SerieService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;







@RestController
@RequestMapping("/series")

public class SerieController {
    
    @Autowired
    private SerieService service;

    @GetMapping()
    public List<SerieDTO> obtenerTodasLasSeries() {
        return service.obtenerTodasLasSeries();
    }
    
    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5Series() {
        return service.obtenerTop5();
    }
    
    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosRecientes() {
        return service.obtenerLanzamientosMasRecientes();
    }
    
    @GetMapping("/{id}")
    public SerieDTO obtenerSeriePorId(@PathVariable Long id) {
        return service.obtenerSeriePorId(id);
    }
    
    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable Long id) {
        return service.obtenerTodasLasTemporadas(id);
    }
    
    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obtenerTemporadaEspecifica(@PathVariable Long id, @PathVariable Long numeroTemporada) {
        return service.obtenerTemporadaEspecifica(id,numeroTemporada);
    }
    
    @GetMapping("/categoria/{genero}")
    public List<SerieDTO> obtenerSeriesPorCategoria(@PathVariable String genero) {
        return service.obtenerSeriesPorCategoria(genero);
    }
    
    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obtenerTop5Episodios(@PathVariable Long id) {
        return service.obtenerTop5Episodios(id);
    }
    
    
}
