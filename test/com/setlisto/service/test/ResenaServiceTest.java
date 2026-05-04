package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.Resena;
import com.setlisto.model.ResenaDTO;
import com.setlisto.service.ResenaService;
import com.setlisto.service.impl.ResenaServiceImpl;

public class ResenaServiceTest {

    private ResenaService service = null;

    public ResenaServiceTest() {
        this.service = new ResenaServiceImpl();
    }

    /**
     * Prueba el flujo completo: Crear, Buscar, Actualizar y Eliminar.
     */
    public void testFullCycle() {
        System.out.println("--- Test: ResenaService CRUD Cycle ---");
        
        // 1. CREATE: Intentamos crear una reseña para el Cliente 1 en el Evento 2
        Resena nueva = new Resena();
        nueva.setEventoId(2L); // Pearl Jam
        nueva.setClienteId(1L); // Juan Pérez
        nueva.setEstrellas(5.0);
        nueva.setFavorito(true);
        nueva.setComentario("Increíble, espero que vuelvan pronto.");

        Resena creada = service.create(nueva);
        if (creada != null) {
            System.out.println("Reseña creada con éxito." + creada);

            // 2. UPDATE: Modificamos el comentario y la puntuación
            creada.setEstrellas(4.5);
            creada.setComentario("Editado: Fue genial, aunque el sonido falló un poco.");
            boolean editado = service.update(creada);
            System.out.println(editado ? "Reseña actualizada.": "Error al actualizar.");

            // 3. FIND BY ID: Verificamos los cambios
            ResenaDTO buscada = service.findById(2L, 1L);
            if (buscada != null) {
                System.out.println("Recuperada: [" + buscada.getEstrellas() + "*] " + buscada.getComentario());
            }

            // 4. DELETE: Limpiamos el dato de prueba
            boolean eliminado = service.delete(2L, 1L);
            System.out.println(eliminado ? "Reseña eliminada correctamente." : "Error al eliminar.");

        } else {
            System.out.println("✘ El registro falló (¿ya existía una reseña para este par evento/usuario?)");
        }
    }

    /**
     * Prueba la recuperación de reseñas por evento.
     */
    public void testFindByEvent(Long eventId) {
        System.out.println("\n--- Test: ResenaService.findByMusicalEvent(" + eventId + ") ---");
        List<ResenaDTO> resenas = service.findByMusicalEvent(eventId);
        if (resenas != null && !resenas.isEmpty()) {
            System.out.println("Se encontraron " + resenas.size() + " reseñas:");
            for (ResenaDTO r : resenas) {
                System.out.println("  - " + r.getNombreUsuario() + ": " + r.getComentario());
            }
        } else {
            System.out.println("No hay reseñas para este evento.");
        }
    }
    
    public void testFindByCustomer(Long userId) {
		System.out.println("\n--- Test: ResenaService.findByCustomer(" + userId + ") ---");
		List<ResenaDTO> resenas = service.findByCustomer(userId);
		if (resenas != null && !resenas.isEmpty()) {
			System.out.println("El cliente tiene " + resenas.size() + " reseñas:");
			for (ResenaDTO r : resenas) {
				System.out.println("  - Evento " + r.getEventoId() + ": " + r.getComentario());
			}
		} else {
			System.out.println("Este cliente no ha publicado reseñas.");
		}
	}

    public static void main(String[] args) {
        ResenaServiceTest test = new ResenaServiceTest();
        
        // 1. Probar ciclo de vida (CRUD)
//        test.testFullCycle();

        // 2. Consultar reseñas existentes del script (Evento 4: Arch Enemy tiene una reseña de Luis)
//        test.testFindByEvent(4L);
        
        // 3. Consultar el historial de reseñas de un cliente
        test.testFindByCustomer(4L);
    }
}