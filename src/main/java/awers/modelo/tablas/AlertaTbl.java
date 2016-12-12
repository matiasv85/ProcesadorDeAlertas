package awers.modelo.tablas;

import java.util.Date;

public class AlertaTbl {
	private String clientNumber;
	private String telefono;
	private String codigoAlerta;
	private int duracion;
	private Date fechaActivacion;

	public AlertaTbl(String clientNumber, String telefono, String codigoAlerta, int duracion) {
		this.clientNumber = clientNumber;
		this.telefono = telefono;
		this.codigoAlerta = codigoAlerta;
		this.duracion = duracion;
	}

	public AlertaTbl(String clientNumber, String telefono, String codigoAlerta, int duracion,
	        Date fechaActivacion) {
		this(clientNumber, telefono, codigoAlerta, duracion);
		this.fechaActivacion = fechaActivacion;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getDuracion() {
		return this.duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Date getFechaActivacion() {
		return this.fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public String getClientNumber() {
		return this.clientNumber;
	}

	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getCodigoAlerta() {
		return this.codigoAlerta;
	}

	public void setCodigoAlerta(String codigoAlerta) {
		this.codigoAlerta = codigoAlerta;
	}

	public String getIdAlerta() {
		return this.getClientNumber() + "_" + this.getCodigoAlerta();
	}

}
