# Trabajo Práctico N.º 2 - Programación Orientada a Objetos

## Datos de la alumna

- **Alumna:** Zoé Castillo
- **Legajo:** 53321
- **Materia:** Paradigmas de Programación
- **Universidad:** Universidad Tecnológica Nacional - Facultad Regional Mendoza
- **Año:** 2026

## Descripción

Sistema de gestión de eventos universitarios desarrollado en Java aplicando conceptos de Programación Orientada a Objetos.

El proyecto permite crear eventos, asignar salas, agregar diferentes tipos de actividades, inscribir estudiantes, emitir certificados y generar tickets de acceso.

## Contenidos implementados

### Ejercicio 1

- Organización del proyecto mediante paquetes.
- Excepción chequeada `CupoExcedidoException`.
- Uso de `throw`, `throws`, `try`, `catch` y `finally`.
- Serialización y deserialización de eventos.
- Manejo granular de excepciones de persistencia.
- Caso exitoso y caso fallido controlado.

### Ejercicio 2

- Interfaz `Certificable`.
- Emisión de certificados para talleres y cursos.
- Las charlas no permiten emitir certificados.
- Incorporación del tipo de actividad `Curso`.

### Ejercicio 3

- Métodos genéricos acotados.
- Uso de wildcards.
- Filtrado de actividades por tipo.
- Creación de listas correctamente tipadas:
  - `List<Charla>`
  - `List<Taller>`
  - `List<Curso>`
- Cálculo del costo de materiales de cada tipo de actividad.

### Ejercicio 4

- Clase anidada `Inscripcion.TicketDeAcceso`.
- Generación de tickets para inscripciones confirmadas.
- Ejecución concurrente mediante `EnvioTicketsThread`.
- Uso de los métodos `start()`, `run()` y `join()`.
- Ejecución simultánea del hilo principal y el hilo de envío de tickets.

## Estructura del proyecto

```text
src
├── actividades
│   ├── Actividad.java
│   ├── Charla.java
│   ├── Curso.java
│   └── Taller.java
├── app
│   └── App.java
├── certificacion
│   └── Certificable.java
├── excepciones
│   └── CupoExcedidoException.java
├── hilos
│   └── EnvioTicketsThread.java
└── modelo
    ├── Estudiante.java
    ├── EventoUniversitario.java
    ├── Inscripcion.java
    └── Sala.java

#Ejecución
Clonar el repositorio.
Abrir el proyecto en IntelliJ IDEA.
Configurar un JDK 17 o superior.
Ejecutar la clase App, ubicada en el paquete app.

Durante la ejecución se crea una carpeta llamada datos, utilizada para guardar el evento serializado.

#Resultado

La ejecución muestra por consola:

Inscripciones exitosas.
Un caso de cupo excedido controlado mediante una excepción.
Persistencia y recuperación del evento.
Certificados emitidos.
Filtrado de actividades mediante genéricos.
Costos de materiales.
Generación y envío concurrente de tickets.
