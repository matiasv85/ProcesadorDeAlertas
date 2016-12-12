package awers.modelo.tablas;

public class Logmdi {
	private String telefono;
	private String codigoAlerta;
	public Logmdi(){
		
	}
	public Logmdi(String telefono){
		this.telefono = telefono;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCodigoAlerta() {
		return codigoAlerta;
	}
	public void setCodigoAlerta(String codigoAlerta) {
		this.codigoAlerta = codigoAlerta;
	}
	
	
}
