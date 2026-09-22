package app;

import actividades.Actividad;
import actividades.Charla;
import actividades.Curso;
import actividades.Taller;

import certificacion.Certificable;
import excepciones.CupoExcedidoException;
import hilos.EnvioTicketsThread;

import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.io.IOException;

import java.util.List;

public class App {

    public static void main(String[] args) {

        System.out.println(
                "=== TRABAJO PRÁCTICO 2 "
                        + "- SISTEMA DE EVENTOS ===\n"
        );

        Estudiante ana =
                new Estudiante("50101", "Ana Torres");

        Estudiante bruno =
                new Estudiante("50102", "Bruno Díaz");

        Estudiante clara =
                new Estudiante("50103", "Clara Ruiz");

        EventoUniversitario evento =
                new EventoUniversitario(
                        "EVT-01",
                        "Jornadas de Tecnología",
                        15000,
                        false
                );

        evento.asignarSala(
                new Sala(
                        1,
                        "Auditorio Central"
                )
        );

        Charla charla =
                new Charla(
                        1,
                        "Inteligencia Artificial",
                        3,
                        "Dra. Laura Paz"
                );

        Taller taller =
                new Taller(
                        2,
                        "Git y GitHub",
                        2,
                        true
                );

        Curso curso =
                new Curso(
                        3,
                        "Java Orientado a Objetos",
                        2,
                        2
                );

        evento.agregarActividad(charla);
        evento.agregarActividad(taller);
        evento.agregarActividad(curso);

        ejecutarFlujoPersistencia(
                evento,
                charla,
                ana
        );

        demostrarCupoExcedido(
                taller,
                ana,
                bruno,
                clara
        );

        inscribirYConfirmar(curso, ana);
        inscribirYConfirmar(curso, clara);

        emitirCertificados(evento);
        demostrarGenericos(evento);
        iniciarEnvioConcurrente(evento);
    }

    private static void ejecutarFlujoPersistencia(
            EventoUniversitario evento,
            Actividad actividad,
            Estudiante estudiante
    ) {
        System.out.println(
                "--- EJERCICIO 1: "
                        + "EXCEPCIÓN Y PERSISTENCIA ---"
        );

        try {
            Inscripcion inscripcion =
                    actividad.inscribir(estudiante);

            inscripcion.confirmar();
            inscripcion.emitirTicket();

            System.out.println(
                    "Inscripción exitosa: "
                            + inscripcion
            );

            evento.persistirEvento();

            System.out.println(
                    "Evento guardado correctamente."
            );

            EventoUniversitario recuperado =
                    EventoUniversitario
                            .recuperarEvento(
                                    evento.getId()
                            );

            System.out.println(
                    "Evento recuperado: "
                            + recuperado.getTitulo()
            );

        } catch (CupoExcedidoException e) {
            System.out.println(
                    "Error de cupo: "
                            + e.getMessage()
            );

        } catch (FileNotFoundException e) {
            System.out.println(
                    "No se encontró el archivo: "
                            + e.getMessage()
            );

        } catch (InvalidClassException e) {
            System.out.println(
                    "La versión de la clase "
                            + "no coincide con el archivo: "
                            + e.getMessage()
            );

        } catch (EOFException e) {
            System.out.println(
                    "El archivo está vacío "
                            + "o incompleto: "
                            + e.getMessage()
            );

        } catch (ClassNotFoundException e) {
            System.out.println(
                    "No se pudo reconstruir "
                            + "una clase guardada: "
                            + e.getMessage()
            );

        } catch (IOException e) {
            System.out.println(
                    "Error de entrada/salida: "
                            + e.getMessage()
            );

        } finally {
            System.out.println(
                    "Flujo de inscripción y "
                            + "persistencia finalizado.\n"
            );
        }
    }

    private static void demostrarCupoExcedido(
            Taller taller,
            Estudiante... estudiantes
    ) {
        System.out.println(
                "--- CASO FALLIDO CONTROLADO: "
                        + "CUPO EXCEDIDO ---"
        );

        for (Estudiante estudiante : estudiantes) {
            try {
                Inscripcion inscripcion =
                        taller.inscribir(estudiante);

                inscripcion.confirmar();
                inscripcion.emitirTicket();

                System.out.println(
                        "Inscripto: "
                                + estudiante.getNombre()
                );

            } catch (CupoExcedidoException e) {
                System.out.println(
                        "Excepción controlada: "
                                + e.getMessage()
                );
            }
        }

        System.out.println();
    }

    private static void inscribirYConfirmar(
            Actividad actividad,
            Estudiante estudiante
    ) {
        try {
            Inscripcion inscripcion =
                    actividad.inscribir(estudiante);

            inscripcion.confirmar();
            inscripcion.emitirTicket();

        } catch (CupoExcedidoException e) {
            System.out.println(
                    "No se pudo inscribir a "
                            + estudiante.getNombre()
                            + ": "
                            + e.getMessage()
            );
        }
    }

    private static void emitirCertificados(
            EventoUniversitario evento
    ) {
        System.out.println(
                "--- EJERCICIO 2: CERTIFICADOS ---"
        );

        for (Actividad actividad :
                evento.getActividades()) {

            if (actividad
                    instanceof Certificable certificable) {

                for (Inscripcion inscripcion :
                        actividad.getInscripciones()) {

                    if (inscripcion.estaConfirmada()) {
                        String certificado =
                                certificable
                                        .generarCertificado(
                                                inscripcion
                                                        .getEstudiante()
                                        );

                        System.out.println(certificado);
                    }
                }

            } else {
                System.out.println(
                        "La charla '"
                                + actividad.getTitulo()
                                + "' no es certificable."
                );
            }
        }

        System.out.println();
    }

    private static void demostrarGenericos(
            EventoUniversitario evento
    ) {
        System.out.println(
                "--- EJERCICIO 3: "
                        + "GENÉRICOS Y WILDCARDS ---"
        );

        List<Charla> charlas =
                evento.filtrarActividadesPorTipo(
                        Charla.class
                );

        List<Taller> talleres =
                evento.filtrarActividadesPorTipo(
                        Taller.class
                );

        List<Curso> cursos =
                evento.filtrarActividadesPorTipo(
                        Curso.class
                );

        mostrarResumenTipo(
                "charlas",
                charlas,
                evento
        );

        mostrarResumenTipo(
                "talleres",
                talleres,
                evento
        );

        mostrarResumenTipo(
                "cursos",
                cursos,
                evento
        );

        System.out.println();
    }

    private static void mostrarResumenTipo(
            String nombre,
            List<? extends Actividad> actividades,
            EventoUniversitario evento
    ) {
        System.out.printf(
                "Cantidad de %s: %d | "
                        + "costo de materiales: $%.2f%n",
                nombre,
                actividades.size(),
                evento.calcularCostoMateriales(
                        actividades
                )
        );
    }

    private static void iniciarEnvioConcurrente(
            EventoUniversitario evento
    ) {
        System.out.println(
                "--- EJERCICIO 4: "
                        + "TICKETS E HILOS ---"
        );

        EnvioTicketsThread hilo =
                new EnvioTicketsThread(evento);

        hilo.start();

        System.out.println(
                "["
                        + Thread.currentThread().getName()
                        + "] El hilo principal continúa:"
        );

        evento.mostrarDatos();

        try {
            hilo.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            System.out.println(
                    "El hilo principal fue interrumpido."
            );
        }

        System.out.println(
                "\nPrograma finalizado correctamente."
        );
    }
}
