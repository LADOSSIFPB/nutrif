package br.edu.ladoss.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mapaRefeicoesRealizadas")
public class MapaPretensaoRefeicao {
	
	private List<PretensaoRefeicao> pretensoesRefeicoes;
	
	private Integer quantidade;
	
	private Refeicao refeicao;
	
	private Dia dia;

	private Date data;

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
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}	

	@XmlElement
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "MapaPretensaoRefeicao[quantidade="+ quantidade 
				+ ", refeicao=" + refeicao 
				+ ", dia=" + dia
				+ ", data=" + data + "]";
	}	
}
