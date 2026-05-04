package com.setlisto.service.test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.setlisto.criteria.EventoMusicalCriteria;
import com.setlisto.model.Artista;
import com.setlisto.model.EventoMusical;
import com.setlisto.model.EventoMusicalDTO;
import com.setlisto.model.GeneroMusical;
import com.setlisto.model.Results;
import com.setlisto.model.SubGeneroMusical;
import com.setlisto.service.EventoMusicalService;
import com.setlisto.service.impl.EventoMusicalServiceImpl;

public class EventoMusicalServiceTest {

	private EventoMusicalService service = null;

	public EventoMusicalServiceTest() {
		this.service = new EventoMusicalServiceImpl();
	}

	/**
	 * Prueba la recuperación de un evento con datos enriquecidos (nombres de lugar, ciudad, etc.).
	 */
	public void testFindById(Long id) {
		System.out.println("--- Test: EventoMusicalService.findById(" + id + ") ---");
		EventoMusicalDTO em = service.findById(id);
		if (em != null) {
			System.out.println("Evento encontrado: " + em.getNombre());
			System.out.println("  Lugar: " + em.getLugarNombre() + " (" + em.getCiudadNombre() + ")");
		} else {
			System.out.println("No se encontró el evento con ID: " + id);
		}
	}

	/**
	 * Prueba los nuevos filtros de búsqueda: Artista, Organizador y Ubicación.
	 */
	public void testFindByCriteriaExtendido() {
		System.out.println("=== INICIANDO PRUEBAS DE CRITERIOS EXTENDIDOS ===");

		// 1. FILTRO POR ARTISTA (Ejemplo: Aitana - ID 1)
		// Según los inserts, Aitana participa en el evento 'Aitana: Alpha Tour' (ID 1).
		EventoMusicalCriteria critArtista = new EventoMusicalCriteria();
		critArtista.setArtistaId(1L); 
		ejecutarBusqueda("Filtro por Artista (ID 1 - Aitana)", critArtista);

		// 2. FILTRO POR CIUDAD (Ejemplo: Madrid - ID 437)
		// Madrid tiene múltiples eventos como Afterlife, Hans Zimmer y Bizarrap
		EventoMusicalCriteria critCiudad = new EventoMusicalCriteria();
		critCiudad.setCiudadId(437L);
		ejecutarBusqueda("Filtro por Ciudad (ID 437 - Madrid)", critCiudad);

		// 3. FILTRO POR ORGANIZADOR (Ejemplo: Valencia Electronic - ID 14)
		// Este organizador gestiona el evento 'Afterlife Madrid' (ID 11)
		EventoMusicalCriteria critOrg = new EventoMusicalCriteria();
		critOrg.setOrganizadorId(14L);
		ejecutarBusqueda("Filtro por Organizador (ID 14 - Valencia Electronic)", critOrg);

		// 4. FILTRO COMBINADO: ROCK EN BARCELONA (Género 2, Ciudad 429)
		// Debería devolver eventos como Pearl Jam o Metallica
		EventoMusicalCriteria critCombo = new EventoMusicalCriteria();
		critCombo.setGeneroMusicalId(2l); 
		critCombo.setCiudadId(429L);
		ejecutarBusqueda("Filtro Combinado: Rock en Barcelona", critCombo);
		
		// 5. Filtro de Fecha; Eventos de 2025
		EventoMusicalCriteria critFecha = new EventoMusicalCriteria();
		critFecha.setFechaInicio(LocalDateTime.of(2025, 1, 1, 0, 0));
		critFecha.setFechaFin(LocalDateTime.of(2025, 12, 31, 23, 59));
		ejecutarBusqueda("Filtro por Fecha: Eventos en 2025", critFecha);
		
		// 6. FIltro por Estado
		EventoMusicalCriteria critEstado = new EventoMusicalCriteria();
		critEstado.setEstadoId(1l); // (1 Borrador) (2 Programado) (3 En Curso) (4 Finalizado) (5 Cancelado) (6 Postergado)
		ejecutarBusqueda("Filtro por Estado ", critEstado);
		
		EventoMusicalCriteria critIdEvento = new EventoMusicalCriteria();
		critIdEvento.setId(1l);
		ejecutarBusqueda("Filtro por ID Evento ", critIdEvento);
		
		// Filtro con from y pageSize
		EventoMusicalCriteria criteria = new EventoMusicalCriteria();
		int from = 1;
		int pageSize = 5; 
		Results<EventoMusicalDTO> resultPage;
		List<EventoMusicalDTO> resultados;
		do {
		    resultPage = service.findByCriteria(criteria, from, pageSize);
		    
		    if (resultPage == null) {
		        System.out.println("Error: El servicio devolvió null.");
		        break; 
		    }
		    resultados = resultPage.getPage();
		    
		    if (resultados == null) {
		        System.out.println("La lista de resultados es null.");
		        break;
		    }

		    print(resultados);
		    from = from + pageSize;
		} while (resultados.size() == pageSize);
		
		// Filtro por nombre de Artista
		EventoMusicalCriteria critNombreArtista = new EventoMusicalCriteria();
		critNombreArtista.setArtistaNombre("Ait");
		ejecutarBusqueda("Filtro por Nombre de Artista (contiene 'Ait')", critNombreArtista);
		
	}
	
