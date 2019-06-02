package com.fakenews.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fakenews.datatypes.EnumTipoCalificacion;

@Entity
public class ResultadoMecanismo implements Serializable {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Basic
	private EnumTipoCalificacion calificacion;
	
	@ManyToOne(targetEntity = MecanismoVerificacion.class)
	private MecanismoVerificacion mecanismo;

	public ResultadoMecanismo() {
		
	}

	public ResultadoMecanismo(MecanismoVerificacion mecanismo) {
		this.mecanismo = mecanismo;
	}

	public ResultadoMecanismo(EnumTipoCalificacion calificacion, MecanismoVerificacion mecanismo) {
		this.calificacion = calificacion;
		this.mecanismo = mecanismo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnumTipoCalificacion getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(EnumTipoCalificacion calificacion) {
		this.calificacion = calificacion;
	}

	public MecanismoVerificacion getMecanismo() {
		return mecanismo;
	}

	public void setMecanismo(MecanismoVerificacion mecanismo) {
		this.mecanismo = mecanismo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calificacion == null) ? 0 : calificacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mecanismo == null) ? 0 : mecanismo.hashCode());
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
		ResultadoMecanismo other = (ResultadoMecanismo) obj;
		if (calificacion != other.calificacion)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mecanismo == null) {
			if (other.mecanismo != null)
				return false;
		} else if (!mecanismo.equals(other.mecanismo))
			return false;
		return true;
	}
	
}
