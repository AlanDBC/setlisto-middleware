package com.setlisto.service.impl;

import java.io.File;
import java.io.IOException;

import com.setlisto.model.DataException;
import com.setlisto.service.StorageData;
import com.setlisto.service.StorageMetadata;
import com.setlisto.service.StorageService;
import com.setlisto.utils.FileUtils;

public class StorageServiceFileImpl implements StorageService {
	
	private static final String STORAGE_FOLDER = "c:\\setlisto-storage";
	
	public StorageServiceFileImpl() {
		
	}
	
	
	// c:\\setlisto-storage\\musical-event\\51341\\51341-main-image.jpg
	/**
	 * Implementa la convencion de nombrado para la carpeta 
	 * en la que se guarda los datos para un id.
	 * @param id
	 * @param metadata
	 * @return
	 */
	private String getFolderFor(String id, StorageMetadata metadata) {
		StringBuilder folderName = new StringBuilder(STORAGE_FOLDER);
		folderName.append(File.separator);
		folderName.append(metadata.getEntityType());
		folderName.append(File.separator);
		folderName.append(id);
		folderName.append(File.separator);
		return folderName.toString();
	}
	
	/**
	 * Implementa la convenci�n de nombrado para el fichero de ciertos datos 
	 * asociados a un id.
	 * @param id
	 * @param metadata
	 * @return
	 */
	private String getFileNameFor(String id, StorageMetadata metadata) {		
		StringBuilder fileName = new StringBuilder(id)
									.append("_").append(metadata.getResourceType())
									.append(".").append(metadata.getFormat());
		return fileName.toString();
	}

	
	@Override
	public void save(String id, StorageMetadata metadata, byte[] data)
		throws DataException {
		String folder = null;
		String fileName = null;
		try {
			folder = getFolderFor(id, metadata);
			fileName = getFileNameFor(id, metadata);
			FileUtils.write(folder, fileName, data);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DataException(folder+": "+fileName+": "+e.getMessage());
		}
	}

	
	@Override
	public StorageData load(String id, StorageMetadata metadata)
			throws DataException {
		StorageData sdata = new StorageData();
		sdata.setId(id);
		sdata.setMetadata(metadata);
		String folder = getFolderFor(id, metadata);
		String fileName = getFileNameFor(id, metadata);
		byte[] data = new byte[1000000]; // TODO 
		try {
			FileUtils.read(folder+File.separator+fileName, data);
			sdata.setData(data);
			return sdata;
		} catch (IOException ioe) {
			throw new DataException(ioe.getMessage());
		}
	}
	
	@Override
	public StorageData[] load(String id)
			throws DataException {
		return null; // TODO
	}

	



	@Override
	public boolean exists(String id, StorageMetadata metadata) throws DataException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void remove(String id, StorageMetadata metadata) throws DataException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

}