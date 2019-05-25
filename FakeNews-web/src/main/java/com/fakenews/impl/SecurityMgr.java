package com.fakenews.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fakenews.interfaces.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    
//	    @Override
//	    public Boolean verifyToken(String token){
//	        
//	    }
	    
}
