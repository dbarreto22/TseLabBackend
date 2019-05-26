package com.fakenews.interfaces;

	/**
	 *
	 * @author rmoreno
	 */
	public interface SecurityMgt {
	    
	    public String getSecret();
	    
	    public String createAndSignToken(String username, String password);
	    
	    public Boolean verifyTokenGoogle(String token_id, String client_id);
}
