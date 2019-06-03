package com.fakenews.service.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fakenews.datatypes.DTLoginResponse;
import com.fakenews.datatypes.DTMailRequest;
import com.fakenews.datatypes.DTMecanismoVerificacion;
import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.DTUsuarioBcknd;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.ejb.NewsEJBLocal;
import com.fakenews.ejb.SecurityLocal;
import com.fakenews.model.Checker;
import com.fakenews.model.Hecho;
import com.fakenews.model.MecanismoExterno;
import com.fakenews.model.MecanismoInterno;
import com.fakenews.model.MecanismoPeriferico;
import com.fakenews.model.MecanismoVerificacion;
import com.google.appengine.repackaged.com.google.common.flogger.backend.system.SystemClock;
import com.fakenews.datatypes.DTAsignarHecho;
import com.fakenews.datatypes.DTHechoMecanismo;
import com.fakenews.datatypes.DTLoginBackendRequest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsRestServiceBckend {
	
	@EJB
	private NewsEJBLocal newsEJB;

	@EJB
	private SecurityLocal securityMgt;
	
	@GET
	@Path("prueba")
	@PermitAll
	public String prueba() {
		return "Todos trabajando";
	}
	
	@GET
	@Path("pruebaToken")
	public String pruebaToken() {
		return "EL TOKEN EN TU CARA";
	}
	
	@POST
    @Path("backend/login")
    @PermitAll
    public DTLoginResponse login(DTLoginBackendRequest request) {
		System.out.println("username: " + request.getUsername());
		System.out.println("password: " + request.getPassword());
		String token = "";
		EnumRoles rol = EnumRoles.ERROR;
		try { 	
			rol = securityMgt.getRolIfAllowed(request.getUsername(), request.getPassword());
			if (rol != EnumRoles.ERROR) {
				token = securityMgt.createAndSignToken(request.getUsername(), request.getPassword());
			}
		
		} catch (Exception ex) {
            System.out.println("backend/login " + ex.getMessage());
        }
		return new DTLoginResponse(token, rol);
	}
	
	@GET
	@Path("getHechos")
	public List<Hecho> getAllHechos(){
		List<Hecho> hechos = null;
		try {
			hechos = newsEJB.getAllHechos();
		}catch (Exception ex) {
			System.out.println("backend/getHechos " + ex.getMessage());
		}
		return hechos;
	}
	
	@POST
	@Path("checker/verificarHecho")
	public DTRespuesta verificarHecho(Hecho hecho) {
		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al verificar el hecho.");
		respuesta = newsEJB.verificarHecho(hecho);
		return respuesta;
	}
	
	@POST
	@Path("submitter/asignarHecho")
	public DTRespuesta asignarHecho(DTAsignarHecho asignaHecho) {
		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al asignar el hecho.");
		respuesta = newsEJB.asignarHecho(asignaHecho.getIdHecho(),asignaHecho.getMail());
		return respuesta;
	}
	
	@GET
	@Path("getHechosByChecker/{mail}")
	public List<Hecho> getHechosByChecker(@PathParam("mail") final String mail) {
		System.out.println("mail: " + mail);
		List<Hecho> hechos = null;
		try {
			hechos = newsEJB.getHechosByChecker(mail);
		}catch (Exception ex) {
			System.out.println("getHechosByChecker" + ex.getMessage());
		}
		return hechos;
	}
	
	@GET
	@Path("backend/getCheckers")
	public List<Checker> getCheckers() {
		List<Checker> checkers = null;
		try {
			checkers = newsEJB.getCheckers();
		}catch (Exception ex) {
			System.out.println("backend/getCheckers" + ex.getMessage());
		}
		return checkers;
	}
	
	@GET
	@Path("backend/getMecanismosInternos")
	public List<MecanismoInterno> getMecanismosInternos() {
		return newsEJB.getMecanismosInternos();
	}
	
	@GET
	@Path("backend/getMecanismosExternos")
	public List<MecanismoExterno> getMecanismosExternos() {
		return newsEJB.getMecanismosExternos();
	}
	
	@POST
	@Path("backend/registro")
	public DTRespuesta registrarUsuarioBackend(DTUsuarioBcknd usuario) {
		return newsEJB.registrarUsuarioBackend(usuario);
	}
	
	@GET
	@Path("admin/getNodosPerifericos")
	public List<MecanismoPeriferico> getMecanismosPerifericos(){
		return newsEJB.getMecanismosPerifericos();
	}
	
	@POST
	@Path("admin/addMecanismoVerificacion")
	public DTRespuesta addMecanismoVerificacion(DTMecanismoVerificacion mecanismo) {
		if (mecanismo != null) {
			  System.out.println("mecanismo: " + mecanismo.getMecanismo());
			  return newsEJB.addMecanismoVerificacion(mecanismo);
		}else {
			  System.out.println("Mecanismo es vacio");
			  return new DTRespuesta("ERROR", "Mecanismo es vacio o no se puede leer");
		}

	}
	
	@POST
	@Path("admin/modificarMecanismoVerificacion")
	public DTRespuesta updateMecanismoVerificacion(DTMecanismoVerificacion mecanismo) {
		return newsEJB.updateMecanismoVerificacion(mecanismo);
	}
	
	@POST
	@Path("checker/verificarHechoMecanismo")
	public DTRespuesta verificarHechoMecanismo(DTHechoMecanismo hechoMecanismo) {
		return newsEJB.verificarHechoMecanismo(hechoMecanismo);
	}
	
	
}
