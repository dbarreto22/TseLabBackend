package com.fakenews.service.rest.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.net.ssl.ManagerFactoryParameters;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//
import com.fakenews.ejb.NewsEJBLocal;
import com.fakenews.interfaces.SecurityMgt;

//import com.mynews.model.Noticia;
//import com.mynews.model.Publicacion;
import javax.ws.rs.core.MediaType;

import com.fakenews.ManagersFactory;
import com.fakenews.datatypes.DTLoginCitizenRequest;
import com.fakenews.datatypes.DTLoginResponse;
import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumParam;
import com.fakenews.datatypes.EnumRoles;

@Path("/citizen")
public class NewsRestServiceCitizenImpl {
	
	@EJB
	private NewsEJBLocal newsEJB; 
	SecurityMgt securityMgt = ManagersFactory.getInstance().getSecurityMgt();
	
    public DTLoginResponse login(DTLoginCitizenRequest request) {
    	
    	String client_id = newsEJB.getParam(EnumParam.CLIENT_ID);
    	
    	Boolean loginOk = securityMgt.verifyTokenGoogle(request.getToken_id(), client_id);
    	  
    	if (loginOk) {
    	  EnumRoles rol = newsEJB.citizenLogin(request.getMail());
    	  if (rol == EnumRoles.ERROR){
    		  return new DTLoginResponse("", rol);
    	  }else {
    		  String jwt = securityMgt.createAndSignToken(request.getMail(), request.getToken_id());
    		  return new DTLoginResponse(jwt, rol);
    	  }
    	} else {
    	  System.out.println("Invalid ID token.");
    	  return new DTLoginResponse("", EnumRoles.ERROR);
    	}
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
