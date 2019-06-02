package com.fakenews.datatypes;

public class DTMecanismoVerificacion {
	
	private Long id;
	
	private String descripcion;
	
	private Boolean habilitado;
	
	private String usuario;
	
	private String password;
	
	private String url;
	
	private EnumTipoMecanismo mecanismo;
	
	public DTMecanismoVerificacion(Long id, String descripcion, Boolean habilitado, String usuario, String password,
			String url, EnumTipoMecanismo mecanismo) {
		this.id = id;
		this.descripcion = descripcion;
		this.habilitado = habilitado;
		this.usuario = usuario;
		this.password = password;
		this.url = url;
		this.mecanismo = mecanismo;
	}

	public DTMecanismoVerificacion(String descripcion, Boolean habilitado, String usuario, String password, String url,
			EnumTipoMecanismo mecanismo) {
		this.descripcion = descripcion;
		this.habilitado = habilitado;
		this.usuario = usuario;
		this.password = password;
		this.url = url;
		this.mecanismo = mecanismo;
	}

	public DTMecanismoVerificacion() {
		
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public EnumTipoMecanismo getMecanismo() {
		return mecanismo;
	}

	public void setMecanismo(EnumTipoMecanismo mecanismo) {
		this.mecanismo = mecanismo;
	}
	
		
}
