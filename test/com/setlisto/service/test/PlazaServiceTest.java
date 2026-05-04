package com.setlisto.service.test;

import java.util.List;

import com.setlisto.criteria.PlazaCriteria;
import com.setlisto.model.PlazaDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PlazaService;
import com.setlisto.service.impl.PlazaServiceImpl;

public class PlazaServiceTest {

    private PlazaService service = null;

    public PlazaServiceTest() {
        this.service = new PlazaServiceImpl();
    }

    /**
     * Prueba la recuperación de un asiento físico por su ID.
     * Según el script de prueba, el ID 1 es 'Pista' en el WiZink Center.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: PlazaService.findById(" + id + ") ---");
        PlazaDTO plaza = service.findById(id);
        if (plaza != null) {
            System.out.println("Plaza encontrada: " + plaza.getFila() + " " + plaza.getNumeroAsiento());
            System.out.println("  Recinto: " + plaza.getLugarNombre());
            System.out.println("  Categoría: " + plaza.getCategoriaNombre());
        } else {
            System.out.println("No se encontró la plaza con ID: " + id);
        }
    }

    /**
     * Prueba la búsqueda de plazas filtrando por recinto y categoría.
     */
    public void testFindByCriteria(Long lugarId, Long categoriaId) {
        System.out.println("\n--- Test: PlazaService.findByCriteria ---");
        PlazaCriteria criteria = new PlazaCriteria();
        criteria.setLugarId(lugarId);
        criteria.setCategoriaId(categoriaId);

        Results<PlazaDTO> resultados = service.findByCriteria(criteria, 1, 10);
        List<PlazaDTO> plazas = resultados.getPage();
        if (resultados != null && !plazas.isEmpty()) {
            System.out.println("Se encontraron " + plazas.size() + " plazas con los criterios.");
            for (PlazaDTO p : plazas) {
                System.out.println("  - Fila: " + p.getFila() + ", Nº: " + p.getNumeroAsiento());
            }
        } else {
            System.out.println("No se encontraron plazas para esos criterios.");
        }
    }

    public static void main(String[] args) {
        PlazaServiceTest test = new PlazaServiceTest();

        // 1. Buscar plaza específica (ID 4: Palco 3 en el Bernabéu [3])
        test.testFindById(4L);

        // 2. Buscar todas las plazas de categoría 'General' (ID 1) en el WiZink Center (ID 1)
        test.testFindByCriteria(1L, 1L);
    }
}