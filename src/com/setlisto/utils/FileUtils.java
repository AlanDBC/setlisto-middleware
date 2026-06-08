package com.setlisto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	
	
	public static int read(String filePath, byte[] buffer) throws IOException {
		File f = new File(filePath);
		InputStream fis = new FileInputStream(f);
		int bytesLeidos = fis.read(buffer);
		return bytesLeidos;
	}
	
	
	
	public static void write(String filePath, byte[] data)
		throws IOException {
		File f = new File(filePath);
		write(f.getParentFile().getAbsolutePath(),f.getName(),data);		
	}
	
	/**
	 * Si el fichero existe, reemplaza el contenido por el de data.
	 * @param nombreCarpeta
	 * @param nombreFichero
	 * @param data
	 */
	public static void write(String parentFolderName, String fileName, byte[] data) 
			throws IOException {
		File parentFolder = new File(parentFolderName);
				
		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}
		
		if (!parentFolder.isDirectory()) {
			throw new IOException(parentFolderName + " NO es una carpeta");
		}
		
		File file = new File(parentFolderName + File.separator + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}

		OutputStream os = new FileOutputStream(file);
		os.write(data);
		os.flush();
		os.close();

	}

}