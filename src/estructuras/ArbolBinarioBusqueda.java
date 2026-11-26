package estructuras;

import modelo.Estudiante;

public final class ArbolBinarioBusqueda {
    private static final class Nodo {
        Estudiante dato; Nodo izquierdo, derecho;
        Nodo(Estudiante dato) { this.dato = dato; }
    }
    private Nodo raiz;
    private int tamano;
    public int tamano() { return tamano; }
    public Estudiante buscar(int matricula) {
        Nodo n = raiz;
        while (n != null) {
            if (matricula == n.dato.matricula()) return n.dato;
            n = matricula < n.dato.matricula() ? n.izquierdo : n.derecho;
        }
        return null;
    }
    public void insertar(Estudiante dato) {
        if (dato == null) throw new IllegalArgumentException("Estudiante nulo");
        raiz = insertar(raiz, dato); tamano++;
    }
    private Nodo insertar(Nodo n, Estudiante dato) {
        if (n == null) return new Nodo(dato);
        if (dato.matricula() == n.dato.matricula()) throw new IllegalArgumentException("Matrícula duplicada");
        if (dato.matricula() < n.dato.matricula()) n.izquierdo = insertar(n.izquierdo, dato);
        else n.derecho = insertar(n.derecho, dato);
        return n;
    }
    public boolean eliminar(int matricula) {
        if (buscar(matricula) == null) return false;
        raiz = eliminar(raiz, matricula); tamano--; return true;
    }
    private Nodo eliminar(Nodo n, int matricula) {
        if (matricula < n.dato.matricula()) n.izquierdo = eliminar(n.izquierdo, matricula);
        else if (matricula > n.dato.matricula()) n.derecho = eliminar(n.derecho, matricula);
        else {
            if (n.izquierdo == null) return n.derecho;
            if (n.derecho == null) return n.izquierdo;
            Nodo sucesor = n.derecho;
            while (sucesor.izquierdo != null) sucesor = sucesor.izquierdo;
            n.dato = sucesor.dato;
            n.derecho = eliminar(n.derecho, sucesor.dato.matricula());
        }
        return n;
    }
    public Vector<Estudiante> inorden() { Vector<Estudiante> v = new Vector<>(); recorrer(raiz, v, 0); return v; }
    public Vector<Estudiante> preorden() { Vector<Estudiante> v = new Vector<>(); recorrer(raiz, v, 1); return v; }
    public Vector<Estudiante> posorden() { Vector<Estudiante> v = new Vector<>(); recorrer(raiz, v, 2); return v; }
    private void recorrer(Nodo n, Vector<Estudiante> v, int orden) {
        if (n == null) return;
        if (orden == 1) v.agregar(n.dato);
        recorrer(n.izquierdo, v, orden);
        if (orden == 0) v.agregar(n.dato);
        recorrer(n.derecho, v, orden);
        if (orden == 2) v.agregar(n.dato);
    }
}
