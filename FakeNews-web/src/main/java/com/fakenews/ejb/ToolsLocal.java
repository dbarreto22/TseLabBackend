package com.fakenews.ejb;

import javax.ejb.Local;

import com.fakenews.datatypes.DTLoginResponse;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.model.Hecho;

/**
	 *
	 * @author rmoreno
	 */
@Local
public interface ToolsLocal {
    
    public String getSecret();
    
    public String createAndSignToken(String username, String password);
    
    public Boolean verifyTokenGoogle(String token_id, String nombre);
    
    public EnumRoles getRolIfAllowed(String username, String password);
    
    public Boolean isUserAllowed(String username, String password);

//	public Boolean sendNotification(Hecho hecho);
    
}
