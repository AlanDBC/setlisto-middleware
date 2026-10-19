package com.setlisto.service;

import com.setlisto.model.DataException;

public interface StorageService {
	
	/**
	 * Almacena los datos asociados al id. Si ya existe un dato asociado al id, lo reemplaza por el nuevo.
	 * @param id
	 * @param data
	 * @throws DataException
	 */
	public void save(String id, StorageMetadata metadata, byte[] data)
		throws DataException;
	
	public boolean exists(String id,StorageMetadata metadata)
			throws DataException;
	
	public StorageData load(String id, StorageMetadata metadata)
			throws DataException;
	
	public StorageData[] load(String id)
			throws DataException;
		
	public void remove(String id, StorageMetadata metadata)
			throws DataException;
			
	public void remove(String id) 
			throws DataException;
	
}