	private void print (List<EventoMusicalDTO> resultsPage) {
		System.out.println("Imprimiendo pagina...");
		for (EventoMusicalDTO em: resultsPage) {
			System.out.println(em);
		}
	}

	/**
	 * Método auxiliar para imprimir los resultados de cada búsqueda.
	 */
	private void ejecutarBusqueda(String descripcion, EventoMusicalCriteria criteria) {
	    System.out.println("\n--- " + descripcion + " ---");
	    Results<EventoMusicalDTO> resultados = service.findByCriteria(criteria, 0, 30);
	    List<EventoMusicalDTO> eventos = resultados.getPage();

	    if (eventos != null && !eventos.isEmpty()) {
	        System.out.println("Se encontraron " + eventos.size() + " eventos:");
	        for (EventoMusicalDTO dto : eventos) {
	            // Mapeamos las listas a String para una visualización rápida
	            String nombresArtistas = dto.getArtistas().stream()
	                    .map(Artista::getNombre)
	                    .collect(Collectors.joining(", "));

	            String nombresGeneros = dto.getGeneros().stream()
	                    .map(GeneroMusical::getNombre)
	                    .collect(Collectors.joining(", "));

	            String nombresSubGeneros = dto.getSubGeneros().stream()
	                    .map(SubGeneroMusical::getNombre)
	                    .collect(Collectors.joining(", "));

	            System.out.println("  - [" + dto.getId() + "] " + dto.getNombre());
	            System.out.println("    Lugar: " + dto.getLugarNombre() + " | Org: " + dto.getOrganizadorNombre());
	            System.out.println("    Géneros: [" + nombresGeneros + "]");
	            System.out.println("    Subgéneros: [" + nombresSubGeneros + "]");
	            System.out.println("    Artistas: [" + nombresArtistas + "]");
	            System.out.println("    -------------------------------------------");
	        }
	    } else {
	        System.out.println("No se encontraron resultados para este criterio.");
	    }
	}

	/**
	 * Prueba la creación de un nuevo evento.
	 */
	public void testCreate() {
		System.out.println("\n--- Test: EventoMusicalService.create ---");
		EventoMusical nuevo = new EventoMusical();
		nuevo.setNombre("Test Service Event");
		nuevo.setDescripcion("Evento de prueba desde el Service");
		nuevo.setFechaInicio(LocalDateTime.of(2026, 5, 15, 20, 0));
		nuevo.setFechaFin(LocalDateTime.of(2026, 5, 15, 23, 30));
		nuevo.setIdOrganizador(1L); // Live Nation España
		nuevo.setIdLugar(1L);       // WiZink Center
		nuevo.setCapacidad(1000);
		nuevo.setIdSubtipo(1l);      // Solo Artist
		// el estado es puesto por el DAO (Borrador)
		nuevo.setIdZonaHoraria(3l);

		EventoMusical creado = service.create(nuevo);
		if (creado != null && creado.getId() != null) {
			System.out.println("Evento creado exitosamente con ID: " + creado.getId());
		} else {
			System.out.println("Fallo al crear el evento (verificar validaciones de capacidad).");
		}
	}

	// Modificara el evento creado en el testCreate para verificar que se actualiza correctamente
	public void updateTest() {
		System.out.println("\n--- Test: EventoMusicalService.update ---");
		EventoMusicalDTO evento = service.findById(12L); // ID
		if (evento != null) {
			System.out.println("Evento antes de la actualización: " + evento);
			evento.setNombre("Test Service Event Updated");
			evento.setDescripcion("Descripción actualizada desde el Service");
			evento.setIdZonaHoraria(4l);
			service.update(evento);

			EventoMusicalDTO updated = service.findById(12L);
			System.out.println("Evento después de la actualización: " + updated);
		} else {
			System.out.println("No se encontró el evento con ID 12 para actualizar.");
		}


	}

	public void deleteTest(Long id) {
		System.out.println("\n--- Test: EventoMusicalService.delete(" + id + ") ---");
		service.delete(id);
		EventoMusicalDTO deleted = service.findById(id);
		if (deleted == null) {
			System.out.println("Evento con ID " + id + " eliminado correctamente.");
		} else {
			System.out.println("El evento con ID " + id + " aún existe después de intentar eliminarlo.");
		}
	}

	public static void main(String[] args) {
		EventoMusicalServiceTest test = new EventoMusicalServiceTest();

		// 1. Buscar evento existente (según script de prueba, el 1L es Aitana)
//				test.testFindById(1L);

		// 2. Probar creación
//		        test.testCreate();

		// 3. Probar modificacion
//		        test.updateTest();   

		// 4. Probar eliminación 
//		         test.deleteTest(21L); 

		// 5. Probar criterios extendidos
				test.testFindByCriteriaExtendido();

	}
}