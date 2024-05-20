package com.aluracursos.screenmatch.model;

import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    private int totalDeTemporadas; 
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String sinopsis;
    private String actores;
    private String poster;
    private Double evaluacion;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;

    public Serie(){
        
    }
    
    public Serie(DatosSerie d) {
        this.titulo = d.titulo();
        this.totalDeTemporadas = d.totalDeTemporadas();
        this.genero = Categoria.fromString(d.genero().split(",")[0].trim());
        this.sinopsis = d.sinopsis();
        this.actores = d.actores();
        this.poster = d.poster();
        this.evaluacion = OptionalDouble.of(Double.valueOf(d.evaluacion())).orElse(0.0);
    }

    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public int getTotalDeTemporadas() {
        return totalDeTemporadas;
    }


    public void setTotalDeTemporadas(int totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }


    public Categoria getGenero() {
        return genero;
    }


    public void setGenero(Categoria genero) {
        this.genero = genero;
    }


    public String getSinopsis() {
        return sinopsis;
    }


    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }


    public String getActores() {
        return actores;
    }


    public void setActores(String actores) {
        this.actores = actores;
    }


    public String getPoster() {
        return poster;
    }


    public void setPoster(String poster) {
        this.poster = poster;
    }


    public Double getEvaluacion() {
        return evaluacion;
    }


    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }


    @Override
    public String toString() {
        return "titulo=" + titulo + ", totalDeTemporadas=" + totalDeTemporadas + ", genero=" + genero
                + ", sinopsis=" + sinopsis + ", actores=" + actores + ", poster=" + poster + ", evaluacion="
                + evaluacion + ", episodios=" + episodios;
    }



    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    
}
