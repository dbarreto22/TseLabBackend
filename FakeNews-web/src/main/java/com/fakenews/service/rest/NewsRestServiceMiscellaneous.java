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
import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.ejb.NewsEJBLocal;
import com.fakenews.ejb.SecurityLocal;
import com.fakenews.model.Checker;
import com.fakenews.model.Hecho;
import com.fakenews.model.MecanismoVerificacion;
import com.fakenews.datatypes.DTAsignarHecho;
import com.fakenews.datatypes.DTLoginBackendRequest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsRestServiceMiscellaneous {
	
	@EJB
	private NewsEJBLocal newsEJB;

	@EJB
	private SecurityLocal securityMgt;
	
	@POST
    @Path("periferico/login")
    @PermitAll
    public DTLoginResponse login(DTLoginBackendRequest request) {
		System.out.println("username: " + request.getUsername());
		System.out.println("password: " + request.getPassword());
		String token = "";
		try { 	
			
			if (securityMgt.isUserAllowed(request.getUsername(), request.getPassword())) {
				token = securityMgt.createAndSignToken(request.getUsername(), request.getPassword());
			}
		
		} catch (Exception ex) {
            System.out.println("backend/login " + ex.getMessage());
        }
		return new DTLoginResponse(token);
	}
	
	
	
}
