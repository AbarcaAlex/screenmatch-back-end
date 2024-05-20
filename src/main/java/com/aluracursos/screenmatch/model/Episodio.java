package com.aluracursos.screenmatch.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int temporada;
    private String titulo;
    private int numeroEpisodio;
    private double evaluacion;
    private LocalDate fechaDeLanzamiento;
    @ManyToOne
    private Serie serie;

    
    public Episodio(){

    }

    public Episodio(int numeroTemporada, DatosEpisodio d) {
        this.temporada = numeroTemporada;
        this.titulo = d.titulo();
        this.numeroEpisodio = Integer.valueOf(d.numeroEpisodio());
        try {
            this.evaluacion = Double.valueOf(d.evaluacion());
        } catch (Exception e) {
            this.evaluacion = 0.0;
        }
        try {
        this.fechaDeLanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
        } catch (Exception e) {
            this.fechaDeLanzamiento = null;
        }
        
    }


    public int getTemporada() {
        return temporada;
    }
    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }
    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }
    public double getEvaluacion() {
        return evaluacion;
    }
    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }
    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }
    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }


    @Override
    public String toString() {
        return "temporada=" + temporada + ", titulo=" + titulo + ", numeroEpisodio=" + numeroEpisodio
                + ", evaluacion=" + evaluacion + ", fechaDeLanzamiento=" + fechaDeLanzamiento;
    }


    public Serie getSerie() {
        return serie;
    }


    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    
    
}
