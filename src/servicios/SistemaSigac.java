package servicios;

import estructuras.Grafo;

public final class SistemaSigac {
    public final GestionAcademica estudiantes = new GestionAcademica();
    public final AtencionLaboratorio laboratorio = new AtencionLaboratorio();
    public final Grafo red = new Grafo(true);
    public final Recomendador social = new Recomendador();

    public void solicitarEquipo(int matricula, String equipo) {
        var estudiante = estudiantes.buscar(matricula);
        if (estudiante == null) throw new IllegalArgumentException("Matrícula inexistente: " + matricula);
        if (!red.contiene(equipo)) throw new IllegalArgumentException("Equipo inexistente: " + equipo);
        laboratorio.solicitar(estudiante.nombre() + " [" + matricula + "]", equipo);
    }

    public void registrarEnRedSocial(int matricula) {
        var estudiante = estudiantes.buscar(matricula);
        if (estudiante == null) throw new IllegalArgumentException("Matrícula inexistente: " + matricula);
        social.agregarPersona(estudiante.nombre());
    }

    public static SistemaSigac casoIntegrador() {
        SistemaSigac s = new SistemaSigac();
        int[] matriculas = {50, 30, 70, 20, 40, 60, 80};
        String[] nombres = {"Ana", "Beto", "Carla", "Diego", "Elena", "Eva", "Felipe"};
        for (int i = 0; i < matriculas.length; i++) s.estudiantes.crear(matriculas[i], nombres[i]);
        for (String equipo : new String[]{"PC1", "SW1", "SRV1", "PC2", "PC3", "PC4"}) s.red.agregarVertice(equipo);
        s.red.conectar("PC1", "SW1", 1);
        s.red.conectar("SW1", "SRV1", 2);
        s.red.conectar("SRV1", "PC2", 3);
        s.red.conectar("PC1", "PC3", 4);
        for (int i = 0; i < 5; i++) s.registrarEnRedSocial(matriculas[i]);
        s.social.amistad("Ana", "Beto");
        s.social.amistad("Ana", "Carla");
        s.social.amistad("Beto", "Diego");
        s.social.amistad("Carla", "Diego");
        s.social.amistad("Carla", "Elena");
        return s;
    }
}
