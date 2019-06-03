package com.fakenews.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class MecanismoInterno extends MecanismoVerificacion implements Serializable {
	
	public MecanismoInterno() {
		
	}

	public MecanismoInterno(String descripcion, Boolean habilitado) {
		super(descripcion, habilitado);
	}
	
	public MecanismoInterno(Long id, String descripcion, Boolean habilitado) {
		super(id, descripcion, habilitado);
	}
	
}
