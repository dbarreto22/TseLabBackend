package com.fakenews.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fakenews.datatypes.EnumParam;
import com.fakenews.interfaces.*;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import org.jboss.resteasy.util.Base64;

/**
 *
 * @author rmoreno
 */
public class SecurityMgr implements SecurityMgt {
    
    private final String secret = "telofirmoasinomas";
    
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
    public Boolean verifyTokenGoogle(String token_id, String client_id) {
    	JacksonFactory jsonFactory = new JacksonFactory();
        HttpTransport transport = UrlFetchTransport.getDefaultInstance();
    	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
	    // Specify the CLIENT_ID of the app that accesses the backend:
	    .setAudience(Collections.singletonList(client_id))
	    // Or, if multiple clients access the backend:
	    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
	    .build();
    	
    	GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(token_id);
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
		
    	if (idToken != null) {
    	  Payload payload = idToken.getPayload();

    	  // Print user identifier
    	  String userId = payload.getSubject();
    	  System.out.println("User ID: " + userId);
    	}
    	
    	return idToken != null;
    }
    
//	    @Override
//	    public Boolean verifyToken(String token){
//	        
//	    }
	    
}
