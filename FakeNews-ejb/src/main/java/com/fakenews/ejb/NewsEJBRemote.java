package com.fakenews.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.model.Hecho;

@Remote
public interface NewsEJBRemote {
	public List<Hecho> getAllHechos();
	public DTRespuesta saveHecho(Hecho hecho);
	public String getParam(String name);
	public EnumRoles citizenLogin(String email);
	public EnumRoles getRol(String email);
	public DTRespuesta verificarHecho(Hecho hecho);
	public DTRespuesta asignarHecho(Long idHecho, String mail);
}
