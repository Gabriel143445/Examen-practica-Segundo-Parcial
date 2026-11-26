package servicios;

import estructuras.ArbolBinarioBusqueda;
import estructuras.ArbolGeneral;
import estructuras.ListaEnlazada;
import estructuras.Pila;
import estructuras.Vector;
import modelo.Estudiante;

public final class GestionAcademica {
    private enum Tipo { CREAR, ACTUALIZAR, ELIMINAR }
    private record Cambio(Tipo tipo, int matricula, String nombreAnterior) {}
    private final Vector<Estudiante> vector = new Vector<>();
    private final ListaEnlazada<Estudiante> lista = new ListaEnlazada<>();
    private final ArbolBinarioBusqueda indice = new ArbolBinarioBusqueda();
    private final Pila<Cambio> historial = new Pila<>();
    private final ArbolGeneral jerarquia = new ArbolGeneral("Universidad Técnica de Ambato");
    private final ArbolGeneral.Nodo carrera;
    private final ListaEnlazada<ArbolGeneral.Nodo> nodos = new ListaEnlazada<>();

    public GestionAcademica() {
        ArbolGeneral.Nodo facultad = jerarquia.agregar(jerarquia.raiz(), "FISEI");
        carrera = jerarquia.agregar(facultad, "Ingeniería en Software");
    }
    public int total() { return vector.tamano(); }
    public int operacionesReversibles() { return historial.tamano(); }
    public Vector<Estudiante> estudiantes() { return vector; }
    public ListaEnlazada<Estudiante> lista() { return lista; }
    public ArbolBinarioBusqueda indice() { return indice; }
    public String jerarquia() { return jerarquia.mostrar(); }
    public Estudiante busquedaSecuencial(int matricula) {
        for (int i = 0; i < vector.tamano(); i++) if (vector.obtener(i).matricula() == matricula) return vector.obtener(i);
        return null;
    }
    public Estudiante buscar(int matricula) { return indice.buscar(matricula); }
    public void crear(int matricula, String nombre) {
        Estudiante nuevo = new Estudiante(matricula, nombre);
        if (buscar(matricula) != null) throw new IllegalArgumentException("Matrícula duplicada: " + matricula);
        insertar(nuevo); historial.apilar(new Cambio(Tipo.CREAR, matricula, null));
    }
    private void insertar(Estudiante e) {
        indice.insertar(e); vector.agregar(e); lista.agregar(e);
        nodos.agregar(jerarquia.agregar(carrera, e.toString()));
    }
    public void actualizar(int matricula, String nombre) {
        Estudiante e = buscar(matricula);
        if (e == null) throw new IllegalArgumentException("Matrícula inexistente: " + matricula);
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre obligatorio");
        String anterior = e.nombre();
        int pos = posicion(matricula);
        e.cambiarNombre(nombre);
        jerarquia.renombrar(nodos.obtener(pos), e.toString());
        historial.apilar(new Cambio(Tipo.ACTUALIZAR, matricula, anterior));
    }
    private int posicion(int matricula) {
        for (int i = 0; i < vector.tamano(); i++) if (vector.obtener(i).matricula() == matricula) return i;
        throw new IllegalArgumentException("Matrícula inexistente: " + matricula);
    }
    public Estudiante eliminar(int matricula) {
        Estudiante e = buscar(matricula);
        if (e == null) throw new IllegalArgumentException("Matrícula inexistente: " + matricula);
        quitar(matricula);
        historial.apilar(new Cambio(Tipo.ELIMINAR, matricula, e.nombre()));
        return e;
    }
    private void quitar(int matricula) {
        int pos = posicion(matricula);
        indice.eliminar(matricula); vector.eliminar(pos); lista.eliminar(pos);
        jerarquia.eliminarHoja(nodos.eliminar(pos));
    }
    public String deshacer() {
        Cambio c = historial.desapilar();
        switch (c.tipo) {
            case CREAR -> quitar(c.matricula);
            case ELIMINAR -> insertar(new Estudiante(c.matricula, c.nombreAnterior));
            case ACTUALIZAR -> {
                Estudiante e = buscar(c.matricula);
                int pos = posicion(c.matricula);
                e.cambiarNombre(c.nombreAnterior);
                jerarquia.renombrar(nodos.obtener(pos), e.toString());
            }
        }
        return c.tipo + " matrícula " + c.matricula;
    }
}
