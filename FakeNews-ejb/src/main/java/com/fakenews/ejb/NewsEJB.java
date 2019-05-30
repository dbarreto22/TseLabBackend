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
import com.fakenews.model.MecanismoPeriferico;
import com.fakenews.model.MecanismoVerificacion;
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
		if (newsDataEJB.getCitizen(mail) != null) {
			return EnumRoles.CITIZEN;
		}else {
			if (newsDataEJB.getChecker(mail) != null) {
				return EnumRoles.CHECKER;
			}else {
				if (newsDataEJB.getSubmitter(mail) != null) {
					return EnumRoles.SUBMITTER;
				}else {
					if (newsDataEJB.getAdmin(mail) != null) {
						return EnumRoles.ADMIN;
					}else {
						return EnumRoles.ERROR;
					}
				}
			}
		}
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
		
	@Override
	public DTRespuesta verificarHecho(Hecho hecho) {
		return newsDataEJB.updateHecho(hecho);
	}
	
	@Override
	public DTRespuesta suscription(String mail) {
		return newsDataEJB.suscription(mail);
	}
	
	@Override
	public DTRespuesta asignarHecho(Long idHecho, String mail) {
		return newsDataEJB.asignarHecho(idHecho, mail);
	}
	
	@Override
	public MecanismoPeriferico getMecanismoPeriferico(String username) {
		return newsDataEJB.getMecanismoPeriferico(username);
	}
	
	@Override
	public List<Checker> getCheckers(){
		return newsDataEJB.getCheckers();
	}
	
	@Override
	public List<MecanismoVerificacion> getMecanismosVerificacion(){
		return newsDataEJB.getMecanismosVerificacion();
	}
	
	@Override
	public List<Hecho> getHechosByChecker(String mail){
		return newsDataEJB.getHechosByChecker(mail);
	}
}

