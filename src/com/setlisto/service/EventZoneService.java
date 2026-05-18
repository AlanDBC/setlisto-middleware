package com.setlisto.service;

import java.util.List;

import com.setlisto.model.EventZone;

public interface EventZoneService {

	public EventZone findById(Long id) throws Exception;

	public List<EventZone> findByEventId(Long musicalEventId) throws Exception;
}
