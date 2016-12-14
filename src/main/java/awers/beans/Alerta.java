package awers.beans;

import org.apache.log4j.Logger;

import awers.modelo.dao.LogmdiDAO;
import awers.modelo.tablas.Logmdi;
import awers.proceso.InterfazPHP;

/**
 * 
 * @author Pollo Clase que representa un alerta activada por sms
 */
public class Alerta implements Runnable {

	private int tiempoHastaPrimerAlerta, tiempoHastaSegundoAlerta;
	private boolean activado;
	private InterfazPHP interfazPHP;
	private String clientNumber;
	private String codigoAlerta;
	private LogmdiDAO logmdiDao;
	private String telefono;

	private static final Logger LOG = Logger.getLogger(Alerta.class.getName());

	public Alerta(String clientNumber, InterfazPHP interfazPHP, int tiempoEspera,
	        String codigoAlerta, LogmdiDAO logmdiDao, String telefono) {
		this.tiempoHastaPrimerAlerta = (tiempoEspera * 60000);
		this.interfazPHP = interfazPHP;
		this.clientNumber = clientNumber;
		this.codigoAlerta = codigoAlerta;
		this.activado = true;
		this.logmdiDao = logmdiDao;
		this.telefono = telefono;
	}

	@Override
	public void run() {

		try {
			int tiempoRestante = this.tiempoHastaPrimerAlerta;
			this.esperar(tiempoRestante);
			Logmdi logmdi = new Logmdi(this.telefono);
			this.logmdiDao.guardar(logmdi);
			this.interfazPHP.informarNueveOnce(this/*this.clientNumber, this.codigoAlerta*/);

		} catch (InterruptedException e) {
			// this.interfazPHP.informarBajaAlerta(this.clientNumber,
			// this.codigoAlerta);
			LOG.info(e.getMessage());
		}

	}

	public void anularAlerta() {
		this.activado = false;
	}

	// Consulta cada 1 segundo si fue o no desactivado o si se acabó el tiempo
	// de espera para el primer alerta
	public void esperar(int tiempoRestante) throws InterruptedException {
		while (this.activado && tiempoRestante > 0) {
			Thread.sleep(1000);

			tiempoRestante -= 1000;
		}
		if (!this.activado) {
			throw new InterruptedException("Fue cancelada el alerta");
		}
	}

	public int getTiempoHastaPrimerAlerta() {
		return tiempoHastaPrimerAlerta;
	}

	public void setTiempoHastaPrimerAlerta(int tiempoHastaPrimerAlerta) {
		this.tiempoHastaPrimerAlerta = tiempoHastaPrimerAlerta;
	}

	public int getTiempoHastaSegundoAlerta() {
		return tiempoHastaSegundoAlerta;
	}

	public void setTiempoHastaSegundoAlerta(int tiempoHastaSegundoAlerta) {
		this.tiempoHastaSegundoAlerta = tiempoHastaSegundoAlerta;
	}

	public boolean isActivado() {
		return activado;
	}

	public void setActivado(boolean activado) {
		this.activado = activado;
	}

	public String getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getCodigoAlerta() {
		return codigoAlerta;
	}

	public void setCodigoAlerta(String codigoAlerta) {
		this.codigoAlerta = codigoAlerta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
}
