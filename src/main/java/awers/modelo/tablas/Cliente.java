package awers.modelo.tablas;

public class Cliente {
	private String nombre;
	private String numeroCuenta;
	private String telefono;
	private String dir;
	private int tiempoEspera;
	
	
	


	public Cliente(String nombre, String numeroCuenta, String telefono, int tiempoEspera, String dir) {
		this.nombre = nombre;
		this.numeroCuenta = numeroCuenta;
		this.telefono = telefono;
		this.tiempoEspera = tiempoEspera;
		this.dir = dir;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public int getTiempoEspera() {
		return tiempoEspera;
	}
	public void setTiempoEspera(int tiempoEspera) {
		this.tiempoEspera = tiempoEspera;
	}
	
		
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public String getIdAlerta(){
		return this.getTelefono()+"_"+this.getDir();
	}

	public String getNroCuentaParaEnvio() throws Exception{
		String nroCuenta = this.numeroCuenta.trim();
		if(nroCuenta.length() < 4){
			throw new Exception("El numero de cuenta es incorrecto");
		}
		
		return nroCuenta.substring(nroCuenta.length()-4, nroCuenta.length());

		
	}
	
}
