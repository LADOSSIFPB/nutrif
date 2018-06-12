package br.edu.ifpb.nutrif.task;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.ExtratoRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoRealizadaDAO;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.ExtratoRefeicao;
import br.edu.ladoss.entity.Refeicao;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

/**
 * This task counts from 1 to 30.
 */
public class ExtratoRefeicaoTask extends Task {

	private static Logger logger = LogManager.getLogger(ExtratoRefeicaoTask.class);

	public boolean canBePaused() {
		return true;
	}

	public boolean canBeStopped() {
		return true;
	}

	public boolean supportsCompletenessTracking() {
		return true;
	}

	public boolean supportsStatusTracking() {
		return true;
	}

	public void execute(TaskExecutionContext executor) throws RuntimeException {

		// Execução da tarefa
		logger.info("Gerar - ExtratoRefeicao");

		// Hoje - Data atual
		Date hoje = new Date();

		logger.info("DataRefeicao - hoje: " + hoje);

		// Campus
		List<Campus> campi = CampusDAO.getInstance().getAll();

		for (Campus campus : campi) {

			Integer idCampus = campus.getId();

			// Refeição
			List<Refeicao> refeicoes = RefeicaoDAO.getInstance().listByIdCampus(idCampus);

			for (Refeicao refeicao : refeicoes) {

				// Quantidade
				Long quantidadeRefeicoes = RefeicaoRealizadaDAO.getInstance()
						.getQuantidadeDiaRefeicaoRealizada(refeicao, hoje);

				logger.info("Quantidade de refeições realizadas: " + quantidadeRefeicoes);

				Dia dia = getDia(hoje);
				Date agora = new Date();

				ExtratoRefeicao extratoRefeicao = new ExtratoRefeicao();
				extratoRefeicao.setCampus(campus);
				extratoRefeicao.setDataExtrato(hoje);
				extratoRefeicao.setDia(dia);
				extratoRefeicao.setRefeicao(refeicao);
				extratoRefeicao.setQuantidadeRefeicoes(quantidadeRefeicoes.intValue());
				extratoRefeicao.setDataExcucao(agora);

				logger.info("Inserir: " + extratoRefeicao);
				ExtratoRefeicaoDAO.getInstance().insert(extratoRefeicao);
			}
		}

		logger.info("Fim - ExtratoRefeicao");
	}	
	
	private Dia getDia(Date dataRefeicao) {
		
		Dia dia = DateUtil.getDayOfWeek(dataRefeicao);
		
		Dia diaMigracao = DiaDAO.getInstance().getById(dia.getId());
		
		return diaMigracao;
	}

}