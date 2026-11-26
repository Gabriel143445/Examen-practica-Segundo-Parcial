package servicios;

import estructuras.Grafo;
import estructuras.Vector;

public final class Recomendador {
    public record Recomendacion(String persona, int amigosComunes) {
        @Override public String toString() { return persona + " (" + amigosComunes + " en común)"; }
    }
    private final Grafo social = new Grafo(false);
    public Grafo grafo() { return social; }
    public void agregarPersona(String nombre) { social.agregarVertice(nombre); }
    public void amistad(String a, String b) { social.conectar(a, b, 1); }
    public Vector<Recomendacion> recomendar(String usuario) {
        Vector<String> directos = social.vecinos(usuario);
        Vector<Recomendacion> resultados = new Vector<>();
        for (int i = 0; i < directos.tamano(); i++) {
            Vector<String> vecinos = social.vecinos(directos.obtener(i));
            for (int j = 0; j < vecinos.tamano(); j++) {
                String candidato = vecinos.obtener(j);
                if (candidato.equals(usuario) || contiene(directos, candidato)) continue;
                int pos = posicion(resultados, candidato);
                if (pos < 0) resultados.agregar(new Recomendacion(candidato, 1));
                else {
                    Recomendacion anterior = resultados.eliminar(pos);
                    resultados.agregar(new Recomendacion(candidato, anterior.amigosComunes() + 1));
                }
            }
        }
        // Inserción ordenada estable: más amigos en común primero; empates por nombre.
        Vector<Recomendacion> ordenados = new Vector<>();
        while (!resultados.vacio()) {
            int mejor = 0;
            for (int i = 1; i < resultados.tamano(); i++) {
                Recomendacion a = resultados.obtener(i), b = resultados.obtener(mejor);
                if (a.amigosComunes() > b.amigosComunes() ||
                    a.amigosComunes() == b.amigosComunes() && a.persona().compareTo(b.persona()) < 0) mejor = i;
            }
            ordenados.agregar(resultados.eliminar(mejor));
        }
        return ordenados;
    }
    private boolean contiene(Vector<String> v, String nombre) {
        for (int i = 0; i < v.tamano(); i++) if (v.obtener(i).equals(nombre)) return true;
        return false;
    }
    private int posicion(Vector<Recomendacion> v, String nombre) {
        for (int i = 0; i < v.tamano(); i++) if (v.obtener(i).persona().equals(nombre)) return i;
        return -1;
    }
}
