package com.setlisto.service.impl;

import org.mindrot.jbcrypt.BCrypt;

import com.setlisto.service.EncryptionService;

public class EncryptionServiceImpl implements EncryptionService {

    /**
     * Genera un hash seguro para la contraseña proporcionada.
     * Utiliza BCrypt.gensalt() para generar una sal aleatoria por defecto (10 rondas).
     */
    @Override
    public String encrypt(String data) {
        if (data == null) {
            return null;
        }
        // Genera la sal y el hash de la contraseña [6, 7]
        return BCrypt.hashpw(data, BCrypt.gensalt());
    }

    /**
     * Compara una contraseña en texto plano con un hash almacenado.
     */
    @Override
    public boolean checkEncryption(String data, String encryptedData) {
        if (data == null || encryptedData == null) {
            return false;
        }
        try {
            // Verifica si el texto plano coincide con el hash almacenado
            return BCrypt.checkpw(data, encryptedData);
        } catch (IllegalArgumentException e) {
            // Maneja casos donde el hash almacenado no tenga un formato BCrypt válido
            e.printStackTrace();
            return false;
        }
    }
}
