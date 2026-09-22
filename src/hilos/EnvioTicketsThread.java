package hilos;

import actividades.Actividad;
import modelo.EventoUniversitario;
import modelo.Inscripcion;

public class EnvioTicketsThread extends Thread {

    private final EventoUniversitario evento;

    public EnvioTicketsThread(
            EventoUniversitario evento
    ) {
        super("HILO-ENVIO-TICKETS");
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println(
                "[HILO-ENVIO-TICKETS] "
                        + "Comienza el envío del evento "
                        + evento.getTitulo()
        );

        for (Actividad actividad :
                evento.getActividades()) {

            for (Inscripcion inscripcion :
                    actividad.getInscripciones()) {

                if (inscripcion.getTicket() != null) {
                    try {
                        Thread.sleep(350);

                        inscripcion
                                .getTicket()
                                .enviarTicket();

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();

                        System.out.println(
                                "[HILO-ENVIO-TICKETS] "
                                        + "Envío interrumpido."
                        );

                        return;
                    }
                }
            }
        }

        System.out.println(
                "[HILO-ENVIO-TICKETS] Envío finalizado."
        );
    }
}