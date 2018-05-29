package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.EditalDAO;
import br.edu.ifpb.nutrif.dao.EventoDAO;
import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.dao.PeriodoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RoleDAO;
import br.edu.ifpb.nutrif.dao.SetorDAO;
import br.edu.ifpb.nutrif.dao.TurmaDAO;
import br.edu.ifpb.nutrif.dao.TurnoDAO;
import br.edu.ifpb.nutrif.dao.migration.MatriculaDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.Periodo;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.entity.Setor;
import br.edu.ladoss.entity.Turma;
import br.edu.ladoss.entity.Turno;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Edital;
import br.edu.ladoss.entity.Evento;
import br.edu.ladoss.entity.migration.Matricula;
import br.edu.ladoss.enumeration.TipoRole;

@Path("migracao")
public class MigracaoController {

	private static Logger logger = LogManager.getLogger(MigracaoController.class);

	private static int CAMPUS_CAMPINA_GRANDE = 3;
	
	private static int LIMIT_QUERY = 50;
	
	/**
	 * 
	 * @return
	 */
	@RolesAllowed({ TipoRole.ADMIN })
	@GET
	@Path("/iniciar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response iniciar() {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {
			
			// Campus
			migrarCampus();			
			
			// N�vel
			migrarNivel();
			
			// Turma
			migrarTurma();
			
			// Turno
			migrarTurno();
			
			// Periodo
			migrarPeriodo();
			
			// Setor
			migrarSetor();
			
			// Role
			migrarRole();
			
			// Dia
			migrarDia();
			
			// Refei��o
			migrarRefeicao();
			
			// Evento
			migrarEvento();
			
			// Edital
			migrarEdital();
			
			// Pessoa
			migrarPessoa();
			
			// DiaRefeicao
			// RefeicaoRealizada
			// Login		

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
		}

