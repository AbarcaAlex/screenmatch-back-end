package com.aluracursos.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import java.util.Optional;
import java.util.List;


public interface ISerieRepository extends JpaRepository<Serie,Long>{

    Optional<Serie> findByTituloContainsIgnoringCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria genero);

    // List<Serie> findByTotalDeTemporadasIsLessThanEqualAndEvaluacionGreaterThanEqual(int maxTemporadas, double minEvaluacion);

    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :maxTemporadas AND s.evaluacion >= :minEvaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(int maxTemporadas, double minEvaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obtenerTemporadaEspecifica(Long id, Long numeroTemporada);
}
