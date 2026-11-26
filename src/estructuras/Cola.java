package estructuras;

public final class Cola<T> {
    private static final class Nodo<T> { T dato; Nodo<T> siguiente; Nodo(T dato) { this.dato = dato; } }
    private Nodo<T> primero, ultimo;
    private int tamano;
    public int tamano() { return tamano; }
    public boolean vacia() { return primero == null; }
    public void encolar(T dato) {
        if (dato == null) throw new IllegalArgumentException("No se admiten valores nulos");
        Nodo<T> nuevo = new Nodo<>(dato);
        if (ultimo == null) primero = nuevo;
        else ultimo.siguiente = nuevo;
        ultimo = nuevo; tamano++;
    }
    public T desencolar() {
        if (vacia()) throw new IllegalStateException("La cola está vacía");
        T dato = primero.dato; primero = primero.siguiente; tamano--;
        if (primero == null) ultimo = null;
        return dato;
    }
}
