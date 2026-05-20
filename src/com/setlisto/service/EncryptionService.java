package com.setlisto.service;

public interface EncryptionService {
	
	/**
	 * Encripta una contraseña
	 * 
	 * @param data contraseña
	 * @return String encriptado
	 */
	public String encrypt(String data);
	
	/**
	 * Compara una contraseña con un string encriptado para ver su compatibilidad
	 * 
	 * @param data contraseña cruda
	 * @param encryptedData contraseña encriptada (hash)
	 * @return true si son correspondientes, false en caso contrario
	 */
	public boolean checkEncryption(String data, String encryptedData);
}
