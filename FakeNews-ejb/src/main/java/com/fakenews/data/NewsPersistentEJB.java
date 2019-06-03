package com.fakenews.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.fakenews.datatypes.DTMecanismoVerificacion;
import com.fakenews.datatypes.DTRespuesta;
import com.fakenews.datatypes.EnumHechoEstado;
import com.fakenews.datatypes.EnumRoles;
import com.fakenews.datatypes.EnumTipoCalificacion;
import com.fakenews.model.Admin;
import com.fakenews.model.Checker;
import com.fakenews.model.Citizen;
import com.fakenews.model.Hecho;
import com.fakenews.model.MecanismoExterno;
import com.fakenews.model.MecanismoInterno;
import com.fakenews.model.MecanismoPeriferico;
import com.fakenews.model.MecanismoVerificacion;
import com.fakenews.model.Parametro;
import com.fakenews.model.ResultadoMecanismo;
import com.fakenews.model.Submitter;
import com.fakenews.model.Usuario;

@Stateless
public class NewsPersistentEJB implements NewsPersistentEJBLocal {
	@PersistenceContext(unitName = "fakenews")
	private EntityManager em;

	@Override
	public DTRespuesta saveHecho(Hecho hecho) {
		hecho.setEstado(EnumHechoEstado.NUEVO);
		em.persist(hecho);
		return new DTRespuesta("OK", "El hecho se ha agregado correctamente.");
	}

	@Override
	public List<Hecho> getAllHechos() {
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
			citizen = (Citizen) em.find(Citizen.class, mail);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return citizen;
	}

	@Override
	public EnumRoles saveCitizen(Citizen citizen) {
		EnumRoles rol = EnumRoles.CITIZEN;
		try {
			em.persist(citizen);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			rol = EnumRoles.ERROR;
		}
		return rol;
	}

