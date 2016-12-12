package awers.sistema;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Configuracion {
	private static final Logger LOG = Logger.getLogger(Configuracion.class.getName());

	private int tiempoPrimerAlerta;
	private int tiempoSegundoAlerta;

	// ?user=admin&passwd=232431&cat=1&to=%s&text=%s

	private String user;
	private String passwd;

	private String urlAviso;
	private String urlFinAlerta;

	private String mensajeAvisoAlta;
	private String mensajeAvisoPendiente;
	private String mensajeBajaAlerta;
	private String mensajeNueveOnce;
	private String mensajeNoServicio;
	private String mensajeAlertaExistente;

	public int getTiempoPrimerAlerta() {
		return this.tiempoPrimerAlerta;
	}

	public void setTiempoPrimerAlerta(int tiempoPrimerAlerta) {
		this.tiempoPrimerAlerta = tiempoPrimerAlerta;
	}

	public int getTiempoSegundoAlerta() {
		return this.tiempoSegundoAlerta;
	}

	public void setTiempoSegundoAlerta(int tiempoSegundoAlerta) {
		this.tiempoSegundoAlerta = tiempoSegundoAlerta;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUrlAviso() {
		return this.urlAviso;
	}

	public void setUrlAviso(String urlAviso) {
		String definitiva = urlAviso + "?user=" + this.user + "&passwd=" + this.passwd
		        + "&cat=1&to=%s&text=%s";
		this.urlAviso = definitiva;
	}

	public String getUrlFinAlerta() {
		return this.urlFinAlerta;
	}

	public void setUrlFinAlerta(String urlFinAlerta) {
		// String definitiva = urlNueveOnce+"?CLIENT_NUMBER=%s&type=ALERT";
		this.urlFinAlerta = urlFinAlerta;
	}

	public String getMensajeAvisoAlta() {
		return this.mensajeAvisoAlta;
	}

	public void setMensajeAvisoAlta(String mensajeAvisoAlta) {
		this.mensajeAvisoAlta = this.formatoTexto(mensajeAvisoAlta);
	}

	public String getMensajeAvisoPendiente() {
		return this.mensajeAvisoPendiente;
	}

	public void setMensajeAvisoPendiente(String mensajeAvisoPendiente) {
		this.mensajeAvisoPendiente = this.formatoTexto(mensajeAvisoPendiente);
	}

	public String getMensajeNueveOnce() {
		return this.mensajeNueveOnce;
	}

	public void setMensajeNueveOnce(String mensajeNueveOnce) {
		this.mensajeNueveOnce = this.formatoTexto(mensajeNueveOnce);
	}

	public String getMensajeBajaAlerta() {
		return this.mensajeBajaAlerta;
	}

	public void setMensajeBajaAlerta(String mensajeBajaAlerta) {
		this.mensajeBajaAlerta = this.formatoTexto(mensajeBajaAlerta);
	}

	public String getMensajeNoServicio() {
		return this.mensajeNoServicio;
	}

	public void setMensajeNoServicio(String mensajeNoServicio) {
		this.mensajeNoServicio = this.formatoTexto(mensajeNoServicio);
	}

	public String getMensajeAlertaExistente() {
		return this.mensajeAlertaExistente;
	}

	public void setMensajeAlertaExistente(String mensajeAlertaExistente) {
		this.mensajeAlertaExistente = this.formatoTexto(mensajeAlertaExistente);
	}

	private String formatoTexto(String original) {
		return original.replace(" ", "%20");
	}

	@SuppressWarnings("unused")
	private void inicializar() {
		this.loadProperties();
		System.out.println("Sistema inicializado!");
		LOG.debug("Sistema inicializado!");

	}

	private void loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			// input = new FileInputStream("configuracion.properties");
			input = Configuracion.class.getClassLoader().getResourceAsStream(
			        "configuracion.properties");

			// load a properties file
			prop.load(input);
			this.setUrlFinAlerta(prop.getProperty("url_fin_alerta"));
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

	}
}
