package modelo;

public final class Estudiante {
    private final int matricula;
    private String nombre;

    public Estudiante(int matricula, String nombre) {
        if (matricula <= 0) throw new IllegalArgumentException("La matrícula debe ser positiva");
        cambiarNombre(nombre);
        this.matricula = matricula;
    }

    public int matricula() { return matricula; }
    public String nombre() { return nombre; }

    public void cambiarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        this.nombre = nombre.trim();
    }

    @Override public String toString() { return matricula + " - " + nombre; }
}
