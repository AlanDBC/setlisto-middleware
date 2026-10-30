package com.setlisto.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    /**
     * Escribe información (byte[]) en un fichero.
     * Crea los directorios padre si no existen.
     */
    public static void write(byte[] data, String filePath, boolean append) throws IOException {
        if (data == null) return;

        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();

        // Crea todos los directorios intermedios necesarios si no existen
        if (parentDir != null) {
            Files.createDirectories(parentDir); 
        }

        // Escribe los bytes delegando en el API moderna
        if (append) {
            Files.write(path, data, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } else {
            Files.write(path, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    /**
     * Escribe datos concatenando ruta padre y nombre de archivo.
     * Reutiliza la lógica del primer método para evitar redundancia.
     */
    public static void write(String parentFolderName, String fileName, byte[] data) throws IOException {
        // Paths.get une los fragmentos de ruta asegurando que tenga los separadores correctos (/ o \)
        Path fullPath = Paths.get(parentFolderName, fileName);
        write(data, fullPath.toString(), false);
    }

    /**
     * Lee la información de un fichero existente como array de bytes.
     * Retorna null si no existe o no es un archivo regular.
     */
    public static byte[] read(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return null;
        }

        return Files.readAllBytes(path);
    }

    /**
     * Lee completamente un fichero y lanza una excepción descriptiva si falla la lectura.
     */
    public static byte[] readFully(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("El archivo no existe: " + filePath);
        }
        if (Files.isDirectory(path)) {
            throw new IOException("La ruta especificada es un directorio, no un archivo: " + filePath);
        }
        if (!Files.isReadable(path)) {
            throw new IOException("No hay permisos para leer el archivo: " + filePath);
        }

        return Files.readAllBytes(path);
    }
}