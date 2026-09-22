package modelo;

import java.io.Serializable;

public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int id;
    private final String nombre;

    public Sala(int id, String nombre) {
        if (id <= 0 || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "Los datos de la sala no son válidos."
            );
        }

        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre + " (#" + id + ")";
    }
}
