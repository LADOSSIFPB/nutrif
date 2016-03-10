package br.edu.ifpb.nutrif.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import br.edu.ifpb.nutrif.controller.AlunoController;
import br.edu.ifpb.nutrif.controller.CronogramaRefeicaoController;
import br.edu.ifpb.nutrif.controller.CursoController;
import br.edu.ifpb.nutrif.controller.RefeicaoController;
import br.edu.ifpb.nutrif.controller.RefeicaoRealizadaController;
import br.edu.ladoss.entity.CronogramaRefeicao;

public class NutrIFApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public NutrIFApplication() {
		
		CorsFilter filter = new CorsFilter();
		filter.getAllowedOrigins().add("*");
		filter.setAllowedMethods("POST, GET, DELETE, PUT, OPTIONS");
		filter.setAllowedHeaders("Content-Type");
		
		this.singletons.add(filter);		
		// ADD YOUR RESTFUL RESOURCES HERE
		this.singletons.add(new AlunoController());
		this.singletons.add(new CronogramaRefeicaoController());
		this.singletons.add(new CursoController());
		this.singletons.add(new RefeicaoController());
		this.singletons.add(new RefeicaoRealizadaController());
		this.singletons.add(new RestServices());
	}

	public Set<Class<?>> getClasses() {
		return this.empty;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}
}