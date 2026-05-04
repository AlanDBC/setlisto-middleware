package com.setlisto.service.test;

import java.util.List;

import com.setlisto.criteria.LugarCriteria;
import com.setlisto.model.Lugar;
import com.setlisto.model.LugarDTO;
import com.setlisto.model.Results;
import com.setlisto.service.LugarService;
import com.setlisto.service.impl.LugarServiceImpl;

public class LugarServiceTest {

    private LugarService service = null;

    public LugarServiceTest() {
        this.service = new LugarServiceImpl();
    }

    /**
     * Prueba la recuperación de un recinto específico por su ID.
     * Según el script de datos maestros, el ID 1 es el 'WiZink Center'.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: LugarService.findById(" + id + ") ---");
        LugarDTO lugar = service.findById(id);
        if (lugar != null) {
            System.out.println("Recinto encontrado: " + lugar.getNombre());
            System.out.println("  Dirección: " + lugar.getDireccion());
            System.out.println("  Ciudad: " + lugar.getCiudadNombre());
        } else {
            System.out.println("No se encontró el recinto con ID: " + id);
        }
    }

    /**
     * Prueba la búsqueda de lugares aplicando criterios geográficos.
     */
    public void testFindByCriteria() {
        System.out.println("\n--- Test: LugarService.findByCriteria ---");
        LugarCriteria criteria = new LugarCriteria();
//        criteria.setPaisId(15l);
//        criteria.setRegionId(regionId);
//        criteria.setLugarNombre("Sant");
        criteria.setLugarDireccion("Av.");

        Results<LugarDTO> resultados = service.findByCriteria(criteria, 0, 10);
        List<LugarDTO> lugares = resultados.getPage();
        if (lugares != null && !lugares.isEmpty()) {
            System.out.println("Se encontraron " + lugares.size() + " recintos en la región:");
            for (LugarDTO l : lugares) {
                System.out.println("  - " + l.getNombre() + " (" + l.getCiudadNombre() + ")");
            }
        } else {
            System.out.println("No hay recintos registrados para esos criterios.");
        }
    }

    /**
     * Prueba la creación de un nuevo recinto.
     */
    public void testCreate() {
        System.out.println("\n--- Test: LugarService.create ---");
        Lugar nuevo = new Lugar();
        nuevo.setNombre("Estadio de Prueba");
        nuevo.setDireccion("Calle Falsa 123");
        nuevo.setCiudadId(437L); // Madrid

        Lugar creado = service.create(nuevo);
        if (creado != null && creado.getId() != null) {
            System.out.println("Recinto creado con ID: " + creado.getId());
        } else {
            System.out.println("Error al crear el recinto.");
        }
    }
    
    // Modificara el recinto creado en el testCreate para verificar que se actualiza correctamente
    public void updateTest() {
    	LugarDTO aModificar = service.findById(14L); // Id del recinto creado en el testCreate
    	if (aModificar != null) {
			aModificar.setNombre("Estadio de Prueba Modificado");
			service.update(aModificar);
			
			// Verificar que se ha actualizado correctamente
			LugarDTO modificado = service.findById(14L);
			if (modificado != null && "Estadio de Prueba Modificado".equals(modificado.getNombre())) {
				System.out.println("Recinto actualizado correctamente: " + modificado.getNombre());
			} else {
				System.out.println("Error al actualizar el recinto.");
			}
		} else {
			System.out.println("No se encontró el recinto para modificar.");
		}
    }
    
    public void deleteTest() {	
    	service.delete(14L); // Id del recinto creado en el testCreate
    }
    
    public static void main(String[] args) {
        LugarServiceTest test = new LugarServiceTest();

        // 1. Buscar el WiZink Center (ID 1)
//        test.testFindById(1L);

        // 2. Buscar lugares en España (15L)
        test.testFindByCriteria();

        // 3. Probar la creación
//        test.testCreate();
        
        // 4. Probar la actualización del recinto creado
//		test.updateTest();
        
        // 5. Probar la eliminación del recinto creado
//      test.deleteTest();
    }
}