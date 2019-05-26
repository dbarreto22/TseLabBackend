package com.fakenews.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.logging.Logger;

import com.fakenews.data.NewsPersistentEJBLocal;
import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Citizen;
import com.fakenews.model.Hecho;
import com.fakenews.model.Submitter;

import java.util.logging.Level;

/**
 * Session Bean implementation class NewsEJB
 */
@Stateless
public class NewsEJB implements NewsEJBRemote, NewsEJBLocal {
	
	@EJB
	private NewsPersistentEJBLocal newsDataEJB;
	
	@Override
	public List<Hecho> getAllHechos(){
		return newsDataEJB.getAllHechos();
	}
	
	@Override
	public DTRespuesta saveHecho(Hecho hecho) {
		return newsDataEJB.saveHecho(hecho);	
	}	
	
	@Override
	public String getParam(String name) {
		System.out.println("getParam: " + name);
		return newsDataEJB.getParam(name);
	}
	
	@Override
	public EnumRoles citizenLogin(String email) {
		if (newsDataEJB.getCitizen(email) != null) {
			return EnumRoles.CITIZEN;
		}else {
			return newsDataEJB.saveCitizen(new Citizen(email));
		}
	}
	
	@Override
	public EnumRoles getRol(String mail) {
		return newsDataEJB.getRol(mail);
	}
	
	@Override
	public Admin getAdmin(String mail) {
		return newsDataEJB.getAdmin(mail);
	}
	
	@Override
	public Checker getChecker(String mail) {
		return newsDataEJB.getChecker(mail);
	}
	
	@Override
	public Submitter getSubmitter(String mail) {
		return newsDataEJB.getSubmitter(mail);
	}
	
//	public Noticia getNoticia(Long id){
//		return newsDataEJB.getNoticia(id);
//	}
//	
//	public List<Publicacion> getAllPublicaciones(){
//		return newsDataEJB.getAllPublicaciones();
//	}
//	
//	public List<Publicacion> findAllPublicacionesByNoticia(Long idNoticia){
//		return newsDataEJB.findPublicacionesByNoticia(idNoticia);	
//	}
//	
//	public String addPublicacion(Publicacion publicacion, Long idNoticia) {
//		List<Publicacion> publicaciones = newsDataEJB.findPublicacionesByNoticia(idNoticia);
//		if (publicaciones == null) {
//			newsDataEJB.addPublicacion(publicacion, idNoticia);
//			return "La publicación fue correctamente agregada.";
//		}else {
//			if (publicaciones.size() < 10) {
//				newsDataEJB.addPublicacion(publicacion, idNoticia);
//				return "La publicación fue correctamente agregada.";
//				}else {
//					return "Esta noticia ya tiene 10 publicaciones. No es posible agregar más.";
//			}
//		}
//	}	
//	

}