		return builder.build();
	}

	private void migrarDia() {
		
		logger.info("Migrar - Dia");
		
		List<Dia> dias = DiaDAO.getInstance().getAll();
		
		for (Dia dia: dias) {
			
			br.edu.ladoss.entity.migration.Dia diaMigracao = 
					new br.edu.ladoss.entity.migration.Dia();
			diaMigracao.setId(dia.getId());
			diaMigracao.setNome(dia.getNome());
			
			logger.info("Inserir: " + diaMigracao);
			br.edu.ifpb.nutrif.dao.migration.DiaDAO.getInstance().insert(diaMigracao);
		}
		
		logger.info("Fim - Dia");		
	}

	private void migrarEvento() {

		logger.info("Migrar - Evento");
		
		List<Evento> eventos = EventoDAO.getInstance().getAll();
		
		for (Evento evento: eventos) {
			
			br.edu.ladoss.entity.migration.Evento eventoMigracao = 
					new br.edu.ladoss.entity.migration.Evento();
			
			eventoMigracao.setId(evento.getId());
			eventoMigracao.setNome(evento.getNome());
			eventoMigracao.setDescricao(evento.getDescricao());
			
			logger.info("Inserir: " + eventoMigracao);
			br.edu.ifpb.nutrif.dao.migration.EventoDAO.getInstance().insert(eventoMigracao);
		}
		
		logger.info("Fim - Evento");
		
	}

	private void migrarEdital() {
		
		logger.info("Migrar - Edital");
		
		List<Edital> editais = EditalDAO.getInstance().getAll();
		
		for (Edital edital: editais) {
			
			br.edu.ladoss.entity.migration.Edital editalMigracao = 
					new br.edu.ladoss.entity.migration.Edital();
			editalMigracao.setId(edital.getId());
			editalMigracao.setNome(edital.getNome());
			editalMigracao.setDataInicial(edital.getDataInicial());
			editalMigracao.setDataFinal(edital.getDataFinal());
			editalMigracao.setQuantidadeBeneficiadosReal(
					edital.getQuantidadeBeneficiadosReal());
			editalMigracao.setQuantidadeBeneficiadosPrevista(
					edital.getQuantidadeBeneficiadosPrevista());
			editalMigracao.setDataInsercao(edital.getDataInsercao());
			editalMigracao.setAtivo(edital.isAtivo());
			
			// Campus
			Campus campus = edital.getCampus();
			if (campus != null) {
				int idCampus = campus.getId();
				br.edu.ladoss.entity.migration.Campus campusMigracao = 
						br.edu.ifpb.nutrif.dao.migration.CampusDAO.getInstance()
							.getById(idCampus);			
				editalMigracao.setCampus(campusMigracao);
			}
			
			// Evento
			Evento evento = edital.getEvento();
			if (evento != null) {
				int idEvento = evento.getId();
				br.edu.ladoss.entity.migration.Evento eventoMigracao = 
						br.edu.ifpb.nutrif.dao.migration.EventoDAO.getInstance().getById(idEvento);
				editalMigracao.setEvento(eventoMigracao);
			}
			
			// Respons�vel
			Funcionario responsavel = edital.getResponsavel();
			if (responsavel != null) {
				int idResponsavel = responsavel.getId();
				br.edu.ladoss.entity.migration.Funcionario responsavelMigracao = 
						br.edu.ifpb.nutrif.dao.migration.FuncionarioDAO.getInstance().getById(idResponsavel);
				editalMigracao.setResponsavel(responsavelMigracao);
			}
			
			// Funcion�rio
			Funcionario funcionario = edital.getResponsavel();
			if (funcionario != null) {
				int idResponsavel = funcionario.getId();
				br.edu.ladoss.entity.migration.Funcionario funcionarioMigracao = 
						br.edu.ifpb.nutrif.dao.migration.FuncionarioDAO.getInstance().getById(idResponsavel);
				editalMigracao.setFuncionario(funcionarioMigracao);
			}
			
			logger.info("Inserir: " + editalMigracao);
			br.edu.ifpb.nutrif.dao.migration.EditalDAO.getInstance().insert(editalMigracao);
		}
		
		logger.info("Fim - Edital");
	}

	private void migrarPeriodo() {
		
		logger.info("Migrar - Periodo");
		
		List<Periodo> periodos = PeriodoDAO.getInstance().getAll();
		
		for (Periodo periodo: periodos) {
			
			br.edu.ladoss.entity.migration.Periodo periodoMigracao = 
					new br.edu.ladoss.entity.migration.Periodo();
			
			periodoMigracao.setId(periodo.getId());
			periodoMigracao.setNome(periodo.getNome());
			
			logger.info("Inserir: " + periodoMigracao);
			br.edu.ifpb.nutrif.dao.migration.PeriodoDAO.getInstance().insert(periodoMigracao);
		}
		
		logger.info("Fim - Periodo");		
	}

	private void migrarSetor() {

		logger.info("Migrar - Setor");
		
		List<Setor> setores = SetorDAO.getInstance().getAll();
		
		for (Setor setor: setores) {
			
			br.edu.ladoss.entity.migration.Setor setorMigracao = 
					new br.edu.ladoss.entity.migration.Setor();
			
			setorMigracao.setId(setor.getId());
			setorMigracao.setNome(setor.getNome());
			
			logger.info("Inserir: " + setorMigracao);
			br.edu.ifpb.nutrif.dao.migration.SetorDAO.getInstance().insert(setorMigracao);
		}
		
		logger.info("Fim - Setor");		
	}

	private void migrarPessoa() {
		
		logger.info("Migrar - Pessoa");
		
		// Pessoa-Role
		// Funcion�rio
		migrarFuncionario();		
		
		// Aluno
		migrarAluno();
		
		logger.info("Fim - Pessoa");
	}

	private void migrarAluno() {
		
		logger.info("Migrar - Aluno");
		
		Long quantidadeAluno = AlunoDAO.getInstance().getQuantidadeTotalAluno();
		Double quantidadePaginas = Math.floor(quantidadeAluno/LIMIT_QUERY) + 1;
		logger.info("Quantidade de p�gina - Aluno: " + quantidadePaginas.intValue());
		
		for (int paginaAtual = 1; paginaAtual <= quantidadePaginas; paginaAtual++) {
			
			List<Aluno> alunos = AlunoDAO.getInstance().getAll(paginaAtual, LIMIT_QUERY);
			logger.info("Quantidade por p�gina - Aluno: " + alunos.size());
			
			for (Aluno aluno: alunos) {
				Date agora = new Date();
				
				br.edu.ladoss.entity.migration.Aluno alunoMigracao = 
						new br.edu.ladoss.entity.migration.Aluno();
				alunoMigracao.setId(aluno.getId());
				alunoMigracao.setNome(aluno.getNome());
				alunoMigracao.setEmail(aluno.getEmail());
				alunoMigracao.setSenha(aluno.getSenha());
				alunoMigracao.setKeyAuth(aluno.getKeyAuth());
				alunoMigracao.setKeyConfirmation(aluno.getKeyConfirmation());
				alunoMigracao.setAtivo(aluno.isAtivo());
				alunoMigracao.setDataInsercao(agora);
				alunoMigracao.setAcesso(aluno.isAcesso());
				
				// Campus
				Campus campus = aluno.getCampus();
				if (campus != null) {
					int idCampus = campus.getId();
					br.edu.ladoss.entity.migration.Campus campusMigracao = 
							br.edu.ifpb.nutrif.dao.migration.CampusDAO.getInstance()
								.getById(idCampus);			
					alunoMigracao.setCampus(campusMigracao);
				}
							
				br.edu.ifpb.nutrif.dao.migration.AlunoDAO.getInstance().insert(alunoMigracao);
				
				// Matricula				
				Matricula matricula = migrarMatricula(aluno, alunoMigracao);
				
				// Dia de Refei��o
				migrarDiaRefeicao(matricula);
			}
		}
		
		logger.info("Fim - Aluno");		
	}

	private void migrarDiaRefeicao(Matricula matricula) {
		
		logger.info("Migrar - DiaRefeicao");
		
		List<DiaRefeicao> diasRefeicoes = DiaRefeicaoDAO.getInstance()
				.getAllByAlunoMatricula(matricula.getNumero());
		
		for (DiaRefeicao diaRefeicao: diasRefeicoes) {
			
			br.edu.ladoss.entity.migration.DiaRefeicao diaRefeicaoMigracao =
					new br.edu.ladoss.entity.migration.DiaRefeicao();
			diaRefeicaoMigracao.setId(diaRefeicao.getId());
			diaRefeicaoMigracao.setDataInsercao(diaRefeicao.getDataInsercao());
			diaRefeicaoMigracao.setAtivo(diaRefeicao.isAtivo());
			diaRefeicaoMigracao.setMigracao(true);
			diaRefeicaoMigracao.setPretensao(diaRefeicao.isPretensao());			
			
			// Matricula - Par�metro
			diaRefeicaoMigracao.setMatricula(matricula);
			
			// Dia
			Dia dia = diaRefeicao.getDia();
			if (dia != null) {
				int idDia = dia.getId();
				br.edu.ladoss.entity.migration.Dia diaMigracao = 
						br.edu.ifpb.nutrif.dao.migration.DiaDAO.getInstance().getById(idDia);
				diaRefeicaoMigracao.setDia(diaMigracao);
			}
			
			// Edital			
			Edital edital = diaRefeicao.getEdital();
			if (edital != null) {
				int idEdital = edital.getId();
				br.edu.ladoss.entity.migration.Edital editalMigracao = 
						br.edu.ifpb.nutrif.dao.migration.EditalDAO.getInstance().getById(idEdital);
				diaRefeicaoMigracao.setEdital(editalMigracao);
			}
			
			// Refei��o
			Refeicao refeicao = diaRefeicao.getRefeicao();
			if (refeicao != null) {
				int idRefeicao = refeicao.getId();
				br.edu.ladoss.entity.migration.Refeicao refeicaoMigracao = 
						br.edu.ifpb.nutrif.dao.migration.RefeicaoDAO.getInstance().getById(idRefeicao);
				diaRefeicaoMigracao.setRefeicao(refeicaoMigracao);
			}
			
			// Funcion�rio
			Funcionario funcionario = edital.getResponsavel();
			if (funcionario != null) {
				int idResponsavel = funcionario.getId();
				br.edu.ladoss.entity.migration.Funcionario funcionarioMigracao = 
						br.edu.ifpb.nutrif.dao.migration.FuncionarioDAO.getInstance().getById(idResponsavel);
				diaRefeicaoMigracao.setFuncionario(funcionarioMigracao);
			}
			
			br.edu.ifpb.nutrif.dao.migration.DiaRefeicaoDAO.getInstance().insert(diaRefeicaoMigracao);		
		}
		
		logger.info("Fim - DiaRefeicao");
	}

	private Matricula migrarMatricula(Aluno aluno, 
			br.edu.ladoss.entity.migration.Aluno alunoMigracao) {
		
		logger.info("Migrar - Matricula");
		
		Matricula matricula = new Matricula();
		matricula.setNumero(aluno.getMatricula());
		matricula.setAluno(alunoMigracao);
					
		// Curso
		Curso curso = aluno.getCurso();
		if (curso != null) {
			int idCurso = curso.getId();
			br.edu.ladoss.entity.migration.Curso cursoMigracao = 
					br.edu.ifpb.nutrif.dao.migration.CursoDAO.getInstance()
						.getById(idCurso);			
			matricula.setCurso(cursoMigracao);
		}
		
		// Turma
		Turma turma = aluno.getTurma();
		if (turma != null) {
			int idTurma = turma.getId();
			br.edu.ladoss.entity.migration.Turma turmaMigracao = 
					br.edu.ifpb.nutrif.dao.migration.TurmaDAO.getInstance().getById(idTurma);
			matricula.setTurma(turmaMigracao);
		}
		
		//Turno
		Turno turno = aluno.getTurno();
		if (turno != null) {
			int idTurno = turno.getId();
			br.edu.ladoss.entity.migration.Turno turnoMigracao =
					br.edu.ifpb.nutrif.dao.migration.TurnoDAO.getInstance().getById(idTurno);
			matricula.setTurno(turnoMigracao);
		}
		
		// Periodo
		Periodo periodo = aluno.getPeriodo();
		if (periodo != null) {
			int idPeriodo = periodo.getId();
			br.edu.ladoss.entity.migration.Periodo periodoMigracao = 
					br.edu.ifpb.nutrif.dao.migration.PeriodoDAO.getInstance().getById(idPeriodo);
			matricula.setPeriodo(periodoMigracao);						
		}
		
		MatriculaDAO.getInstance().insert(matricula);
		
		logger.info(matricula);
		logger.info("Fim - Matricula");
		
		return matricula;
	}

	private void migrarFuncionario() {
		
		logger.info("Migrar - Funcion�rio");
		List<Funcionario> funcionarios = FuncionarioDAO.getInstance().getAll();
		
		for (Funcionario funcionario: funcionarios) {
			
			Date agora = new Date();
			
			br.edu.ladoss.entity.migration.Funcionario funcionarioMigracao = 
					new br.edu.ladoss.entity.migration.Funcionario();
			funcionarioMigracao.setId(funcionario.getId());
			funcionarioMigracao.setNome(funcionario.getNome());
			funcionarioMigracao.setEmail(funcionario.getEmail());
			funcionarioMigracao.setSenha(funcionario.getSenha());
			funcionarioMigracao.setKeyAuth(funcionario.getKeyAuth());
			funcionarioMigracao.setTipo(Funcionario.TIPO_FUNCIONARIO);
			funcionarioMigracao.setDataInsercao(funcionario.getDataInsercao());
			funcionarioMigracao.setDataModificacao(agora);
			funcionarioMigracao.setAtivo(funcionario.isAtivo());
			
			Campus campus = funcionario.getCampus();
			if (campus != null) {
				int idCampus = campus.getId();
				br.edu.ladoss.entity.migration.Campus campusMigracao = 
						br.edu.ifpb.nutrif.dao.migration.CampusDAO.getInstance()
							.getById(idCampus);			
				funcionarioMigracao.setCampus(campusMigracao);
			}
			
			Setor setor = funcionario.getSetor();
			if (setor != null) {
				int idSetor = setor.getId();
				br.edu.ladoss.entity.migration.Setor setorMigracao = 
						br.edu.ifpb.nutrif.dao.migration.SetorDAO.getInstance().getById(idSetor);
				funcionarioMigracao.setSetor(setorMigracao);
			}
			
			List<Role> roles = funcionario.getRoles();
			List<br.edu.ladoss.entity.migration.Role> rolesMigracao = 
					new ArrayList<br.edu.ladoss.entity.migration.Role>();
			for (Role role: roles) {
				br.edu.ladoss.entity.migration.Role roleMigracao = 
						br.edu.ifpb.nutrif.dao.migration.RoleDAO.getInstance().getById(role.getId());
				rolesMigracao.add(roleMigracao);
			}
			funcionarioMigracao.setRoles(rolesMigracao);
			
			logger.info("Inserir: " + funcionarioMigracao);
			br.edu.ifpb.nutrif.dao.migration.FuncionarioDAO.getInstance()
				.insert(funcionarioMigracao);	
		}
		
		logger.info("Fim - Funcion�rio");
	}

	private void migrarRefeicao() {
		
		logger.info("Migrar - Refei��o");
		
		List<Refeicao> refeicoes = RefeicaoDAO.getInstance().getAll();
		
		for (Refeicao refeicao: refeicoes) {
			
			br.edu.ladoss.entity.migration.Refeicao refeicaoMigracao = 
					new br.edu.ladoss.entity.migration.Refeicao();
			refeicaoMigracao.setId(refeicao.getId());
			refeicaoMigracao.setTipo(refeicao.getTipo());
			refeicaoMigracao.setHoraInicio(refeicao.getHoraInicio());
			refeicaoMigracao.setHoraFinal(refeicao.getHoraFinal());
			refeicaoMigracao.setHoraPrevisaoPretensao(refeicao.getHoraPrevisaoPretensao());
			refeicaoMigracao.setCusto(refeicao.getCusto());
			refeicaoMigracao.setAtivo(refeicao.isAtivo());
			
			br.edu.ladoss.entity.migration.Campus campus = 
					br.edu.ifpb.nutrif.dao.migration.CampusDAO.getInstance()
						.getById(CAMPUS_CAMPINA_GRANDE);			
			refeicaoMigracao.setCampus(campus);
			
			logger.info("Inserir: " + refeicaoMigracao);
			br.edu.ifpb.nutrif.dao.migration.RefeicaoDAO.getInstance()
				.insert(refeicaoMigracao);		
		}
		
		logger.info("Fim - Refei��o");		
	}

	private void migrarRole() {
		
		logger.info("Migrar - Role");
		
		List<Role> roles = RoleDAO.getInstance().getAll();
		
		for (Role role: roles) {
			
			br.edu.ladoss.entity.migration.Role roleMigracao = 
					new br.edu.ladoss.entity.migration.Role();
			roleMigracao.setId(role.getId());
			roleMigracao.setNome(role.getNome());
			roleMigracao.setDescricao(role.getDescricao());
			
			logger.info("Inserir: " + roleMigracao);
			br.edu.ifpb.nutrif.dao.migration.RoleDAO.getInstance().insert(roleMigracao);
		}
		
		logger.info("Fim - Role");		
	}

	private void migrarTurno() {
		
		logger.info("Migrar - Turno");
		
		List<Turno> turnos = TurnoDAO.getInstance().getAll();
		
		for (Turno turno: turnos) {
			
			br.edu.ladoss.entity.migration.Turno turnoMigracao = 
					new br.edu.ladoss.entity.migration.Turno();
			
			turnoMigracao.setId(turno.getId());
			turnoMigracao.setNome(turno.getNome());
			
			logger.info("Inserir: " + turnoMigracao);
			br.edu.ifpb.nutrif.dao.migration.TurnoDAO.getInstance().insert(turnoMigracao);
		}
		
		logger.info("Fim - Turno");		
	}

	private void migrarTurma() {

		logger.info("Migrar - Turma");
		List<Turma> turmas = TurmaDAO.getInstance().getAll();
		
		for (Turma turma: turmas) {
			br.edu.ladoss.entity.migration.Turma turmaMigracao = 
					new br.edu.ladoss.entity.migration.Turma();
			
			turmaMigracao.setId(turma.getId());
			turmaMigracao.setNome(turma.getNome());
			
			logger.info("Inserir: " + turmaMigracao);
			
			br.edu.ifpb.nutrif.dao.migration.TurmaDAO.getInstance().insert(turmaMigracao);
		}
		
		logger.info("Fim - Turma");		
	}

	private void migrarNivel() {
		
		logger.info("Migrar - N�vel");
				
		logger.info("Fim - N�vel");		
	}

	private void migrarCampus() {
		
		logger.info("Migrar - Campus");
		List<Campus> campi = CampusDAO.getInstance().getAll();
		
		for (Campus campus: campi) {
			br.edu.ladoss.entity.migration.Campus campusMigracao = 
					new br.edu.ladoss.entity.migration.Campus();
			
			campusMigracao.setId(campus.getId());
			campusMigracao.setCidade(campus.getCidade());
			campusMigracao.setSigla(campus.getSigla());
			campusMigracao.setAtivo(campus.isAtivo());
			campusMigracao.setDataInsercao(new Date());
			
			logger.info("Inserir: " + campusMigracao);
			
			br.edu.ifpb.nutrif.dao.migration.CampusDAO.getInstance().insert(campusMigracao);
		}
		
		logger.info("Fim - Campus");
	}
}