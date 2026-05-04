package com.setlisto.service.test;

import java.sql.Date;
import java.util.List;

import com.setlisto.criteria.OrganizadorCriteria;
import com.setlisto.model.Organizador;
import com.setlisto.model.Results;
import com.setlisto.service.OrganizadorService;
import com.setlisto.service.impl.OrganizadorServiceImpl;

public class OrganizadorServiceTest {

	private OrganizadorService service = null;

	public OrganizadorServiceTest() {
		this.service = new OrganizadorServiceImpl();
	}

	/**
	 * Prueba el flujo completo de registro. 
	 * Debe cifrar la contraseña y enviar un correo de bienvenida
	 */
	public void testRegister() {
		System.out.println("--- Test: OrganizadorService.register ---");
		Organizador nuevo = new Organizador();
		nuevo.setNombreComercial("Producciones Musicales S.L.");
		nuevo.setNombre("Carlos");
		nuevo.setApellido1("García");
		nuevo.setApellido2("López");
		nuevo.setEmail("contacto@producciones.test");
		nuevo.setContrasena("admin123");
		nuevo.setTelefono("912345678");
		nuevo.setVerificado(false);
		Date fecha = Date.valueOf("1985-08-15");
		nuevo.setFechaNacimiento(fecha);

		Organizador registrado = service.register(nuevo);
		if (registrado != null && registrado.getId() != null) {
			System.out.println("Organizador registrado con ID: " + registrado.getId());
		} else {
			System.out.println("El registro falló.");
		}
	}

	/**
	 * Prueba la autenticación verificando el hash de la contraseña
	 */
	public void testLogin(String email, String password) {
		System.out.println("\n--- Test: OrganizadorService.login ---");
		Organizador org = service.login(email, password);
		if (org != null) {
			System.out.println("Login exitoso para: " + org.getNombreComercial());
		} else {
			System.out.println("Credenciales incorrectas para: " + email);
		}
	}

	/**
	 * Prueba la actualización de datos generales
	 */
	public void testUpdate(Long id) {
		System.out.println("\n--- Test: OrganizadorService.update ---");
		Organizador org = service.findById(id);
		if (org != null) {
			org.setTelefono("600999888");
			boolean exito = service.update(org);
			System.out.println(exito ? "Datos actualizados correctamente." : "Error al actualizar.");
		}
	}

	/**
	 * Prueba el cambio específico del estado de verificación
	 */
	public void testUpdateVerifiedStatus(Long id, boolean status) {
		System.out.println("\n--- Test: OrganizadorService.updateVerifiedStatus ---");
		boolean exito = service.updateVerifiedStatus(id, status);
		System.out.println(exito ? "Estado de verificación cambiado a: " + status : "Error al cambiar estado.");
	}

	/**
	 * Prueba la búsqueda por criterios (ej: buscar por email)
	 */
	public void testFindByCriteria(OrganizadorCriteria criteria) {
		System.out.println("\n--- Test: OrganizadorService.findByCriteria ---");
		Results<Organizador> resultados = service.findByCriteria(criteria, 1, 10);
		List<Organizador> organizadores = resultados.getPage();
		if (organizadores != null) {
			for (Organizador org : organizadores) {
				System.out.println("Encontrado: " + org.getNombreComercial() + " - " + org.getEmail());
			}
		} else {
			System.out.println("No se encontraron organizadores con los criterios dados.");
		}
		
	}

	public void deleteTest(Long id) {
		System.out.println("\n--- Test: OrganizadorService.delete ---");
		boolean exito = service.delete(id);
		System.out.println(exito ? "Organizador marcado como eliminado." : "Error al eliminar.");
	}

	public static void main(String[] args) {
		OrganizadorServiceTest test = new OrganizadorServiceTest();

//		 1. Registro de nuevo organizador
//		        test.testRegister();

//		 2. Login con el usuario recién creado
		        test.testLogin("contacto@producciones.test", "admin123");

//		 3. Buscar por criterio
//		        OrganizadorCriteria criteria = new OrganizadorCriteria();
//		        criteria.setEmail("asdasd");
//		        test.testFindByCriteria(criteria);

//		 4. Actualizar datos
//		        test.testUpdate(11L);

//		 5. Verificar un organizador
//		        test.testUpdateVerifiedStatus(11L, true);

//		 6. Eliminar organizador
//				  test.deleteTest(22L);
	}
}