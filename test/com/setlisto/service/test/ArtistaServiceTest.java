package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.Artista;
import com.setlisto.service.ArtistaService;
import com.setlisto.service.impl.ArtistaServiceImpl;

public class ArtistaServiceTest {

    private ArtistaService service = null;

    public ArtistaServiceTest() {
        this.service = new ArtistaServiceImpl();
    }

    /**
     * Prueba la recuperación de un artista por su identificador único.
     */
    public void testFindById(Long id) {
        System.out.println("--- Probando findById(" + id + ") ---");
        Artista artista = service.findById(id);
        if (artista != null) {
            // El método toString() usa reflexión de AbstractValueObject
            System.out.println("Artista encontrado: " + artista);
        } else {
            System.out.println("Artista no encontrado.");
        }
        System.out.println();
    }

    /**
     * Prueba la obtención de artistas vinculados a un evento específico.
     * Utiliza los IDs de eventos definidos en el script de datos
     */
    public void testFindByMusicalEvent(Long eventId) {
        System.out.println("--- Probando findByMusicalEvent(" + eventId + ") ---");
        List<Artista> artistas = service.findByMusicalEvent(eventId);
        if (artistas != null && !artistas.isEmpty()) {
            System.out.println("Se encontraron " + artistas.size() + " artistas:");
            for (Artista a : artistas) {
                System.out.println("  - " + a.getNombre());
            }
        } else {
            System.out.println("No hay artistas vinculados a este evento.");
        }
        System.out.println();
    }

    /**
     * Prueba la creación y persistencia de un nuevo artista.
     */
    public void testCreate() {
        System.out.println("--- Probando save(Nuevo Artista) ---");
        Artista nuevo = new Artista();
        nuevo.setNombre("Daft Punk");
        nuevo.setDescripcion("Dúo francés de música electrónica.");

        Artista guardado = service.create(nuevo);
        if (guardado != null && guardado.getId() != null) {
            System.out.println("Artista guardado correctamente. ID: " + guardado.getId());
        } else {
            System.out.println("Falló el guardado del artista.");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArtistaServiceTest test = new ArtistaServiceTest();

        // 1. Buscar a Aitana (ID 1)
        test.testFindById(1L);

        // 2. Buscar artistas del evento 18 (Paco de Lucía Legacy - Vicente Amigo)
        test.testFindByMusicalEvent(18L);

        // 3. Crear un nuevo artista en la base de datos
//        test.testCreate();
    }
}