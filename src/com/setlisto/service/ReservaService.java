package com.setlisto.service;

import java.util.List;

import com.setlisto.model.TicketDTO;

public interface ReservaService {

	public List<TicketDTO> comprarZona(Long eventZoneId, Long customerId, int cantidad, Long paymentMethodId, Long currencyId) throws Exception;
}
