package modelo;

import actividades.Actividad;
import actividades.Charla;
import actividades.Curso;
import actividades.Taller;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventoUniversitario implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int cantidadEventos;

    private final String id;
    private final String titulo;
    private final double costoBase;
    private final boolean gratuito;

    private Sala sala;

    private final List<Actividad> actividades =
            new ArrayList<>();

    public EventoUniversitario(
            String id,
            String titulo,
            double costoBase,
            boolean gratuito
    ) {
        if (id == null
                || id.isBlank()
                || titulo == null
                || titulo.isBlank()
                || costoBase < 0) {

            throw new IllegalArgumentException(
                    "Los datos del evento no son válidos."
            );
        }

        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;

        cantidadEventos++;
    }

    public EventoUniversitario(
            EventoUniversitario otroEvento
    ) {
        this(
                otroEvento.id + "-COPIA",
                otroEvento.titulo + " (copia)",
                otroEvento.costoBase,
                otroEvento.gratuito
        );

        this.sala = otroEvento.sala;
        this.actividades.addAll(otroEvento.actividades);
    }

    public double calcularCostoEstimado() {
        double costoDelEvento;

        if (gratuito) {
            costoDelEvento = 0;
        } else {
            costoDelEvento = costoBase;
        }

        return costoDelEvento
                + calcularCostoMateriales(actividades);
    }

    public void asignarSala(Sala sala) {
        if (sala == null) {
            throw new IllegalArgumentException(
                    "La sala es obligatoria."
            );
        }

        this.sala = sala;
    }

    public void agregarActividad(Actividad actividad) {
        if (actividad == null) {
            throw new IllegalArgumentException(
                    "La actividad es obligatoria."
            );
        }

        actividades.add(actividad);
    }

    public void crearActividad(
            int id,
            String titulo,
            String tipo
    ) {
        switch (tipo.toLowerCase()) {
            case "charla":
                agregarActividad(
                        new Charla(
                                id,
                                titulo,
                                20,
                                "A confirmar"
                        )
                );
                break;

            case "taller":
                agregarActividad(
                        new Taller(
                                id,
                                titulo,
                                15,
                                false
                        )
                );
                break;

            case "curso":
                agregarActividad(
                        new Curso(
                                id,
                                titulo,
                                15,
                                1
                        )
                );
                break;

            default:
                throw new IllegalArgumentException(
                        "Tipo de actividad desconocido: "
                                + tipo
                );
        }
    }

    public <T extends Actividad>
    List<T> filtrarActividadesPorTipo(Class<T> tipo) {

        List<T> resultado = new ArrayList<>();

        for (Actividad actividad : actividades) {
            if (tipo.isInstance(actividad)) {
                resultado.add(tipo.cast(actividad));
            }
        }

        return resultado;
    }

    public double calcularCostoMateriales(
            List<? extends Actividad> actividades
    ) {
        double costoTotal = 0;

        for (Actividad actividad : actividades) {
            costoTotal += actividad.calcularCostoMateriales();
        }

        return costoTotal;
    }

    public void mostrarDatos() {
        System.out.println(
                "Evento: " + titulo + " (" + id + ")"
        );

        if (sala == null) {
            System.out.println("Sala: sin asignar");
        } else {
            System.out.println("Sala: " + sala);
        }

        System.out.printf(
                "Costo estimado: $%.2f%n",
                calcularCostoEstimado()
        );

        System.out.println("Actividades:");

        for (Actividad actividad : actividades) {
            System.out.println("  - " + actividad);
            actividad.mostrarInscripciones();
        }
    }

    public boolean persistirEvento() throws IOException {
        Path carpeta = Path.of("datos");

        Files.createDirectories(carpeta);

        Path archivo = carpeta.resolve(id + ".ser");

        try (
                ObjectOutputStream salida =
                        new ObjectOutputStream(
                                new FileOutputStream(
                                        archivo.toFile()
                                )
                        )
        ) {
            salida.writeObject(this);
            return true;
        }
    }

    public static EventoUniversitario recuperarEvento(
            String id
    ) throws IOException, ClassNotFoundException {

        Path archivo = Path.of(
                "datos",
                id + ".ser"
        );

        try (
                ObjectInputStream entrada =
                        new ObjectInputStream(
                                new FileInputStream(
                                        archivo.toFile()
                                )
                        )
        ) {
            return (EventoUniversitario)
                    entrada.readObject();
        }
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Sala getSala() {
        return sala;
    }

    public List<Actividad> getActividades() {
        return Collections.unmodifiableList(actividades);
    }

    public static int getCantidadEventos() {
        return cantidadEventos;
    }
}
