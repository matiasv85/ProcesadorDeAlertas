package awers.controladores;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import awers.sistema.Sistema;

@Controller
@RequestMapping("/procesadorDeMensajes")
public class ProcesadorDeMensajes implements ApplicationContextAware {

	private Sistema sistema;

	@RequestMapping(value = "/procesarMensaje", method = RequestMethod.GET)
	public void procesarMensaje(@RequestParam("client_number") String clientNumber,
	        @RequestParam("telefono") String telefono,
	        @RequestParam("tiempo_duracion_alerta") int tiempoDuracionAlerta,
	        @RequestParam("codigo_alerta") String codigoAlerta) {

		this.sistema.getAdministradorDeHilos().levantarAlerta(clientNumber, telefono,
		        tiempoDuracionAlerta, codigoAlerta);

	}

	@RequestMapping(value = "/bajarAlerta", method = RequestMethod.GET)
	public void bajarAlerta(@RequestParam("id") String id, @RequestParam("dir") String dir) {
		this.sistema.getAdministradorDeHilos().anularAlerta(id, dir);

	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.sistema = (Sistema) context.getBean("sistema");

	}

	@RequestMapping(value = "/helth-check", method = RequestMethod.GET)
	public String helthCheck() {
		return "{status : OK!}";

	}

}
