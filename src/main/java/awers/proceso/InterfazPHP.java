package awers.proceso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import awers.modelo.tablas.Cliente;
import awers.sistema.Sistema;

public class InterfazPHP {
	static Logger log = Logger.getLogger(InterfazPHP.class.getName());
	SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
	private Sistema sistema;

	public void informarRegistroAlerta(Cliente cliente) {
		synchronized (cliente.getIdAlerta()) {
			String url = String.format(this.sistema.getConfiguracion().getUrlAviso(),
			        cliente.getTelefono(), this.sistema.getConfiguracion().getMensajeAvisoAlta()
			                + "%20" + (this.formatoFecha.format(new Date())));
			this.abrirUrl(url);
		}

	}

	public void informarAlertaAbierta(Cliente cliente) {
		synchronized (cliente.getIdAlerta()) {
			String url = String.format(this.sistema.getConfiguracion().getUrlAviso(),
			        cliente.getTelefono(),
			        this.sistema.getConfiguracion().getMensajeAvisoPendiente() + "%20"
			                + (this.formatoFecha.format(new Date())));
			this.abrirUrl(url);
		}

	}

	public void informarAlertaExistente(Cliente cliente) {
		synchronized (cliente.getIdAlerta()) {
			String url = String.format(this.sistema.getConfiguracion().getUrlAviso(),
			        cliente.getTelefono(),
			        this.sistema.getConfiguracion().getMensajeAlertaExistente() + "%20"
			                + (this.formatoFecha.format(new Date())));
			this.abrirUrl(url);
		}

	}

	public void informarNueveOnce(String clientNumber, String codigoAlerta) {
		synchronized (clientNumber) {
			try {
				String url = String.format(this.sistema.getConfiguracion().getUrlFinAlerta(),
				        clientNumber, codigoAlerta);

				this.abrirUrl(url);
				this.sistema.getAdministradorDeHilos().anularAlerta(clientNumber, codigoAlerta);
			} catch (Exception exc) {
				log.error(exc.getMessage());
			}
		}

	}

	/*
	 * public void informarBajaAlerta(String clientNumber, String codigoAlerta)
	 * { synchronized (clientNumber) { String url =
	 * String.format(this.sistema.getConfiguracion().getUrlAviso(),
	 * cliente.getTelefono(),
	 * this.sistema.getConfiguracion().getMensajeBajaAlerta() + "%20" +
	 * (this.formatoFecha.format(new Date()))); this.abrirUrl(url); }
	 * 
	 * }
	 * 
	 * public void informarNoServicio(String telefono) { synchronized (telefono)
	 * { String url =
	 * String.format(this.sistema.getConfiguracion().getUrlAviso(), telefono,
	 * this.sistema.getConfiguracion().getMensajeNoServicio() + "%20" +
	 * (this.formatoFecha.format(new Date()))); this.abrirUrl(url); }
	 * 
	 * }
	 */
	public Sistema getSistema() {
		return this.sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public void abrirUrl(String urlStr) {
		try {

			URL url = new URL(urlStr);
			// URL url = new
			// URL("http://spgprs.dyndns.org:8061/sendmsg?user=admin&passwd=232431&cat=1&to=2215468204&text=PruebasMatias");
			log.debug(("Url: " + url.toString()));
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(false);
			httpCon.setRequestMethod("GET");
			httpCon.connect();
			httpCon.getResponseCode();
			httpCon.disconnect();

		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
