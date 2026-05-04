package com.setlisto.others.test;

import com.setlisto.model.Cliente;
import com.setlisto.model.EventoMusicalDTO;

public class ToStringTest {

	public static void main(String[] args) {
		EventoMusicalDTO em = new EventoMusicalDTO();
		System.out.println(em);
		
		Cliente c = new Cliente();
		c.setNombre("Alan");
		System.out.println(c);
	}

}
