package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.ZonaHorariaDAO;
import com.setlisto.model.ZonaHoraria;
import com.setlisto.service.ZonaHorariaService;

public class ZonaHorariaServiceImpl implements ZonaHorariaService {

	private ZonaHorariaDAO zonaHorariaDAO = null;
	
	public ZonaHorariaServiceImpl() {
		this.zonaHorariaDAO = new ZonaHorariaDAO();
	}

	@Override
	public ZonaHoraria findById(Long id) {
		return zonaHorariaDAO.findById(id);
	}

	@Override
	public List<ZonaHoraria> findAll() {
		return zonaHorariaDAO.findAll();
	}
}
