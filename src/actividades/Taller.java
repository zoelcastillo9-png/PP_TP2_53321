package actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Taller extends Actividad
        implements Certificable {

    private static final long serialVersionUID = 1L;

    private final boolean requiereNotebook;

    public Taller(
            int id,
            String titulo,
            int cupoMaximo,
            boolean requiereNotebook
    ) {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        if (requiereNotebook) {
            return 5000;
        }

        return 2000;
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO | "
                + ENTIDAD_EMISORA
                + " certifica que "
                + estudiante.getNombre()
                + " participó del taller '"
                + getTitulo()
                + "'.";
    }

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }
}
