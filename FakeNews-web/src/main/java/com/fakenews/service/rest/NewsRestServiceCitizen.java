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

import com.fakenews.ManagersFactory;
import com.fakenews.datatypes.DTLoginCitizenRequest;
import com.fakenews.datatypes.DTLoginResponse;
import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.ejb.NewsEJBLocal;
import com.fakenews.interfaces.SecurityMgt;

@Path("/citizen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsRestServiceCitizen {
	
	@EJB
	private NewsEJBLocal newsEJB; 
	SecurityMgt securityMgt = ManagersFactory.getInstance().getSecurityMgt();
	
	@POST
    @Path("login")
    @PermitAll	
    public DTLoginResponse login(DTLoginCitizenRequest request) {
		String token = "";
		EnumRoles rol = EnumRoles.ERROR;
		System.out.println("/citizen/login");
		System.out.println("mail: " + request.getMail() + " token_id: " + request.getToken_id());
		
		try { 		    	
	    	Boolean loginOk = securityMgt.verifyTokenGoogle(request.getToken_id());
	    	System.out.println("loginOk: " + loginOk.toString());
	    	  
	    	if (loginOk) {
	    	  rol = newsEJB.citizenLogin(request.getMail());
	    	  if (rol != EnumRoles.ERROR){
	    		  token = securityMgt.createAndSignToken(request.getMail(), request.getToken_id());
	    	  }
	    	}
	    	
		} catch (Exception ex) {
            System.out.println("citizen/login " + ex.getMessage());
        }
		return new DTLoginResponse(token, rol);
	}
	
//	@POST
//	@Path("addHecho")
//	public DTRespuesta addHecho(Hecho hec) {
//		return newsEJB.getNoticia(id);
//	}
//	
//	@GET
//	@Path("getAllPublicaciones")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Publicacion> getAllPublicaciones(){
//		return newsEJB.getAllPublicaciones();
//	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("findAllPublicacionesByNoticia/{idNoticia}")
//	public List<Publicacion> findAllPublicacionesByNoticia(@PathParam("idNoticia") Long idNoticia){
//		return newsEJB.findAllPublicacionesByNoticia(idNoticia);
//	}
//	
//	@POST
//	@Path("addPublicacion")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String addPublicacion(@HeaderParam("idNoticia") Long idNoticia,Publicacion publicacion) {
//		return newsEJB.addPublicacion(publicacion, idNoticia);
//	}
//
//	@POST
//	@Path("addNoticia")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String addNoticia(Noticia noticia) {
//		return newsEJB.addNoticia(noticia.getTitulo(),noticia.getDescripcion());
//	}
	
}
