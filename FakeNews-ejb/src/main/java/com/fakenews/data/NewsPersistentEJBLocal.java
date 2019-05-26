package com.fakenews.data;

import java.util.List;
import javax.ejb.Local;

import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumParam;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Citizen;
import com.fakenews.model.Hecho;
import com.fakenews.model.Submitter;

@Local
public abstract interface NewsPersistentEJBLocal
{
  public DTRespuesta saveHecho(Hecho hecho);
  
  public List<Hecho> getAllHechos();
  
  public String getParam(EnumParam name);
  
  public Citizen getCitizen(String mail);
  
  public Checker getChecker(String mail);
  
  public Submitter getSubmitter(String mail);
  
  public Admin getAdmin(String mail);
  
  public EnumRoles saveCitizen(Citizen citizen);
  
//  public abstract void addPublicacion(Publicacion paramPublicacion, Long idNoticia);
//  
//  
//  public abstract List<Publicacion> getAllPublicaciones();
//  
//  public abstract Noticia getNoticia(Long id);
//  
//  public abstract Publicacion getPublicacion(Long id);
//  
//  public abstract List<Publicacion> findPublicacionesByNoticia(Long idNoticia);
}