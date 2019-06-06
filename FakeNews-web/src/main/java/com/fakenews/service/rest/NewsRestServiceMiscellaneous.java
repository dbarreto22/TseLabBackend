package com.fakenews.service.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fakenews.datatypes.DTLoginResponse;
import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumHechoEstado;
import com.fakenews.ejb.NewsEJBLocal;
import com.fakenews.ejb.ToolsLocal;
import com.fakenews.model.Hecho;
import com.fakenews.datatypes.DTHechoMecanismo;
import com.fakenews.datatypes.DTLoginBackendRequest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsRestServiceMiscellaneous {
	
	@EJB
	private NewsEJBLocal newsEJB;

	@EJB
	private ToolsLocal toolsEJB;
	
	@POST
    @Path("periferico/login")
    @PermitAll
    public DTLoginResponse login(DTLoginBackendRequest request) {
		System.out.println("username: " + request.getUsername());
		System.out.println("password: " + request.getPassword());
		String token = "";
		try { 	
			
			if (toolsEJB.isUserAllowed(request.getUsername(), request.getPassword())) {
				token = toolsEJB.createAndSignToken(request.getUsername(), request.getPassword());
			}
		
		} catch (Exception ex) {
            System.out.println("backend/login " + ex.getMessage());
        }
		return new DTLoginResponse(token);
	}
	
	@POST
    @Path("resultadoVerificarHechoMecanismo")
    public DTRespuesta resultadoVerificarHechoMecanismo(DTHechoMecanismo hechoMecanismo) {
		return newsEJB.resultadoverificarHechoMecanismo(hechoMecanismo);
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
	
	@GET
	@Path("getHechosByEstado/{estado}")
	public List<Hecho> getHechosBy(@PathParam("estado") final EnumHechoEstado estado){
		List<Hecho> hechos = null;
		try {
			hechos = newsEJB.getHechosByEstado(estado);
		}catch (Exception ex) {
			System.out.println("backend/getHechosByEstado " + ex.getMessage());
		}
		return hechos;
	}
	
}
