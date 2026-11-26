package pruebas;

import estructuras.ArbolBinarioBusqueda;
import estructuras.Cola;
import estructuras.Grafo;
import estructuras.Pila;
import modelo.Estudiante;
import servicios.GestionAcademica;
import servicios.SistemaSigac;

public final class Pruebas {
    private static int verificaciones;
    private static void comprobar(boolean condicion, String mensaje) {
        verificaciones++;
        if (!condicion) throw new AssertionError(mensaje);
    }
    private static void falla(Runnable operacion, String mensaje) {
        boolean fallo = false;
        try { operacion.run(); } catch (IllegalArgumentException | IllegalStateException e) { fallo = true; }
        comprobar(fallo, mensaje);
    }
    private static String matriculas(ArbolBinarioBusqueda abb) {
        StringBuilder s = new StringBuilder();
        var v = abb.inorden();
        for (int i = 0; i < v.tamano(); i++) {
            if (i > 0) s.append(',');
            s.append(v.obtener(i).matricula());
        }
        return s.toString();
    }
    public static void main(String[] args) {
        SistemaSigac s = SistemaSigac.casoIntegrador();
        comprobar(s.estudiantes.total() == 7, "Siete estudiantes");
        comprobar(matriculas(s.estudiantes.indice()).equals("20,30,40,50,60,70,80"), "Inorden inicial");
        comprobar(s.estudiantes.busquedaSecuencial(40) == s.estudiantes.buscar(40), "Búsquedas equivalentes");
        falla(() -> s.estudiantes.crear(50, "Duplicado"), "Duplicado rechazado");
        falla(() -> s.estudiantes.crear(0, "Inválido"), "Matrícula cero rechazada");
        falla(() -> s.estudiantes.crear(90, " "), "Nombre vacío rechazado");
        s.estudiantes.eliminar(30);
        comprobar(matriculas(s.estudiantes.indice()).equals("20,40,50,60,70,80"), "Eliminar nodo con dos hijos");
        comprobar(s.estudiantes.busquedaSecuencial(30) == null, "Eliminación en vector");
        comprobar(s.estudiantes.lista().tamano() == 6, "Eliminación en lista");
        s.estudiantes.deshacer();
        comprobar(s.estudiantes.buscar(30) != null, "Deshacer eliminación");
        s.estudiantes.actualizar(30, "Nuevo nombre");
        comprobar(s.estudiantes.jerarquia().contains("30 - Nuevo nombre"), "Jerarquía actualizada");
        s.estudiantes.deshacer();
        comprobar(s.estudiantes.buscar(30).nombre().equals("Beto"), "Deshacer actualización");
        s.estudiantes.crear(90, "Temporal"); s.estudiantes.deshacer();
        comprobar(s.estudiantes.buscar(90) == null, "Deshacer creación");
        falla(() -> s.estudiantes.eliminar(999), "Eliminar inexistente");
        falla(() -> new GestionAcademica().deshacer(), "Pila vacía");
        s.solicitarEquipo(50, "PC1");
        comprobar(s.laboratorio.atender().estudiante().equals("Ana [50]"), "Solicitud integrada con matrícula");
        falla(() -> s.solicitarEquipo(999, "PC1"), "Solicitud con matrícula inexistente");
        falla(() -> s.solicitarEquipo(50, "NO"), "Solicitud con equipo inexistente");

        var lab = s.laboratorio;
        lab.solicitar("Ana", "PC1"); lab.solicitar("Beto", "PC2"); lab.solicitar("Carla", "PC3");
        comprobar(lab.atender().estudiante().equals("Ana"), "FIFO 1");
        comprobar(lab.atender().estudiante().equals("Beto"), "FIFO 2");
        comprobar(lab.atender().estudiante().equals("Carla"), "FIFO 3");
        falla(lab::atender, "Cola vacía");
        Pila<Integer> pila = new Pila<>(); pila.apilar(1); pila.apilar(2);
        comprobar(pila.desapilar() == 2 && pila.desapilar() == 1, "LIFO");
        falla(pila::desapilar, "Pila vacía independiente");
        Cola<Integer> cola = new Cola<>(); falla(cola::desencolar, "Cola vacía independiente");

        comprobar(s.red.cantidadVertices() == 6, "Seis equipos");
        comprobar(s.red.bfs("PC1").toString().equals("[PC1, SW1, PC3, SRV1, PC2]"), "BFS y orden de vecinos");
        comprobar(s.red.dfs("PC1").toString().equals("[PC1, SW1, SRV1, PC2, PC3]"), "DFS");
        comprobar(s.red.noAlcanzables("PC1").toString().equals("[PC4]"), "PC4 aislado");
        comprobar(s.red.peso("SW1", "SRV1") == 2, "Peso conservado");
        comprobar(s.red.desconectar("SW1", "SRV1"), "Eliminar arista");
        comprobar(s.red.noAlcanzables("PC1").toString().equals("[SRV1, PC2, PC4]"), "Conectividad actualizada");
        comprobar(!s.red.desconectar("SW1", "SRV1"), "Arista ausente");
        falla(() -> s.red.bfs("NO"), "Origen inexistente");
        falla(() -> s.red.conectar("PC1", "NO", 1), "Destino inexistente");
        falla(() -> s.red.conectar("PC1", "PC2", 0), "Peso inválido");
        Grafo g = new Grafo(true); g.agregarVertice("A"); g.agregarVertice("B"); g.agregarVertice("C");
        g.conectar("A", "B", 1); g.conectar("C", "B", 1); g.eliminarVertice("B");
        comprobar(g.vecinos("A").vacio() && g.vecinos("C").vacio(), "Eliminar aristas entrantes");
        comprobar(!g.eliminarVertice("B"), "Vértice ausente");

        var r = s.social.recomendar("Ana");
        comprobar(r.tamano() == 2, "Dos recomendaciones");
        comprobar(r.obtener(0).persona().equals("Diego") && r.obtener(0).amigosComunes() == 2, "Diego con dos comunes");
        comprobar(r.obtener(1).persona().equals("Elena") && r.obtener(1).amigosComunes() == 1, "Elena con uno");
        comprobar(s.social.recomendar("Diego").toString().contains("Ana"), "Amistad no dirigida");
        falla(() -> s.social.recomendar("Nadie"), "Usuario social inexistente");
        System.out.println("OK: " + verificaciones + " verificaciones.");
    }
}
