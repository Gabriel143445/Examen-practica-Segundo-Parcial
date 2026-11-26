package app;

import servicios.SistemaSigac;
import java.util.Scanner;

public final class Main {
    private static final Scanner entrada = new Scanner(System.in);
    private SistemaSigac sistema = new SistemaSigac();

    public static void main(String[] args) { new Main().ejecutar(); }
    private String leer(String mensaje) { System.out.print(mensaje); return entrada.nextLine().trim(); }
    private int entero(String mensaje) {
        try { return Integer.parseInt(leer(mensaje)); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Se requiere un número entero"); }
    }
    private void ejecutar() {
        while (true) {
            System.out.println("\n=== SIGAC-UTA ===");
            System.out.println("1 Cargar caso integrador | 2 Crear estudiante | 3 Buscar matrícula | 4 Actualizar estudiante");
            System.out.println("5 Eliminar estudiante | 6 Listar y recorrer ABB | 7 Ver jerarquía | 8 Deshacer");
            System.out.println("9 Solicitar equipo | 10 Atender solicitud | 11 Agregar equipo | 12 Conectar equipos");
            System.out.println("13 Desconectar equipos | 14 Eliminar equipo | 15 Ver red/BFS/DFS/no alcanzables");
            System.out.println("16 Agregar persona | 17 Registrar amistad | 18 Recomendar | 0 Salir");
            try {
                int opcion = entero("Opción: ");
                if (opcion == 0) return;
                atender(opcion);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Entrada finalizada."); return;
            }
        }
    }
    private void atender(int op) {
        switch (op) {
            case 1 -> { sistema = SistemaSigac.casoIntegrador(); System.out.println("Caso integrador cargado."); }
            case 2 -> { sistema.estudiantes.crear(entero("Matrícula: "), leer("Nombre: ")); System.out.println("Creado."); }
            case 3 -> {
                int m = entero("Matrícula: ");
                System.out.println("Vector: " + sistema.estudiantes.busquedaSecuencial(m));
                System.out.println("ABB: " + sistema.estudiantes.buscar(m));
            }
            case 4 -> { sistema.estudiantes.actualizar(entero("Matrícula: "), leer("Nuevo nombre: ")); System.out.println("Actualizado."); }
            case 5 -> System.out.println("Eliminado: " + sistema.estudiantes.eliminar(entero("Matrícula: ")));
            case 6 -> {
                System.out.println("Vector: " + sistema.estudiantes.estudiantes());
                System.out.println("Lista: " + sistema.estudiantes.lista());
                System.out.println("Inorden: " + sistema.estudiantes.indice().inorden());
                System.out.println("Preorden: " + sistema.estudiantes.indice().preorden());
                System.out.println("Posorden: " + sistema.estudiantes.indice().posorden());
            }
            case 7 -> System.out.print(sistema.estudiantes.jerarquia());
            case 8 -> System.out.println("Deshecho: " + sistema.estudiantes.deshacer());
            case 9 -> { sistema.solicitarEquipo(entero("Matrícula: "), leer("Equipo: ")); System.out.println("Solicitud encolada."); }
            case 10 -> System.out.println("Atendida: " + sistema.laboratorio.atender());
            case 11 -> { sistema.red.agregarVertice(leer("Equipo: ")); System.out.println("Equipo agregado."); }
            case 12 -> { sistema.red.conectar(leer("Origen: "), leer("Destino: "), entero("Peso positivo: ")); System.out.println("Conectados."); }
            case 13 -> System.out.println("Conexión eliminada: " + sistema.red.desconectar(leer("Origen: "), leer("Destino: ")));
            case 14 -> System.out.println("Equipo eliminado: " + sistema.red.eliminarVertice(leer("Equipo: ")));
            case 15 -> {
                System.out.print(sistema.red);
                String origen = leer("Origen del recorrido: ");
                System.out.println("BFS: " + sistema.red.bfs(origen));
                System.out.println("DFS: " + sistema.red.dfs(origen));
                System.out.println("No alcanzables: " + sistema.red.noAlcanzables(origen));
            }
            case 16 -> { sistema.registrarEnRedSocial(entero("Matrícula: ")); System.out.println("Estudiante agregado a la red social."); }
            case 17 -> { sistema.social.amistad(leer("Persona A: "), leer("Persona B: ")); System.out.println("Amistad registrada."); }
            case 18 -> System.out.println("Recomendaciones: " + sistema.social.recomendar(leer("Persona: ")));
            default -> System.out.println("Opción inexistente.");
        }
    }
}
