package com.setlisto.service.test;

import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.Resena;
import com.setlisto.model.ResenaDTO;
import com.setlisto.service.ResenaService;
import com.setlisto.service.impl.ResenaServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Reseñas.
 * Gestiona valoraciones, comentarios y favoritos de eventos [1].
 */
public class ResenaServiceTest {

    private static final Logger logger = LogManager.getLogger(ResenaServiceTest.class);
    private ResenaService resenaService;

    public ResenaServiceTest() {
        try {
            // Inicialización del servicio transaccional [2]
            this.resenaService = new ResenaServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar ResenaService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de una reseña específica por evento y cliente [1].
     * Referencia: Evento 4 valorado por Cliente 3 en datos de prueba [4].
     */
    public void testFindById(Long eventoId, Long usuarioId) {
        System.out.println("\n--- Ejecutando testFindById para Evento " + eventoId + " y Usuario " + usuarioId + " ---");
        try {
            // El servicio gestiona la conexión mediante JDBCUtils [2, 5]
            ResenaDTO resena = resenaService.findById(eventoId, usuarioId);
            if (resena != null) {
                logger.info("Reseña recuperada: Stars={} | Comentario='{}'", resena.getEstrellas(), resena.getComentario());
                System.out.println("Puntuación: " + resena.getEstrellas() + " estrellas");
                System.out.println("Comentario: " + (resena.getComentario() != null ? resena.getComentario() : "Sin comentario"));
                System.out.println("Favorito: " + (resena.getFavorito() != null ? "SÍ" : "NO"));
            } else {
                System.out.println("Reseña no encontrada.");
            }
        } catch (Exception e) {
            logger.error("Error buscando reseña: {}", e.getMessage());
        }
    }

    /**
     * Prueba la creación de una nueva reseña [1].
     */
    public void testCreate() {
        System.out.println("\n--- Ejecutando testCreate ---");
        try {
            Resena nueva = new Resena();
            nueva.setEventoId(1L); // Aitana: Alpha Tour [6]
            nueva.setClienteId(2L);    // Ana García [3]
            nueva.setEstrellas(new Double(5.00d));
            nueva.setComentario("Una experiencia inolvidable, el sonido fue perfecto.");
            nueva.setFavorito(true);

            // La creación es atómica; el ServiceImpl cierra la conexión tras el commit [2]
            Resena creada = resenaService.create(nueva);
            if (creada != null) {
                logger.info("Reseña registrada con éxito para cliente ID: {}", nueva.getClienteId());
                System.out.println("Reseña creada para el evento: " + creada.getEventoId());
            }
        } catch (Exception e) {
            // Captura errores como duplicidad de reseña (un usuario/un evento) [1]
            logger.error("Fallo al crear reseña transaccional: {}", e.getMessage());
            System.err.println("Excepción capturada en creación: " + e.getMessage());
        }
    }

    /**
     * Prueba la obtención de todas las reseñas de un evento musical [1].
     */
    public void testFindByEvent(Long eventoId) {
        System.out.println("\n--- Ejecutando testFindByMusicalEvent para Evento " + eventoId + " ---");
        try {
            List<ResenaDTO> resenas = resenaService.findByMusicalEvent(eventoId);
            if (resenas != null && !resenas.isEmpty()) {
                logger.info("Se recuperaron {} reseñas para el evento {}", resenas.size(), eventoId);
                System.out.println("Reseñas encontradas:");
                for (ResenaDTO r : resenas) {
                    System.out.println(" - Cliente ID " + r.getClienteId() + " puntuó con " + r.getEstrellas() + " estrellas.");
                }
            } else {
                System.out.println("No hay reseñas registradas para este evento.");
            }
        } catch (Exception e) {
            logger.error("Error recuperando reseñas del evento: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        ResenaServiceTest tester = new ResenaServiceTest();

        // 1. Buscar reseña existente en la carga inicial (Arch Enemy) [4]
        tester.testFindById(4L, 3L);

        // 2. Intentar crear una nueva reseña (Aitana)
        tester.testCreate();

        // 3. Listar todas las reseñas de un evento específico
        tester.testFindByEvent(11L); // Afterlife Madrid [4, 6]

        System.out.println("\n--- Pruebas de ResenaService finalizadas ---");
    }
}