package estructuras;

public final class Pila<T> {
    private static final class Nodo<T> { T dato; Nodo<T> siguiente; Nodo(T dato, Nodo<T> siguiente) { this.dato = dato; this.siguiente = siguiente; } }
    private Nodo<T> cima;
    private int tamano;
    public int tamano() { return tamano; }
    public boolean vacia() { return cima == null; }
    public void apilar(T dato) {
        if (dato == null) throw new IllegalArgumentException("No se admiten valores nulos");
        cima = new Nodo<>(dato, cima); tamano++;
    }
    public T desapilar() {
        if (vacia()) throw new IllegalStateException("La pila está vacía");
        T dato = cima.dato; cima = cima.siguiente; tamano--; return dato;
    }
}
