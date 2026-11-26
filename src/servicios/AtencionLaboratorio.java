package servicios;

import estructuras.Cola;
import modelo.Solicitud;

public final class AtencionLaboratorio {
    private final Cola<Solicitud> pendientes = new Cola<>();
    private int siguiente = 1;
    public Solicitud solicitar(String estudiante, String equipo) {
        Solicitud s = new Solicitud(siguiente, estudiante, equipo);
        siguiente++; pendientes.encolar(s); return s;
    }
    public Solicitud atender() { return pendientes.desencolar(); }
    public int pendientes() { return pendientes.tamano(); }
}
