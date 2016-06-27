package br.edu.ladoss.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mapaRefeicoesRealizadas")
public class MapaPretensaoRefeicao {
	
	private List<PretensaoRefeicao> pretensoesRefeicoes;
	
	private Refeicao refeicao;
	
	private Dia dia;

	private Date dataInicio;
	
	private Date dataFim;

	@XmlElement
	public List<PretensaoRefeicao> getPretensoesRefeicoes() {
		return pretensoesRefeicoes;
	}

	public void setPretensoesRefeicoes(List<PretensaoRefeicao> pretensoesRefeicoes) {
		this.pretensoesRefeicoes = pretensoesRefeicoes;
	}

	@XmlElement
	public Refeicao getRefeicao() {
		return refeicao;
	}

	public void setRefeicao(Refeicao refeicao) {
		this.refeicao = refeicao;
	}

	@XmlElement
	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}
	
	@XmlElement
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@XmlElement
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MapaPretensaoRefeicao[refeicao=" + refeicao 
				+ ", dia=" + dia 
				+ ", dataInicio=" + dataInicio
				+ ", dataFim=" + dataFim + "]";
	}	
}
