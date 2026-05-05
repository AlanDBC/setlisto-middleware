package com.setlisto.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Clase base para los objetos de valor (Value Objects) en el modelo.
 * Proporciona una implementación predeterminada del método toString()
 * utilizando la reflexión para generar una representación en cadena
 * de los atributos del objeto. Comun.
 */
public abstract class AbstractValueObject {

	public AbstractValueObject() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
}
