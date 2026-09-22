package actividades;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int CUPO_MINIMO = 1;

    private final int id;
    private final String titulo;
    private final int cupoMaximo;

    private final List<Inscripcion> inscripciones =
            new ArrayList<>();

    protected Actividad(int id, String titulo, int cupoMaximo) {
        if (id <= 0
                || titulo == null
                || titulo.isBlank()
                || cupoMaximo < CUPO_MINIMO) {

            throw new IllegalArgumentException(
                    "Los datos de la actividad no son válidos."
            );
        }

        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
    }

    public Inscripcion inscribir(Estudiante estudiante)
            throws CupoExcedidoException {

        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante es obligatorio."
            );
        }

        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException(
                    "No hay cupo en '" + titulo
                            + "'. Máximo: " + cupoMaximo
            );
        }

        boolean yaEstaInscripto = inscripciones.stream()
                .anyMatch(inscripcion ->
                        inscripcion.getEstudiante().equals(estudiante));

        if (yaEstaInscripto) {
            throw new IllegalArgumentException(
                    "El estudiante ya está inscripto en " + titulo
            );
        }

        Inscripcion inscripcion =
                new Inscripcion(estudiante, this);

        inscripciones.add(inscripcion);

        return inscripcion;
    }

    public void mostrarInscripciones() {
        if (inscripciones.isEmpty()) {
            System.out.println("    Sin inscripciones");
        } else {
            inscripciones.forEach(inscripcion ->
                    System.out.println("    " + inscripcion));
        }
    }

    public abstract double calcularCostoMateriales();

    public abstract String getTipo();

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    @Override
    public String toString() {
        return getTipo()
                + " #" + id
                + " - " + titulo
                + " | cupo "
                + inscripciones.size()
                + "/" + cupoMaximo;
    }
}
