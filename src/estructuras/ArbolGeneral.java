package estructuras;

public final class ArbolGeneral {
    public static final class Nodo {
        private String nombre;
        private final ListaEnlazada<Nodo> hijos = new ListaEnlazada<>();
        private Nodo padre;
        private Nodo(String nombre) { this.nombre = nombre; }
        public String nombre() { return nombre; }
        public int numeroHijos() { return hijos.tamano(); }
        public Nodo hijo(int indice) { return hijos.obtener(indice); }
    }
    private final Nodo raiz;
    public ArbolGeneral(String nombreRaiz) { raiz = new Nodo(validar(nombreRaiz)); }
    public Nodo raiz() { return raiz; }
    public Nodo agregar(Nodo padre, String nombre) {
        if (padre == null || !pertenece(raiz, padre)) throw new IllegalArgumentException("Padre inexistente");
        Nodo hijo = new Nodo(validar(nombre)); hijo.padre = padre; padre.hijos.agregar(hijo); return hijo;
    }
    public void renombrar(Nodo nodo, String nombre) {
        if (nodo == null || !pertenece(raiz, nodo)) throw new IllegalArgumentException("Nodo inexistente");
        nodo.nombre = validar(nombre);
    }
    public boolean eliminarHoja(Nodo nodo) {
        if (nodo == null || nodo == raiz || nodo.numeroHijos() != 0 || !pertenece(raiz, nodo)) return false;
        Nodo padre = nodo.padre;
        for (int i = 0; i < padre.numeroHijos(); i++) if (padre.hijo(i) == nodo) {
            padre.hijos.eliminar(i); nodo.padre = null; return true;
        }
        return false;
    }
    private boolean pertenece(Nodo actual, Nodo objetivo) {
        if (actual == objetivo) return true;
        for (int i = 0; i < actual.numeroHijos(); i++) if (pertenece(actual.hijo(i), objetivo)) return true;
        return false;
    }
    private String validar(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre obligatorio");
        return nombre.trim();
    }
    public String mostrar() { StringBuilder s = new StringBuilder(); mostrar(raiz, 0, s); return s.toString(); }
    private void mostrar(Nodo n, int nivel, StringBuilder s) {
        s.append("  ".repeat(nivel)).append(n.nombre).append('\n');
        for (int i = 0; i < n.numeroHijos(); i++) mostrar(n.hijo(i), nivel + 1, s);
    }
}
