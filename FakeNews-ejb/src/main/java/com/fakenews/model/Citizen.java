package com.fakenews.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Citizen extends Usuario implements Serializable {
	
	@Basic
	private Boolean suscripto;
	
	@OneToMany(targetEntity = Hecho.class, fetch = FetchType.LAZY)
	private List<Hecho> hechos; 
	
	public Citizen() {
		
	}

	public Citizen(String email, String nickname, String telefono, Boolean suscripto) {
		super(email, nickname, telefono);
		this.suscripto = suscripto;
	}

	public Boolean getSuscripto() {
		return suscripto;
	}

	public void setSuscripto(Boolean suscripto) {
		this.suscripto = suscripto;
	}

	public List<Hecho> getHechos() {
		return hechos;
	}

	public void setHechos(List<Hecho> hechos) {
		this.hechos = hechos;
	}
			
}
