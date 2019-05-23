package com.fakenews.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//
//import com.fakenews.ejb.NewsEJBLocal;
//import com.mynews.model.Noticia;
//import com.mynews.model.Publicacion;
import javax.ws.rs.core.MediaType;

@Path("/")
public class NewsRestService {
	
//	@EJB
//	private NewsEJBLocal newsEJB;
	

	@GET
	@Path("prueba")
	public String prueba(){
		return "Hello ppl";
	}
	
//	@GET
//	@Path("getNoticia/{idNoticia}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Noticia getNoticia(@PathParam("idNoticia") Long id) {
//		return newsEJB.getNoticia(id);
//	}
//	
//	@GET
//	@Path("getAllPublicaciones")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Publicacion> getAllPublicaciones(){
//		return newsEJB.getAllPublicaciones();
//	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("findAllPublicacionesByNoticia/{idNoticia}")
//	public List<Publicacion> findAllPublicacionesByNoticia(@PathParam("idNoticia") Long idNoticia){
//		return newsEJB.findAllPublicacionesByNoticia(idNoticia);
//	}
//	
//	@POST
//	@Path("addPublicacion")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String addPublicacion(@HeaderParam("idNoticia") Long idNoticia,Publicacion publicacion) {
//		return newsEJB.addPublicacion(publicacion, idNoticia);
//	}
//
//	@POST
//	@Path("addNoticia")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String addNoticia(Noticia noticia) {
//		return newsEJB.addNoticia(noticia.getTitulo(),noticia.getDescripcion());
//	}
	
}
