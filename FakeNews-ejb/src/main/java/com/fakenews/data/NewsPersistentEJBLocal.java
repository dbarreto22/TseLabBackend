package com.fakenews.data;

import java.util.List;
import javax.ejb.Local;

import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Citizen;
import com.fakenews.model.Hecho;
import com.fakenews.model.MecanismoPeriferico;
import com.fakenews.model.MecanismoVerificacion;
import com.fakenews.model.Submitter;

@Local
public abstract interface NewsPersistentEJBLocal
{
  
	public DTRespuesta saveHecho(Hecho hecho);
  
	public List<Hecho> getAllHechos();
  
	public String getParam(String name);
  
	public Citizen getCitizen(String mail);
  
	public Checker getChecker(String mail);
  
	public Submitter getSubmitter(String mail);
  
	public Admin getAdmin(String mail);
  
	public EnumRoles saveCitizen(Citizen citizen);
 
	public DTRespuesta updateHecho(Hecho hecho);
  
	public DTRespuesta asignarHecho(Long idHecho, String mail);

	public DTRespuesta suscription(String mail);

	public MecanismoPeriferico getMecanismoPeriferico(String username);

	public List<Checker> getCheckers();

	public List<Hecho> getHechosByChecker(String mail);
	
	public List<MecanismoVerificacion> getMecanismosVerificacion();

	public DTRespuesta saveAdmin(Admin admin);

	public DTRespuesta saveChecker(Checker checker);

	public DTRespuesta saveSubmitter(Submitter submitter);

	public List<MecanismoPeriferico> getMecanismosPerifericos();

}