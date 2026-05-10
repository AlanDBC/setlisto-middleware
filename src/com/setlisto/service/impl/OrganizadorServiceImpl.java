package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.criteria.OrganizadorCriteria;
import com.setlisto.dao.OrganizadorDAO;
import com.setlisto.model.Organizador;
import com.setlisto.model.Results;
import com.setlisto.service.EncryptionService;
import com.setlisto.service.MailService;
import com.setlisto.service.OrganizadorService;

public class OrganizadorServiceImpl implements OrganizadorService {

    private OrganizadorDAO organizadorDAO = null;
    private EncryptionService encryptionService = null;
    private MailService mailService = null;

    public OrganizadorServiceImpl() {
        this.organizadorDAO = new OrganizadorDAO();
        this.encryptionService = new EncryptionServiceImpl();
        this.mailService = new MailServiceImpl();
    }

    @Override
    public Organizador register(Organizador organizador) {
        String passwordEncrypted = encryptionService.encrypt(organizador.getContrasena());
        organizador.setContrasena(passwordEncrypted);

        Organizador registrado = organizadorDAO.create(organizador);
        if (registrado != null) {
            mailService.sendEmail(registrado.getEmail(), "Bienvenido a Setlisto Partners",
                    "Hola " + registrado.getNombre() + ", su cuenta de organizador ha sido registrada.");
        }
        return registrado;
    }

    @Override
    public Organizador login(String email, String password) {
        Organizador org = organizadorDAO.findByEmail(email);
        if (org != null && encryptionService.checkEncryption(password, org.getContrasena())) {
            return org;
        }
        return null;
    }

    @Override
    public Organizador findById(Long id) {
        return organizadorDAO.findById(id);
    }

    @Override
    public Results<Organizador> findByCriteria(OrganizadorCriteria criteria, int from, int pageSize) {
        return organizadorDAO.findByCriteria(criteria, from, pageSize);
    }

    @Override
    public boolean update(Organizador organizador) {
        return organizadorDAO.update(organizador);
    }

    @Override
    public boolean updateVerifiedStatus(Long id, boolean verificado) {
        return organizadorDAO.updateVerifiedStatus(id, verificado);
    }

    @Override
    public boolean delete(Long id) {
        return organizadorDAO.delete(id);
    }

	@Override
	public List<Organizador> findAll() {
		return organizadorDAO.findAll();
	}
}
