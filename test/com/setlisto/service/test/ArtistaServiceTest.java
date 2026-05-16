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

    public void testFindById(Long id) {
        System.out.println("--- Probando findById(" + id + ") ---");
        try {
            Artista artista = service.findById(id);
            if (artista != null) {
                System.out.println("Artista encontrado: " + artista);
            } else {
                System.out.println("Artista no encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Error al buscar el artista por ID: " + e.getMessage());
        }
        System.out.println();
    }

    public void testFindByMusicalEvent(Long eventId) {
        System.out.println("--- Probando findByMusicalEvent(" + eventId + ") ---");
        try {
            List<Artista> artistas = service.findByMusicalEvent(eventId);
            if (artistas != null && !artistas.isEmpty()) {
                System.out.println("Se encontraron " + artistas.size() + " artistas:");
                for (Artista a : artistas) {
                    System.out.println("  - " + a.getNombre());
                }
            } else {
                System.out.println("No hay artistas vinculados a este evento.");
            }
        } catch (Exception e) {
            System.err.println("Error al buscar artistas por evento: " + e.getMessage());
        }
        System.out.println();
    }

    public void testCreate() {
        System.out.println("--- Probando save(Nuevo Artista) ---");
        try {
            Artista nuevo = new Artista();
            nuevo.setNombre("Daft Punk");
            nuevo.setDescripcion("Dúo francés de música electrónica.");

            Artista guardado = service.create(nuevo);
            if (guardado != null && guardado.getId() != null) {
                System.out.println("Artista guardado correctamente. ID: " + guardado.getId());
            } else {
                System.out.println("Falló el guardado del artista.");
            }
        } catch (Exception e) {
            System.err.println("Error al crear el artista: " + e.getMessage());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArtistaServiceTest test = new ArtistaServiceTest();
        // Si testFindById falla, testFindByMusicalEvent se ejecutará de todos modos.
//        test.testFindById(1L);
//        test.testFindByMusicalEvent(18L);
         test.testCreate();
    }
}