	@Override
	public Checker getChecker(String mail) {
		Checker checker = null;
		try {
			System.out.println("mail: " + mail);
			checker = (Checker) em.find(Checker.class, mail);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return checker;
	}

	@Override
	public Submitter getSubmitter(String mail) {
		Submitter sub = null;
		try {
			sub = (Submitter) em.find(Submitter.class, mail);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return sub;
	}

	@Override
	public Admin getAdmin(String mail) {
		Admin admin = null;
		try {
			admin = (Admin) em.find(Admin.class, mail);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return admin;
	}

	@Override
	public DTRespuesta updateHecho(Hecho hecho) {
		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al verificar el hecho.");
		try {
			Date date = new Date(System.currentTimeMillis());
			Object object = em.find(Hecho.class, hecho.getId());
			if (object instanceof Hecho) {
				Hecho nuevoHecho = (Hecho) object;
				nuevoHecho.setFechaFinVerificacion(date);
				nuevoHecho.setEstado(EnumHechoEstado.VERIFICADO);
				nuevoHecho.setCalificacion(hecho.getCalificacion());
				nuevoHecho.setJustificacion(hecho.getJustificacion());
				em.merge(nuevoHecho);
				if (nuevoHecho != null && nuevoHecho.getCalificacion() != null) {
					respuesta.setResultado("OK");
					respuesta.setMensaje("Se ha verificado el hecho correctamente. " + "Calificado como "
							+ nuevoHecho.getCalificacion());
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return respuesta;
	}

	@Override
	public DTRespuesta asignarHecho(Long idHecho, String mail) {
		System.out.println("AsignarHecho");
		System.out.println("idHecho: " + idHecho.toString());
		System.out.println("mail: " + mail);

		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al asignar el Hecho.");
		Hecho hecho = null;
		Checker checker = null;
		Object object = em.find(Hecho.class, idHecho);
		if (object instanceof Hecho) {
			hecho = (Hecho) object;
		}

		object = em.find(Checker.class, mail);
		if (object instanceof Checker) {
			checker = (Checker) object;
		}

		if (hecho != null && checker != null) {
			hecho.setEstado(EnumHechoEstado.A_COMPROBAR);
			hecho.setChecker(checker);
			em.merge(hecho);
			respuesta.setResultado("OK");
			respuesta.setMensaje("Se ha asignado el hecho correctamente.");
		} else {
			respuesta.setMensaje("No existe hecho o checker asociado a los datos");
		}
		return respuesta;
	}

	@Override
	public DTRespuesta suscription(String mail) {
		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error.");
		System.out.println("suscription");
		System.out.println("mail: " + mail);
		
		Citizen citizen = this.getCitizen(mail);
		
		if (citizen != null) {
			citizen.setSuscripto(true);
			em.merge(citizen);
			respuesta.setResultado("OK");
			respuesta.setMensaje("El usuario se suscribió correctamente.");
		}else {
			respuesta.setMensaje("No se ha encontrado el usuario.");
		}
		return respuesta;
	}

	@Override
	public MecanismoPeriferico getMecanismoPeriferico(String username) {
		MecanismoPeriferico mecanismo = null;
		try {
			Query q = em.createNamedQuery(MecanismoPeriferico.getByUsuario).setParameter("username", username);
			Object object = q.getSingleResult();
			if(object instanceof MecanismoPeriferico) {
				mecanismo = (MecanismoPeriferico) object;
			}
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return mecanismo;
	}

	@Override
	public List<Checker> getCheckers(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Checker> cq = cb.createQuery(Checker.class);
		cq.select(cq.from(Checker.class));
		return em.createQuery(cq).getResultList();
	}
	
	@Override
	public List<Hecho> getHechosByChecker(String mail){
		System.out.println("getHechosByChecker");
	
		Checker checker = this.getChecker(mail);
	
		Query q = em.createNamedQuery(Hecho.getByChecker).setParameter("checker", checker);
		return q.getResultList();			
	}

	@Override
	public List<MecanismoInterno> getMecanismosInternos(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MecanismoInterno> cq = cb.createQuery(MecanismoInterno.class);
		cq.select(cq.from(MecanismoInterno.class));
		return em.createQuery(cq).getResultList();
	}
	
	@Override
	public List<MecanismoExterno> getMecanismosExternos(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MecanismoExterno> cq = cb.createQuery(MecanismoExterno.class);
		cq.select(cq.from(MecanismoExterno.class));
		return em.createQuery(cq).getResultList();
	}
	
	@Override
	public DTRespuesta saveAdmin(Admin admin) {
		em.persist(admin);
		return new DTRespuesta("OK", "El usuario se ha registrado correctamente.");
	}

	@Override
	public DTRespuesta saveChecker(Checker checker) {
		em.persist(checker);
		return new DTRespuesta("OK", "El usuario se ha registrado correctamente.");
	}

	@Override
	public DTRespuesta saveSubmitter(Submitter submitter) {
		em.persist(submitter);
		return new DTRespuesta("OK", "El usuario se ha registrado correctamente.");
	}
	
	@Override
	public List<MecanismoPeriferico> getMecanismosPerifericos(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MecanismoPeriferico> cq = cb.createQuery(MecanismoPeriferico.class);
		cq.select(cq.from(MecanismoPeriferico.class));
		return em.createQuery(cq).getResultList();
	}
	
	@Override
	public DTRespuesta saveMecanismoInterno(MecanismoInterno mecanismo) {
		em.persist(mecanismo);
		return new DTRespuesta("OK", "El mecanismo se ha registrado correctamente.");
	}
	
	@Override
	public DTRespuesta saveMecanismoExterno(MecanismoExterno mecanismo) {
		em.persist(mecanismo);
		return new DTRespuesta("OK", "El mecanismo se ha registrado correctamente.");
	}
	
	@Override
	public DTRespuesta saveMecanismoPeriferico(MecanismoPeriferico mecanismo) {
		em.persist(mecanismo);
		return new DTRespuesta("OK", "El mecanismo se ha registrado correctamente.");
	}
		
	@Override
	public DTRespuesta updateMecanismoPeriferico(MecanismoPeriferico mecanismo) {
		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al actualizar el periférico.");
		try {
			em.merge(mecanismo);
			respuesta.setResultado("OK");
			respuesta.setMensaje("El periférico se ha actualizado correctamente.");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return respuesta;
	}
	
	@Override
	public DTRespuesta updateMecanismoInterno(MecanismoInterno mecanismoInterno) {
		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al actualizar el mecanismo.");
		try {
			em.merge(mecanismoInterno);
			respuesta.setResultado("OK");
			respuesta.setMensaje("El mecanismo se ha actualizado correctamente.");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return respuesta;
	}

	@Override
	public DTRespuesta updateMecanismoExterno(MecanismoExterno mecanismoExterno) {
		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al actualizar el mecanismo.");
		try {
			em.merge(mecanismoExterno);
			respuesta.setResultado("OK");
			respuesta.setMensaje("El mecanismo se ha actualizado correctamente.");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return respuesta;
	}
	
	@Override
	public DTRespuesta verificarHechoMecanismo(Long idHecho, Long idMecanismoVerificacion) {
		System.out.println("VerificarHechoMecanismo");
		System.out.println("idHecho: " + idHecho.toString());
		System.out.println("idMecanismoVerificacion: " + idMecanismoVerificacion.toString());

		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al asignar el Hecho.");
		Hecho hecho = null;
		MecanismoVerificacion mecanismo = null;
		Object object = em.find(Hecho.class, idHecho);
		if (object instanceof Hecho) {
			hecho = (Hecho) object;
		}
		object = em.find(MecanismoVerificacion.class, idMecanismoVerificacion);
		if (object instanceof MecanismoVerificacion) {
			mecanismo = (MecanismoVerificacion) object;
		}
		
		if (hecho != null && mecanismo != null) {
			Date date = new Date(System.currentTimeMillis());
			hecho.setFechaInicioVerificacion(date);
			hecho.setEstado(EnumHechoEstado.EN_PROCESO);
			ResultadoMecanismo resultado = new ResultadoMecanismo(mecanismo);
			em.persist(resultado);
			hecho.getResultadosMecanismos().add(resultado);
			em.merge(hecho);
			respuesta.setResultado("OK");
			respuesta.setMensaje("Se ha agregado el mecanismo correctamente.");
		} else {
			respuesta.setMensaje("No existe el hecho o mecanismo");
		}
		return respuesta;
	}
	
	@Override
	public DTRespuesta resultadoverificarHechoMecanismo(Long idHecho, Long idMecanismoVerificacion,
			EnumTipoCalificacion calificacion) 
	{
		System.out.println("ResultadoVerificarHechoMecanismo");
		System.out.println("idHecho: " + idHecho.toString());
		System.out.println("idMecanismoVerificacion: " + idMecanismoVerificacion.toString());

		DTRespuesta respuesta = new DTRespuesta("ERROR", "Ha ocurrido un error al asignar el resultado al Hecho.");
		Hecho hecho = null;
		MecanismoVerificacion mecanismo = null;
		Object object = em.find(Hecho.class, idHecho);
		if (object instanceof Hecho) {
			hecho = (Hecho) object;
		}
		object = em.find(MecanismoVerificacion.class, idMecanismoVerificacion);
		if (object instanceof MecanismoVerificacion) {
			mecanismo = (MecanismoVerificacion) object;
		}
		
		if (hecho != null && mecanismo != null) {
			ResultadoMecanismo resultado = new ResultadoMecanismo(mecanismo);
			resultado.setCalificacion(calificacion);
			em.persist(resultado);
			hecho.getResultadosMecanismos().add(resultado);
			em.merge(hecho);
			respuesta.setResultado("OK");
			respuesta.setMensaje("Se ha calificado el hecho correctamente.");
		} else {
			respuesta.setMensaje("No existe el hecho o mecanismo");
		}
		return respuesta;
	}

}