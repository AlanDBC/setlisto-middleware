package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.ZonaHoraria;
import com.setlisto.service.ZonaHorariaService;
import com.setlisto.service.impl.ZonaHorariaServiceImpl;

public class ZonaHorariaServiceTest {
	
	private ZonaHorariaService service;

	public ZonaHorariaServiceTest() {
		service = new ZonaHorariaServiceImpl();
	}
	
	private ZonaHoraria finById (Long id) {
		ZonaHoraria zonaHoraria = service.findById(id);
		
		System.out.println("Zona Horaria con id " + id + ": " + zonaHoraria.getNombre());
		
		return zonaHoraria;
	}
	
	private List<ZonaHoraria> findAll () {
		List<ZonaHoraria> zonasHorarias = service.findAll();
		System.out.println("Lista de Zonas Horarias: ");
		for (ZonaHoraria zonaHoraria : zonasHorarias) {
			System.out.println("- " + zonaHoraria.getNombre());
		}
		return zonasHorarias;
	}
	
	
	public static void main(String[] args) {
		ZonaHorariaServiceTest test = new ZonaHorariaServiceTest();
		test.finById(1L);
		test.findAll();
	}

}
