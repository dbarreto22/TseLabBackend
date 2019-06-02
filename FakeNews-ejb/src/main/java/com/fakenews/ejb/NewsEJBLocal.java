package com.fakenews.ejb;

import java.util.List;

import javax.ejb.Local;

import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.DTUsuarioBcknd;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Hecho;
import com.fakenews.model.MecanismoPeriferico;
import com.fakenews.model.MecanismoVerificacion;
import com.fakenews.model.Submitter;

@Local
public abstract interface NewsEJBLocal {

	public List<Hecho> getAllHechos();
	public DTRespuesta saveHecho(Hecho hecho);
	public String getParam(String name);
	public EnumRoles citizenLogin(String email, String nombre);
	public EnumRoles getRol(String email);
	public Admin getAdmin(String mail);
	public Checker getChecker(String mail);
	public Submitter getSubmitter(String mail);
	public DTRespuesta verificarHecho(Hecho hecho);
	public DTRespuesta asignarHecho(Long idHecho, String mail);
	public DTRespuesta suscription(String mail);
	public MecanismoPeriferico getMecanismoPeriferico(String username);
	public List<Checker> getCheckers();
	public List<MecanismoVerificacion> getMecanismosVerificacion();
	public List<Hecho> getHechosByChecker(String mail);
	public DTRespuesta registrarUsuarioBackend(DTUsuarioBcknd usuario);
	public List<MecanismoPeriferico> getMecanismosPerifericos();
}
