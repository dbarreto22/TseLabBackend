package com.fakenews.datatypes;

import java.io.Serializable;

public class DTLoginResponse implements Serializable{
	
	private String jwt;
	private EnumRoles rol;
	
	public DTLoginResponse() {
		
	}
	
	public DTLoginResponse(String jwt, EnumRoles rol) {
		this.jwt = jwt;
		this.rol = rol;
	}
	
	public DTLoginResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public EnumRoles getRol() {
		return rol;
	}

	public void setRol(EnumRoles rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jwt == null) ? 0 : jwt.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTLoginResponse other = (DTLoginResponse) obj;
		if (jwt == null) {
			if (other.jwt != null)
				return false;
		} else if (!jwt.equals(other.jwt))
			return false;
		if (rol != other.rol)
			return false;
		return true;
	}
	
}
