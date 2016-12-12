package awers.sistema;

import awers.proceso.AdministradorDeHilos;

public class Sistema{
	
	private AdministradorDeHilos administradorDeHilos;
	private Configuracion configuracion;
	
	
	public Sistema(){
		super();
		
	}
		
	public AdministradorDeHilos getAdministradorDeHilos() {
		return administradorDeHilos;
	}

	public void setAdministradorDeHilos(AdministradorDeHilos administradorDeHilos) {
		this.administradorDeHilos = administradorDeHilos;
	}

	
	
	public Configuracion getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	public void inicializar(){
		this.administradorDeHilos.inializarAlertasPendientes();
	}
	
	
	
	
}
