package br.edu.ifpb.nutrif.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import br.edu.ifpb.nutrif.controller.AlunoController;
import br.edu.ifpb.nutrif.controller.ArquivoController;
import br.edu.ifpb.nutrif.controller.CampusController;
import br.edu.ifpb.nutrif.controller.CursoController;
import br.edu.ifpb.nutrif.controller.DiaController;
import br.edu.ifpb.nutrif.controller.DiaRefeicaoController;
import br.edu.ifpb.nutrif.controller.EditalController;
import br.edu.ifpb.nutrif.controller.EventoController;
import br.edu.ifpb.nutrif.controller.FuncionarioController;
import br.edu.ifpb.nutrif.controller.PessoaController;
import br.edu.ifpb.nutrif.controller.PretensaoRefeicaoController;
import br.edu.ifpb.nutrif.controller.RefeicaoController;
import br.edu.ifpb.nutrif.controller.RefeicaoRealizadaController;
import br.edu.ifpb.nutrif.controller.RoleController;
import br.edu.ifpb.nutrif.controller.SetorController;
import br.edu.ifpb.nutrif.controller.TurmaController;
import br.edu.ifpb.nutrif.controller.TurnoController;

public class NutrIFApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public NutrIFApplication() {
		
		// Multiple client-request: Cross-Filter
		CorsFilter filter = new CorsFilter();
		filter.getAllowedOrigins().add("*");
		filter.setAllowedMethods("POST, GET, DELETE, PUT, OPTIONS");
		filter.setAllowedHeaders("Content-Type, Authorization");
		
		this.singletons.add(filter);
		
		// Controllers disponíveis no serviço.
		this.singletons.add(new AlunoController());
		this.singletons.add(new FuncionarioController());
		this.singletons.add(new PessoaController());
		this.singletons.add(new SetorController());
		this.singletons.add(new CampusController());
		this.singletons.add(new EditalController());
		this.singletons.add(new EventoController());
		this.singletons.add(new DiaRefeicaoController());
		this.singletons.add(new CursoController());
		this.singletons.add(new RefeicaoController());
		this.singletons.add(new DiaController());
		this.singletons.add(new RoleController());
		this.singletons.add(new RefeicaoRealizadaController());
		this.singletons.add(new PretensaoRefeicaoController());
		this.singletons.add(new ArquivoController());
		this.singletons.add(new TurnoController());
		this.singletons.add(new TurmaController());
		
		// Information service.
		this.singletons.add(new NutrIFRestIndex());
		this.singletons.add(new NutrIFRestServices());		
	}

	public Set<Class<?>> getClasses() {
		return this.empty;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}
}