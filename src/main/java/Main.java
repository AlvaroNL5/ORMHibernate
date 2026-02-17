import dao.PersonajeDAO;
import dao.RazaDAO;
import model.Personaje;
import model.Raza;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersonajeDAO personajeDAO = new PersonajeDAO();
        RazaDAO razaDAO = new RazaDAO();

        try {
            // 1. Registro de razas
            System.out.println("--- Registrando razas ---");

            Raza hobbit = new Raza("Hobbit", "Sigilo y resistencia", "La Comarca", 100);
            Raza elfo = new Raza("Elfo", "Inmortalidad y vista aguda", "Rivendel", 999999);
            Raza humano = new Raza("Humano", "Adaptabilidad y valentía", "Gondor", 80);
            Raza enano = new Raza("Enano", "Fuerza y maestría en forja", "Erebor", 250);

            razaDAO.guardar(hobbit);
            razaDAO.guardar(elfo);
            razaDAO.guardar(humano);
            razaDAO.guardar(enano);

            System.out.println("4 razas registradas con éxito.");

            // 2. Registro de personajes (2 por raza)
            System.out.println("\n--- Registrando personajes ---");

            Personaje frodo = new Personaje("Frodo Bolsón", 50, "Dardo", 45.0, LocalDate.of(2968, 9, 22), hobbit);
            Personaje sam = new Personaje("Samsagaz Gamyi", 38, "Espada corta", 42.0, LocalDate.of(2980, 4, 6), hobbit);

            Personaje legolas = new Personaje("Legolas", 2931, "Arco élfico", 80.0, LocalDate.of(87, 1, 1), elfo);
            Personaje arwen = new Personaje("Arwen", 2700, "Hadhafang", 78.0, LocalDate.of(241, 1, 1), elfo);

            Personaje aragorn = new Personaje("Aragorn", 87, "Andúril", 85.0, LocalDate.of(2931, 3, 1), humano);
            Personaje eowyn = new Personaje("Éowyn", 24, "Espada de Rohan", 65.0, LocalDate.of(2995, 1, 1), humano);

            Personaje gimli = new Personaje("Gimli", 139, "Hacha de batalla", 75.0, LocalDate.of(2879, 1, 1), enano);
            Personaje thorin = new Personaje("Thorin Escudo de Roble", 195, "Orcrist", 88.0, LocalDate.of(2746, 1, 1), enano);

            personajeDAO.guardar(frodo);
            personajeDAO.guardar(sam);
            personajeDAO.guardar(legolas);
            personajeDAO.guardar(arwen);
            personajeDAO.guardar(aragorn);
            personajeDAO.guardar(eowyn);
            personajeDAO.guardar(gimli);
            personajeDAO.guardar(thorin);

            System.out.println("8 personajes registrados con éxito.");

            // 3. Listar todos los personajes
            System.out.println("\n--- Listado general de personajes ---");
            List<Personaje> todos = personajeDAO.listarTodos();
            for (Personaje p : todos) {
                System.out.println("  " + p.getNombre() + " | Raza: " + p.getRaza().getNombre() + " | Poder: " + p.getNivelPoder());
            }

            // 4. Consultar personajes de raza Elfo
            System.out.println("\n--- Personajes de raza Elfo ---");
            List<Personaje> elfos = personajeDAO.buscarPorRaza(elfo.getId());
            for (Personaje p : elfos) {
                System.out.println("  " + p.getNombre());
            }

            // 5. Buscar personajes por fragmento de nombre
            System.out.println("\n--- Búsqueda por nombre que contenga 'ara' ---");
            List<Personaje> resultadosBusqueda = personajeDAO.buscarPorNombre("ara");
            resultadosBusqueda.forEach(p -> System.out.println("  Encontrado: " + p.getNombre()));

            // 6. Personajes con poder entre 60 y 90
            System.out.println("\n--- Personajes con nivel de poder entre 60 y 90 ---");
            List<Personaje> poderMedio = personajeDAO.buscarPorRangoNivelPoder(60.0, 90.0);
            poderMedio.forEach(p -> System.out.println("  " + p.getNombre() + " -> " + p.getNivelPoder()));

            // 7. Actualizar nivel de poder de Sam
            System.out.println("\n--- Actualizando poder de Sam ---");
            sam.setNivelPoder(52.0);
            personajeDAO.actualizar(sam);
            Personaje samActualizado = personajeDAO.obtenerPorId(sam.getId());
            System.out.println("Sam ahora tiene poder: " + samActualizado.getNivelPoder());

            // 8. Eliminar a Éowyn
            System.out.println("\n--- Eliminando a Éowyn ---");
            personajeDAO.eliminar(eowyn.getId());
            System.out.println("Éowyn ha sido eliminada del registro.");

            // 9. Personajes de raza Enano (tras eliminación)
            System.out.println("\n--- Personajes de raza Enano ---");
            List<Personaje> listaEnanos = personajeDAO.buscarPorRaza(enano.getId());
            listaEnanos.forEach(p -> System.out.println("  " + p.getNombre()));

            // 10. Top 3 personajes más poderosos
            System.out.println("\n--- Top 3 más poderosos ---");
            List<Personaje> top3 = personajeDAO.obtenerMasPoderosos(3);
            int posicion = 1;
            for (Personaje p : top3) {
                System.out.println("  " + posicion + ". " + p.getNombre() + " (Poder: " + p.getNivelPoder() + ")");
                posicion++;
            }

            // 11. Top 3 personajes más antiguos
            System.out.println("\n--- Top 3 más antiguos ---");
            List<Personaje> veteranos = personajeDAO.obtenerMasAntiguos(3);
            for (Personaje p : veteranos) {
                System.out.println("  " + p.getNombre() + " - Edad: " + p.getEdad() + " años");
            }

            // 12. Consultas adicionales
            System.out.println("\n--- Estadísticas por raza ---");

            // a) Cantidad de personajes por raza
            List<Raza> todasRazas = razaDAO.listarTodas();
            for (Raza r : todasRazas) {
                Raza razaCompleta = razaDAO.obtenerConPersonajes(r.getId());
                int total = razaCompleta.getPersonajes().size();
                System.out.println("  " + r.getNombre() + ": " + total + " personaje(s)");
            }

            // b) Raza con más personajes
            System.out.println("\nRaza con mayor cantidad de personajes:");
            Raza razaDominante = null;
            int mayor = 0;
            for (Raza r : todasRazas) {
                Raza razaCompleta = razaDAO.obtenerConPersonajes(r.getId());
                int total = razaCompleta.getPersonajes().size();
                if (total > mayor) {
                    mayor = total;
                    razaDominante = r;
                }
            }
            if (razaDominante != null) {
                System.out.println("  " + razaDominante.getNombre() + " con " + mayor + " personaje(s)");
            }

            // c) Personajes longevos (más de 150 años)
            System.out.println("\nPersonajes con más de 150 años:");
            List<Personaje> todosActuales = personajeDAO.listarTodos();
            todosActuales.stream()
                    .filter(p -> p.getEdad() != null && p.getEdad() > 150)
                    .forEach(p -> System.out.println("  " + p.getNombre() + " - " + p.getEdad() + " años"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
            System.out.println("\nSesión cerrada correctamente.");
        }
    }
}