package com.setlisto.service;

public interface EncryptionService {

	public String encrypt(String data);
	
	public boolean checkEncryption(String data, String encryptedData);
}
