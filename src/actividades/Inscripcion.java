package modelo;

import actividades.Actividad;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LocalDate fecha;
    private String estado;

    private final Estudiante estudiante;
    private final Actividad actividad;

    private TicketDeAcceso ticket;

    public Inscripcion(
            Estudiante estudiante,
            Actividad actividad
    ) {
        this.fecha = LocalDate.now();
        this.estado = "PENDIENTE";
        this.estudiante = estudiante;
        this.actividad = actividad;
    }

    public void confirmar() {
        estado = "CONFIRMADA";
    }

    public TicketDeAcceso emitirTicket() {
        if (!estaConfirmada()) {
            throw new IllegalStateException(
                    "Solo se emiten tickets para "
                            + "inscripciones confirmadas."
            );
        }

        if (ticket == null) {
            ticket = new TicketDeAcceso();
        }

        return ticket;
    }

    public boolean estaConfirmada() {
        return "CONFIRMADA".equals(estado);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public TicketDeAcceso getTicket() {
        return ticket;
    }

    @Override
    public String toString() {
        return estudiante
                + " - "
                + actividad.getTitulo()
                + " ["
                + estado
                + "]";
    }

    public class TicketDeAcceso implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String idTicket;
        private final LocalDate fechaEmision;

        private TicketDeAcceso() {
            idTicket = UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase();

            fechaEmision = LocalDate.now();
        }

        public void enviarTicket() {
            System.out.printf(
                    "[%s] Ticket %s enviado a %s para '%s'%n",
                    Thread.currentThread().getName(),
                    idTicket,
                    estudiante.getNombre(),
                    actividad.getTitulo()
            );
        }

        public String getIdTicket() {
            return idTicket;
        }

        public LocalDate getFechaEmision() {
            return fechaEmision;
        }

        @Override
        public String toString() {
            return "Ticket "
                    + idTicket
                    + " | "
                    + estudiante.getNombre()
                    + " | "
                    + actividad.getTitulo()
                    + " | "
                    + fechaEmision;
        }
    }
}