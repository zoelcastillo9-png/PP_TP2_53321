package certificacion;

import modelo.Estudiante;

public interface Certificable {

    String ENTIDAD_EMISORA =
            "Universidad Tecnológica Nacional - FRM";

    String generarCertificado(Estudiante estudiante);
}