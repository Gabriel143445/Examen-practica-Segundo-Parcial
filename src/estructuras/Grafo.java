package estructuras;

public final class Grafo {
    private static final class Arista {
        Vertice destino; int peso; Arista siguiente;
        Arista(Vertice destino, int peso) { this.destino = destino; this.peso = peso; }
    }
    private static final class Vertice {
        String id; Arista primera, ultima; Vertice siguiente;
        Vertice(String id) { this.id = id; }
    }
    private Vertice primero, ultimo;
    private final boolean dirigido;
    private int cantidad;
    public Grafo(boolean dirigido) { this.dirigido = dirigido; }
    public int cantidadVertices() { return cantidad; }
    public boolean dirigido() { return dirigido; }
    public boolean contiene(String id) { return buscar(id) != null; }
    private Vertice buscar(String id) {
        if (id == null) return null;
        for (Vertice v = primero; v != null; v = v.siguiente) if (v.id.equals(id)) return v;
        return null;
    }
    private Vertice exigir(String id) {
        Vertice v = buscar(id);
        if (v == null) throw new IllegalArgumentException("Vértice inexistente: " + id);
        return v;
    }
    public void agregarVertice(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Identificador vacío");
        id = id.trim();
        if (contiene(id)) throw new IllegalArgumentException("Vértice duplicado: " + id);
        Vertice v = new Vertice(id);
        if (ultimo == null) primero = v; else ultimo.siguiente = v;
        ultimo = v; cantidad++;
    }
    private Arista buscarArista(Vertice origen, Vertice destino) {
        for (Arista a = origen.primera; a != null; a = a.siguiente) if (a.destino == destino) return a;
        return null;
    }
    public void conectar(String origen, String destino, int peso) {
        if (peso <= 0) throw new IllegalArgumentException("El peso debe ser positivo");
        Vertice a = exigir(origen), b = exigir(destino);
        if (a == b) throw new IllegalArgumentException("No se admiten lazos");
        if (buscarArista(a, b) != null) throw new IllegalArgumentException("Conexión duplicada");
        agregarArista(a, b, peso);
        if (!dirigido) agregarArista(b, a, peso);
    }
    private void agregarArista(Vertice origen, Vertice destino, int peso) {
        Arista a = new Arista(destino, peso);
        if (origen.ultima == null) origen.primera = a; else origen.ultima.siguiente = a;
        origen.ultima = a;
    }
    public boolean desconectar(String origen, String destino) {
        Vertice a = exigir(origen), b = exigir(destino);
        boolean existia = quitarArista(a, b);
        if (!dirigido && existia) quitarArista(b, a);
        return existia;
    }
    private boolean quitarArista(Vertice origen, Vertice destino) {
        Arista anterior = null;
        for (Arista a = origen.primera; a != null; a = a.siguiente) {
            if (a.destino == destino) {
                if (anterior == null) origen.primera = a.siguiente; else anterior.siguiente = a.siguiente;
                if (origen.ultima == a) origen.ultima = anterior;
                return true;
            }
            anterior = a;
        }
        return false;
    }
    public boolean eliminarVertice(String id) {
        Vertice objetivo = buscar(id);
        if (objetivo == null) return false;
        for (Vertice v = primero; v != null; v = v.siguiente) if (v != objetivo) quitarArista(v, objetivo);
        Vertice anterior = null;
        for (Vertice v = primero; v != objetivo; v = v.siguiente) anterior = v;
        if (anterior == null) primero = objetivo.siguiente; else anterior.siguiente = objetivo.siguiente;
        if (ultimo == objetivo) ultimo = anterior;
        cantidad--; return true;
    }
    public Vector<String> vertices() {
        Vector<String> resultado = new Vector<>();
        for (Vertice v = primero; v != null; v = v.siguiente) resultado.agregar(v.id);
        return resultado;
    }
    public Vector<String> vecinos(String id) {
        Vertice v = exigir(id); Vector<String> resultado = new Vector<>();
        for (Arista a = v.primera; a != null; a = a.siguiente) resultado.agregar(a.destino.id);
        return resultado;
    }
    public int peso(String origen, String destino) {
        Arista a = buscarArista(exigir(origen), exigir(destino));
        if (a == null) throw new IllegalArgumentException("Conexión inexistente");
        return a.peso;
    }
    private boolean contiene(Vector<String> lista, String id) {
        for (int i = 0; i < lista.tamano(); i++) if (lista.obtener(i).equals(id)) return true;
        return false;
    }
    public Vector<String> bfs(String origen) {
        exigir(origen); Vector<String> visitados = new Vector<>(); Cola<Vertice> cola = new Cola<>();
        visitados.agregar(origen); cola.encolar(buscar(origen));
        while (!cola.vacia()) {
            Vertice v = cola.desencolar();
            for (Arista a = v.primera; a != null; a = a.siguiente) if (!contiene(visitados, a.destino.id)) {
                visitados.agregar(a.destino.id); cola.encolar(a.destino);
            }
        }
        return visitados;
    }
    public Vector<String> dfs(String origen) {
        exigir(origen); Vector<String> visitados = new Vector<>(); dfs(buscar(origen), visitados); return visitados;
    }
    private void dfs(Vertice v, Vector<String> visitados) {
        visitados.agregar(v.id);
        for (Arista a = v.primera; a != null; a = a.siguiente)
            if (!contiene(visitados, a.destino.id)) dfs(a.destino, visitados);
    }
    public Vector<String> noAlcanzables(String origen) {
        Vector<String> alcanzados = bfs(origen), faltantes = new Vector<>();
        for (Vertice v = primero; v != null; v = v.siguiente)
            if (!contiene(alcanzados, v.id)) faltantes.agregar(v.id);
        return faltantes;
    }
    public boolean alcanzable(String origen, String destino) {
        exigir(destino); return contiene(bfs(origen), destino);
    }
    @Override public String toString() {
        StringBuilder s = new StringBuilder();
        for (Vertice v = primero; v != null; v = v.siguiente) {
            s.append(v.id).append(" -> ");
            for (Arista a = v.primera; a != null; a = a.siguiente) {
                if (a != v.primera) s.append(", ");
                s.append(a.destino.id).append('(').append(a.peso).append(')');
            }
            s.append('\n');
        }
        return s.toString();
    }
}
