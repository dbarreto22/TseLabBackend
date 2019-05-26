package com.fakenews.data;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Citizen;
import com.fakenews.model.Hecho;
import com.fakenews.model.Parametro;
import com.fakenews.model.Submitter;
import com.fakenews.model.Usuario;

@Stateless
public class NewsPersistentEJB
  implements NewsPersistentEJBLocal
{
  @PersistenceContext(unitName="fakenews")
  private EntityManager em;
  
  @Override
  public DTRespuesta saveHecho(Hecho hecho)
  {
    em.persist(hecho);
    return new DTRespuesta("OK", "El hecho se ha agregado correctamente.");
  }
  
  @Override
  public List<Hecho> getAllHechos()
  {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Hecho> cq = cb.createQuery(Hecho.class);
    cq.select(cq.from(Hecho.class));
    return em.createQuery(cq).getResultList();
  }
  
  @Override
  public String getParam(String name) {
	  Parametro param = em.find(Parametro.class, name);
	  return param.getValue();
  }
  
  @Override
  public Citizen getCitizen(String mail) {
	  Citizen citizen = null;
	  try {
		  citizen = (Citizen)em.find(Citizen.class, mail);
	  }catch(Exception e) {
		  System.out.print(e.getMessage());
	  }
	  return citizen;
  }
  
  @Override
  public EnumRoles saveCitizen(Citizen citizen) {
	  EnumRoles rol = EnumRoles.CITIZEN;
	  try {
		  em.persist(citizen);
	  }catch(Exception e) {
		  System.out.print(e.getMessage());
		  rol = EnumRoles.ERROR;
	  }
	  return rol;
  }
  
  @Override
  public Checker getChecker(String mail) {
	  Checker checker = null;
	  try {
		  checker = (Checker)em.find(Checker.class, mail);
	  }catch(Exception e) {
		  System.out.print(e.getMessage());
	  }
	  return checker;
  }
  
  @Override
  public Submitter getSubmitter(String mail) {
	  Submitter sub = null;
	  try {
		  sub = (Submitter)em.find(Submitter.class, mail);
	  }catch(Exception e) {
		  System.out.print(e.getMessage());
	  }
	  return sub;
  }
  
  @Override
  public Admin getAdmin(String mail) {
	  Admin admin = null;
	  try {
		  admin = (Admin)em.find(Admin.class, mail);
	  }catch(Exception e) {
		  System.out.print(e.getMessage());
	  }
	  return admin;
  }
  
  @Override
  public EnumRoles getRol(String mail) {
	  Query q = em.createNamedQuery(Usuario.GET_ROL).setParameter("email", mail);
	  String rol = (String) q.getSingleResult();
	  System.out.println(rol);
	  return EnumRoles.valueOf(rol);
  }
 
//  public void addPublicacion(Publicacion publicacion, Long idNoticia)
//  {
//	System.out.println("tipo: " + publicacion.getTipo());  
//	System.out.println("url: " + publicacion.getUrl()); 
//	Noticia noticia = (Noticia)em.find(Noticia.class, idNoticia);
//    noticia.addPublicacion(publicacion);
//    em.merge(noticia);
//  }
//  
//  public List<Publicacion> getAllPublicaciones()
//  {
//    CriteriaBuilder cb = em.getCriteriaBuilder();
//    CriteriaQuery<Publicacion> cq = cb.createQuery(Publicacion.class);
//    cq.select(cq.from(Publicacion.class));
//    return em.createQuery(cq).getResultList();
//  }
//  
//  public Noticia getNoticia(Long id)
//  {
//    return (Noticia)em.find(Noticia.class, id);
//  }
//  
//  public Publicacion getPublicacion(Long id)
//  {
//    return (Publicacion)em.find(Publicacion.class, id);
//  }
//  
//  public List<Publicacion> findPublicacionesByNoticia(Long idNoticia)
//  {
//    Noticia noticia = (Noticia)em.find(Noticia.class, idNoticia);
//    return noticia.getPublicaciones();
//  }
}