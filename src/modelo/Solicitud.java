package modelo;

public record Solicitud(int numero, String estudiante, String equipo) {
    public Solicitud {
        if (numero <= 0) throw new IllegalArgumentException("Número inválido");
        if (estudiante == null || estudiante.isBlank() || equipo == null || equipo.isBlank())
            throw new IllegalArgumentException("Estudiante y equipo son obligatorios");
        estudiante = estudiante.trim(); equipo = equipo.trim();
    }
    @Override public String toString() { return "#" + numero + " " + estudiante + " solicita " + equipo; }
}
