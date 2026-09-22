package actividades;

public class Charla extends Actividad {

    private static final long serialVersionUID = 1L;

    private final String disertante;

    public Charla(
            int id,
            String titulo,
            int cupoMaximo,
            String disertante
    ) {
        super(id, titulo, cupoMaximo);

        if (disertante == null || disertante.isBlank()) {
            throw new IllegalArgumentException(
                    "El disertante es obligatorio."
            );
        }

        this.disertante = disertante;
    }

    @Override
    public double calcularCostoMateriales() {
        return 0;
    }

    @Override
    public String getTipo() {
        return "Charla";
    }

    public String getDisertante() {
        return disertante;
    }
}
