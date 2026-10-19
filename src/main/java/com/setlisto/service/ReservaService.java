package com.setlisto.service;

import java.util.List;

import com.setlisto.model.TicketDTO;

public interface ReservaService {

	/**
	 * Reserva una plaza dentro de la zona elegida
	 * 
	 * @param zonaEventoId
	 * @param clienteId
	 * @param cantidad de plazas que se quiere reservar
	 * @param metodoPagoId
	 * @param monedaId
	 * @return Lista de tickets, por si se compró mas de uno
	 * @throws ServiceException
	 */
	public List<TicketDTO> comprarZona(Long eventZoneId, Long customerId, int cantidad, Long paymentMethodId, Long currencyId) throws ServiceException;
}
