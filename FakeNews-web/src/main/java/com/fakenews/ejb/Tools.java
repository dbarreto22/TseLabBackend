package com.fakenews.ejb;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fakenews.datatypes.DTLoginResponse;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.ejb.NewsEJBLocal;
import com.fakenews.ejb.ToolsLocal;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Hecho;
import com.fakenews.model.MecanismoPeriferico;
import com.fakenews.model.Submitter;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.jboss.resteasy.util.Base64;

/**
 *
 * @author rmoreno
 */

@Stateless
public class Tools implements ToolsLocal {
    
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
            message = exception.getMessage();
        }
        return message;
    }
    
    @Override
    public String getSecret(){
        return secret;
    }
    
    @Override
    public Boolean verifyTokenGoogle(String token_id, String nombre) {
    	System.out.println("verifyTokenGoogle");
    	Boolean loginOk = false;
    	
    	String client_id1 = newsEJB.getParam("CLIENT_ID1");
    	System.out.println("client_id: " + client_id1);
    	
    	String client_id2 = newsEJB.getParam("CLIENT_ID2");
    	System.out.println("client_id: " + client_id2);
    	
    	String client_id3 = newsEJB.getParam("CLIENT_ID3");
    	System.out.println("client_id: " + client_id3);
    	   	
    	
    	JacksonFactory jsonFactory = new JacksonFactory();
    	HttpTransport transport = new NetHttpTransport();
    	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
	    .setAudience(Arrays.asList(client_id1, client_id2,client_id3))
	    .build();
    	
    	System.out.println("token_id: " + token_id);
    	
    	GoogleIdToken idToken = null;
    	
		try {
			idToken = verifier.verify(token_id);
			
			if (idToken != null) {
	    	  Payload payload = idToken.getPayload();

	    	  // Print user identifier
	    	  String userId = payload.getSubject();
	    	  nombre = (String) payload.get("name");
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
    	String nombre = "";
    	MecanismoPeriferico periferico = newsEJB.getMecanismoPeriferico(username);
    	
    	if(periferico != null) {
    		return periferico.getPassword().equals(password);
    				
    	}else {
    		EnumRoles rol = newsEJB.getRol(username);
        	
        	switch (rol) {
        	case CITIZEN: 
        		return this.verifyTokenGoogle(password, nombre);
        		
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
    	
    }
    
    @Override
    public EnumRoles getRolIfAllowed(String username, String password){
    	System.out.print("getRolIfAllowed");
    	EnumRoles rolSalida = EnumRoles.ERROR;
    	EnumRoles rol = newsEJB.getRol(username);
        System.out.println("ROL: " + rol.rolStr());
        String nombre = "";
        switch (rol) {
        	case CITIZEN: 
        		if (this.verifyTokenGoogle(password,nombre)) {
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
    
//    @Override
//    public Boolean sendNotification(Hecho hecho) {
//        String title = "Se ha verificado un hecho";
//        String message = "Usted ha obtenido un ";
//        String deviceToken = "";
//        String tipo = "";
//        int response = 0;
//        if (notaObj instanceof Estudiante_Curso) {
//            Estudiante_Curso curso = (Estudiante_Curso) notaObj;
//            deviceToken = curso.getUsuario().getDeviceToken();
//            tipo = "curso";
//            message += curso.getCalificacion();
//            message += " en el curso de " + curso.getCurso().getAsignatura_Carrera().getAsignatura().getNombre() + ".";
//        } else {
//            if (notaObj instanceof Estudiante_Examen) {
//                Estudiante_Examen examen = (Estudiante_Examen) notaObj;
//                deviceToken = examen.getUsuario().getDeviceToken();
//                tipo = "examen";
//                message += examen.getCalificacion();
//                message += " en el examen de " + examen.getExamen().getAsignatura_Carrera().getAsignatura().getNombre() + ".";
//            }
//        }  
//    	return false;
//    }
    
//    public Boolean sendPushWithSimpleAndroid(String title, String message, String deviceToken) {
//  	
//		if (deviceToken != null && deviceToken != "") {
//	        System.out.println("deviceToken: " + deviceToken); 
//	        JsonObject info = new JsonObject();
//	        JsonObject data = new JsonObject();
//	        JsonObject json = new JsonObject();
//	        int response = 0;
//	        
//	        data.addProperty("title", title);
//	        data.addProperty("body", message);
//	        json.addProperty("to", deviceToken);
//	        json.add("data", data);
//	        URL url;
//	
//	        try {
//	            url = new URL("https://fcm.googleapis.com/fcm/send");
//	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	            conn.setRequestProperty("Authorization", "key=" + ClaveFireBase);
//	            conn.setRequestProperty("Content-Type", "application/json");
//	            conn.setRequestMethod("POST");
//	            conn.setDoOutput(true);
//	            // Send FCM message content.
//	            OutputStream outputStream = conn.getOutputStream();
//	            String jsonString = new Gson().toJson(json);
//	            System.out.println("jsonString: " + jsonString);
//	            byte[] utf8JsonString = jsonString.getBytes("UTF8");
//	            outputStream.write(utf8JsonString);
//	            System.out.println("utf8JsonString: " + utf8JsonString.toString());
//	
//	            response = conn.getResponseCode();
//	            System.out.println(conn.getResponseCode());
//	            System.out.println(conn.getResponseMessage());
//	            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//	            StringBuilder sb = new StringBuilder();
//	            String output;
//	            while ((output = br.readLine()) != null) {
//	                sb.append(output);
//	            }
//	            System.out.println("sb.toString(): " + sb.toString());
//	        } catch (Exception ex) {
//	            response = 505;
//	            Logger.getLogger(InitMgr.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
//	        }
//	    }
}
