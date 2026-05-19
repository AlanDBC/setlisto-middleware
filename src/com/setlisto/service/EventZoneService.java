package com.setlisto.service;

import java.util.List;

import com.setlisto.model.ZonaEvento;

public interface EventZoneService {

	public ZonaEvento findById(Long id) throws ServiceException;

	public List<ZonaEvento> findByEventId(Long musicalEventId) throws ServiceException;
}
