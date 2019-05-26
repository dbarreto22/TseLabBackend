package com.fakenews.interfaces;

import com.fakenews.datatypes.DTLoginResponse;
import com.fakenews.datatypes.EnumRoles;

/**
	 *
	 * @author rmoreno
	 */
	public interface SecurityMgt {
	    
	    public String getSecret();
	    
	    public String createAndSignToken(String username, String password);
	    
	    public Boolean verifyTokenGoogle(String token_id);
	    
	    public EnumRoles getRolIfAllowed(String username, String password);
	    
	    public Boolean isUserAllowed(String username, String password);
}
