package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String legajo;
    private final String nombre;

    public Estudiante(String legajo, String nombre) {
        if (legajo == null || legajo.isBlank()
                || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El legajo y el nombre son obligatorios."
            );
        }

        this.legajo = legajo;
        this.nombre = nombre;
    }

    public String getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre + " (legajo " + legajo + ")";
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Estudiante estudiante)) {
            return false;
        }

        return legajo.equals(estudiante.legajo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legajo);
    }
}
