package pruebas;

import servicios.SistemaSigac;

public final class CasoIntegrador {
    public static void main(String[] args) {
        SistemaSigac s = SistemaSigac.casoIntegrador();
        System.out.println("ABB inorden inicial: " + s.estudiantes.indice().inorden());
        s.solicitarEquipo(50, "PC1");
        s.solicitarEquipo(30, "PC2");
        s.solicitarEquipo(70, "PC3");
        s.estudiantes.eliminar(30);
        System.out.println("ABB tras eliminar 30: " + s.estudiantes.indice().inorden());
        System.out.println("FIFO: " + s.laboratorio.atender() + " | " + s.laboratorio.atender() + " | " + s.laboratorio.atender());

        s.estudiantes.actualizar(40, "Nombre temporal");
        System.out.println("LIFO 1: " + s.estudiantes.deshacer());
        System.out.println("LIFO 2: " + s.estudiantes.deshacer());
        System.out.println("ABB restaurado: " + s.estudiantes.indice().inorden());

        System.out.println("BFS desde PC1: " + s.red.bfs("PC1"));
        System.out.println("DFS desde PC1: " + s.red.dfs("PC1"));
        System.out.println("No alcanzables desde PC1: " + s.red.noAlcanzables("PC1"));
        System.out.println("Recomendaciones para Ana: " + s.social.recomendar("Ana"));
        s.red.desconectar("SW1", "SRV1");
        System.out.println("No alcanzables tras quitar SW1 -> SRV1: " + s.red.noAlcanzables("PC1"));
    }
}
