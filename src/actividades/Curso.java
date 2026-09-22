package actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Curso extends Actividad
        implements Certificable {

    private static final long serialVersionUID = 1L;

    private final int nivel;

    public Curso(
            int id,
            String titulo,
            int cupoMaximo,
            int nivel
    ) {
        super(id, titulo, cupoMaximo);

        if (nivel < 1 || nivel > 3) {
            throw new IllegalArgumentException(
                    "El nivel debe estar entre 1 y 3."
            );
        }

        this.nivel = nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        return nivel * 3000.0;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO | "
                + ENTIDAD_EMISORA
                + " certifica que "
                + estudiante.getNombre()
                + " participó del curso '"
                + getTitulo()
                + "' (nivel "
                + nivel
                + ").";
    }

    public int getNivel() {
        return nivel;
    }
}