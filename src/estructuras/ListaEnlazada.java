package estructuras;

public final class ListaEnlazada<T> {
    private static final class Nodo<T> {
        T dato; Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }
    private Nodo<T> cabeza, cola;
    private int tamano;

    public int tamano() { return tamano; }
    public boolean vacia() { return tamano == 0; }
    public void agregar(T dato) {
        if (dato == null) throw new IllegalArgumentException("No se admiten valores nulos");
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cola == null) cabeza = nuevo;
        else cola.siguiente = nuevo;
        cola = nuevo;
        tamano++;
    }
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) throw new IndexOutOfBoundsException("Índice: " + indice);
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) actual = actual.siguiente;
        return actual.dato;
    }
    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamano) throw new IndexOutOfBoundsException("Índice: " + indice);
        Nodo<T> quitado;
        if (indice == 0) { quitado = cabeza; cabeza = cabeza.siguiente; }
        else {
            Nodo<T> anterior = cabeza;
            for (int i = 1; i < indice; i++) anterior = anterior.siguiente;
            quitado = anterior.siguiente;
            anterior.siguiente = quitado.siguiente;
            if (quitado == cola) cola = anterior;
        }
        tamano--;
        if (tamano == 0) cola = null;
        return quitado.dato;
    }
    public void limpiar() { cabeza = cola = null; tamano = 0; }
    @Override public String toString() {
        StringBuilder texto = new StringBuilder("[");
        for (Nodo<T> n = cabeza; n != null; n = n.siguiente) {
            if (n != cabeza) texto.append(", ");
            texto.append(n.dato);
        }
        return texto.append(']').toString();
    }
}
