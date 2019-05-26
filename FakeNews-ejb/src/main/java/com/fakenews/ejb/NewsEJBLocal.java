package com.fakenews.ejb;

import java.util.List;

import javax.ejb.Local;

import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumParam;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Hecho;
import com.fakenews.model.Submitter;

@Local
public abstract interface NewsEJBLocal {

	public List<Hecho> getAllHechos();
	public DTRespuesta saveHecho(Hecho hecho);
	public String getParam(EnumParam name);
	public EnumRoles citizenLogin(String email);
	public EnumRoles getRol(String email);
	public Admin getAdmin(String mail);
	public Checker getChecker(String mail);
	public Submitter getSubmitter(String mail);
//	public Noticia getNoticia(Long id);
//	public List<Publicacion> getAllPublicaciones();
//	public List<Publicacion> findAllPublicacionesByNoticia(Long idNoticia);
//	public String addPublicacion(Publicacion publicacion, Long idNoticia);

}
