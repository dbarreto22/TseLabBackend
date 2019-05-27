package com.fakenews.ejb;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fakenews.datatypes.DTLoginResponse;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.ejb.NewsEJBLocal;
import com.fakenews.ejb.SecurityLocal;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Submitter;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.resteasy.util.Base64;

/**
 *
 * @author rmoreno
 */

@Stateless
public class Security implements SecurityLocal {
    
    private final String secret = "telofirmoasinomas";
    
    @EJB
	private NewsEJBLocal newsEJB; 
    
    @Override
    public String createAndSignToken(String username, String password) {
        String message = "";
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer("fakeNews")
                    .withIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(-3))))
                    .withClaim("username", Base64.encodeBytes(username.getBytes()))
                    .withClaim("password", Base64.encodeBytes(password.getBytes()))
                    .withExpiresAt(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(-3))))
                    .sign(algorithm);
            message = token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            message = exception.getMessage();
        }
        return message;
    }
    
    @Override
    public String getSecret(){
        return secret;
    }
    
    @Override
    public Boolean verifyTokenGoogle(String token_id) {
    	System.out.println("verifyTokenGoogle");
    	Boolean loginOk = false;
    	
    	String client_id1 = newsEJB.getParam("CLIENT_ID1");
    	System.out.println("client_id: " + client_id1);
    	
    	
    	JacksonFactory jsonFactory = new JacksonFactory();
    	HttpTransport transport = new NetHttpTransport();
    	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
	    .setAudience(Collections.singletonList(client_id1))
	    // Or, if multiple clients access the backend:
	    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
	    .build();
    	
    	System.out.println("token_id: " + token_id);
    	
    	GoogleIdToken idToken = null;
    	
		try {
			idToken = verifier.verify(token_id);
			
			if (idToken != null) {
	    	  Payload payload = idToken.getPayload();

	    	  // Print user identifier
	    	  String userId = payload.getSubject();
	    	  System.out.println("User ID: " + userId);
	    	  loginOk = true;
	    	}
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
			loginOk = false;
		}   	
    	
    	return loginOk;
    }
    
    @Override
    public Boolean isUserAllowed(String username, String password){
    	EnumRoles rol = newsEJB.getRol(username);
        
    	switch (rol) {
    	case CITIZEN: 
    		return this.verifyTokenGoogle(password);
    		
    	case ADMIN:
    		Admin admin = newsEJB.getAdmin(username);
    		if (admin != null && admin.getPassword().equals(password)) {
    			return true;
    		}
    		
    	case CHECKER:
    		Checker checker = newsEJB.getChecker(username);
    		if (checker != null && checker.getPassword().equals(password)) {
    			return true;
    		}
    		        		
    	case SUBMITTER:	
    		Submitter sub = newsEJB.getSubmitter(username);
    		if(sub != null && sub.getPassword().equals(password)) {
    			return true;
    		}
    	
    	default:
    		return false;
    		
    }
    }
    
    @Override
    public EnumRoles getRolIfAllowed(String username, String password){
    	System.out.print("getRolIfAllowed");
    	EnumRoles rolSalida = EnumRoles.ERROR;
    	EnumRoles rol = newsEJB.getRol(username);
        System.out.println("ROL: " + rol.rolStr());
        switch (rol) {
        	case CITIZEN: 
        		if (this.verifyTokenGoogle(password)) {
        			rolSalida = rol;
        		};
        		
        	case ADMIN:
        		Admin admin = newsEJB.getAdmin(username);
        		if (admin != null && admin.getPassword().equals(password)) {
        			System.out.println("ES IGUAL LA PASSWORD");
        			rolSalida = rol;
        		}
        		
        	case CHECKER:
        		Checker checker = newsEJB.getChecker(username);
        		if (checker != null && checker.getPassword().equals(password)) {
        			rolSalida = rol;
        		}
        		        		
        	case SUBMITTER:	
        		Submitter sub = newsEJB.getSubmitter(username);
        		if(sub != null && sub.getPassword().equals(password)) {
        			rolSalida = rol;
        		}        		
        }
        
        System.out.println("rolSalida: " + rolSalida.rolStr());
        return rolSalida;
        
    }
	    
}
