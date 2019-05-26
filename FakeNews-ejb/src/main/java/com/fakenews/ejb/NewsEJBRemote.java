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
//	public Noticia getNoticia(Long id);
//	public List<Publicacion> getAllPublicaciones();
//	public List<Publicacion> findAllPublicacionesByNoticia(Long idNoticia);
//	public String addPublicacion(Publicacion publicacion, Long idNoticia);

}
