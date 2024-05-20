package com.aluracursos.screenmatch.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.ISerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey="+System.getenv().get("OMDB_API_KEY");
    private ConvierteDatos conversor = new ConvierteDatos();
    private ISerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    public Principal(ISerieRepository repository) {
        this.repositorio=repository;
    }

    public void mostrarMenu(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                    5 - Buscar top 5 series
                    6 - Buscar series por categoria
                    7 - Buscar series con un maximo de tmeporadas y un minimo de nota
                    8 - Buscar episodios por titulo
                    9 - Buscar top e episodios por serie
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;

                case 3:
                    mostrarSeriesBuscadas();
                    break;

                case 4: 
                    buscarSeriesPorTitulo();
                    break;

                case 5:
                    buscarTop5Series();
                    break;

                case 6:
                    buscarseriesPorCategoria();
                    break;

                case 7:
                    buscarseriePorTemporadasYEvaluacion();
                    break;

                case 8:
                    buscarEpisodioPorTitulo();
                    break;

                case 9:
                    buscarTop5Episodios();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarTop5Episodios() {
        buscarSeriesPorTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s Temporada: %s Episodio: %s Evaluacion %s\n",e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
        }
    }

    private void buscarEpisodioPorTitulo() {
        System.out.println("Escriba el titulo del episodio a buscar: ");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e -> System.out.printf("Serie: %s Temporada: %s Episodio: %s Evaluacion %s\n",e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }

    private void buscarseriePorTemporadasYEvaluacion() {
        System.out.println("Escriba el maximo de temporadas que debe tener la serie a buscar: ");
        var maxTemporadas = teclado.nextInt();
        System.out.println("Escriba la evaluacion minima que debe tener la serie a buscar: ");
        var minEvaluacion = teclado.nextDouble();
        List<Serie> busqueda = repositorio.seriesPorTemporadaYEvaluacion(maxTemporadas, minEvaluacion);

        if (busqueda.isEmpty()) {
            System.out.println("No se encontraron series que cumplan con los requisitos de busqueda");
        }else{
            System.out.println("Las series que cumplen con los requisitos de busqueda son: ");
            busqueda.forEach(b -> System.out.println("Titulo: "+b.getTitulo()+" Evaluacion: "+b.getEvaluacion()));
        }
    }

    private void buscarseriesPorCategoria() {
        System.out.println("Escriba el genero/catregoria a buscar: ");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series del genero "+genero+" son:");
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarTop5Series() {
        List<Serie> top5 = repositorio.findTop5ByOrderByEvaluacionDesc();
        top5.forEach(t -> System.out.println("Serie: "+t.getTitulo()+" Evaluacion: "+t.getEvaluacion()));
    }

    private void buscarSeriesPorTitulo() {
        System.out.println("Escribe el nombre de la serie que deseas buscar:");
        var nombreSerie = teclado.nextLine();
        serieBuscada = repositorio.findByTituloContainsIgnoringCase(nombreSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("La serie buscada es: "+serieBuscada.get());
        }else{
            System.out.println("no se encontraron reslutados!");
        }
    }

    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }
    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios: ");
        var nombreSerie = teclado.nextLine();

        Optional<Serie> serie = series.stream()
            .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
            .findFirst();

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                    var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+").toLowerCase() + "&season=" + i + API_KEY);
                    DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                    temporadas.add(datosTemporada);
            }

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                    .map(e -> new Episodio(t.numeroTemporada(), e)))
                .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
        
    }
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        System.out.println(datos);
    }
    private void mostrarSeriesBuscadas(){
        series = repositorio.findAll();
        
        series.stream()
            .sorted(Comparator.comparing(Serie::getGenero))
            .forEach(System.out::println);
    }
    

}
