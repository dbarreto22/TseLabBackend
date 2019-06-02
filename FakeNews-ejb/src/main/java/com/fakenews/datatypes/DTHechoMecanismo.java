package com.fakenews.datatypes;

public class DTHechoMecanismo {
	
	private Long idHecho;
	
	private Long idMecanismoVerificacion;

	public DTHechoMecanismo() {
		
	}

	public DTHechoMecanismo(Long idHecho, Long idMecanismoVerificacion) {
		this.idHecho = idHecho;
		this.idMecanismoVerificacion = idMecanismoVerificacion;
	}

	public Long getIdHecho() {
		return idHecho;
	}

	public void setIdHecho(Long idHecho) {
		this.idHecho = idHecho;
	}

	public Long getIdMecanismoVerificacion() {
		return idMecanismoVerificacion;
	}

	public void setIdMecanismoVerificacion(Long idMecanismoVerificacion) {
		this.idMecanismoVerificacion = idMecanismoVerificacion;
	}
	
}
