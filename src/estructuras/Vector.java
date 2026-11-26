package estructuras;

public final class Vector<T> {
    private Object[] datos = new Object[4];
    private int tamano;

    public int tamano() { return tamano; }
    public boolean vacio() { return tamano == 0; }

    public void agregar(T valor) {
        if (valor == null) throw new IllegalArgumentException("No se admiten valores nulos");
        if (tamano == datos.length) {
            Object[] nuevo = new Object[datos.length * 2];
            for (int i = 0; i < tamano; i++) nuevo[i] = datos[i];
            datos = nuevo;
        }
        datos[tamano++] = valor;
    }

    @SuppressWarnings("unchecked") public T obtener(int indice) {
        validar(indice);
        return (T) datos[indice];
    }

    @SuppressWarnings("unchecked") public T eliminar(int indice) {
        validar(indice);
        T eliminado = (T) datos[indice];
        for (int i = indice; i < tamano - 1; i++) datos[i] = datos[i + 1];
        datos[--tamano] = null;
        return eliminado;
    }

    private void validar(int indice) {
        if (indice < 0 || indice >= tamano) throw new IndexOutOfBoundsException("Índice: " + indice);
    }

    @Override public String toString() {
        StringBuilder texto = new StringBuilder("[");
        for (int i = 0; i < tamano; i++) {
            if (i > 0) texto.append(", ");
            texto.append(datos[i]);
        }
        return texto.append(']').toString();
    }
}
