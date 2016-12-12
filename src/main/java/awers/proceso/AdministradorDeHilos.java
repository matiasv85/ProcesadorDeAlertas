package awers.proceso;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import awers.beans.Alerta;
import awers.modelo.dao.AlertaDAO;
import awers.modelo.dao.LogmdiDAO;
import awers.modelo.tablas.AlertaTbl;

public class AdministradorDeHilos implements ApplicationContextAware {
	public HashMap<String, Alerta> alertas;
	private ApplicationContext contexto;
	private AlertaDAO alertaDao;
	private LogmdiDAO logmdiDao;

	private static final Logger log = Logger.getLogger(AdministradorDeHilos.class.getName());

	public AdministradorDeHilos() {
		this.alertas = new HashMap<String, Alerta>();

	}

	public void anularAlerta(String clientNumber, String codigoAlerta) {
		String idAlerta = clientNumber + "_" + codigoAlerta;
		this.alertas.get(idAlerta).anularAlerta();
		this.alertas.remove(idAlerta);
		this.alertaDao.eliminarPorId(clientNumber, codigoAlerta);
	}

	public void levantarAlerta(String clientNumber, String telefono, int tiempoDuracionAlerta,
	        String codigoAlerta) {
		String idAlerta = clientNumber + "_" + codigoAlerta;
		try {
			// llamar a una URL para indicar el alta del servicio
			/*
			 * ((InterfazPHP) this.contexto.getBean("interfazPHP"))
			 * .informarRegistroAlerta(this.cliente);
			 */
			// M�todo privado que crea el Thread -1 significa que tiene que
			// tomar el tiempo del cliente
			this.crearAlerta(clientNumber, tiempoDuracionAlerta, codigoAlerta, idAlerta, telefono);

			this.registrarAlerta(clientNumber, tiempoDuracionAlerta, telefono, codigoAlerta);

		} catch (Exception exc) {
			log.error(exc.getMessage());
		}

	}

	private void registrarAlerta(String clientNumber, int tiempoDuracionAlerta, String telefono,
	        String codigoAlerta) {
		this.alertaDao.guardar(new AlertaTbl(clientNumber, telefono, codigoAlerta,
		        tiempoDuracionAlerta));

	}

	public void setAlertaDao(AlertaDAO alertaDao) {
		this.alertaDao = alertaDao;
	}
	
	public void setLogmdiDao(LogmdiDAO logmdiDao) {
		this.logmdiDao = logmdiDao;
	}

	public void inializarAlertasPendientes() {
		try {
			List<AlertaTbl> alertas = this.alertaDao.obtener();
			Iterator<AlertaTbl> i = alertas.iterator();
			DateTime ahora = new DateTime();

			while (i.hasNext()) {
				AlertaTbl alerta = i.next();
				DateTime fechaActivacion = new DateTime(alerta.getFechaActivacion());
				int transcurridos = Minutes.minutesBetween(fechaActivacion, ahora).getMinutes();
				if (transcurridos < alerta.getDuracion()) {
					int tiempoEspera = (alerta.getDuracion() - transcurridos);
					this.crearAlerta(alerta.getClientNumber(), tiempoEspera,
					        alerta.getCodigoAlerta(), null, alerta.getTelefono());
				}

			}
		} catch (Exception exc) {
			log.error(exc.getMessage());
		}

	}

	private void crearAlerta(String clientNumber, int tiempoPrimerAlerta, String codigoAlerta,
	        String idAlerta, String telefono) {

		Alerta alerta = new Alerta(clientNumber,
		        (InterfazPHP) this.contexto.getBean("interfazPHP"), tiempoPrimerAlerta,
		        codigoAlerta, logmdiDao, telefono);

		this.alertas.put(clientNumber + "_" + codigoAlerta, alerta);
		new Thread(alerta).start();
	}

	@Override
	public void setApplicationContext(ApplicationContext contexto) throws BeansException {
		this.contexto = contexto;

	}
}
