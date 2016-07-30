package br.edu.ladoss.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mapaRefeicoesRealizadas")
public class MapaRefeicaoRealizada {
	
	private List<RefeicaoRealizada> refeicoesRealizadas;
	
	private int quantidade;
	
	private Refeicao refeicao;
	
	private Dia dia;

	private Date data;

	@XmlElement
	public List<RefeicaoRealizada> getRefeicoesRealizadas() {
		return refeicoesRealizadas;
	}

	public void setRefeicoesRealizadas(List<RefeicaoRealizada> refeicoesRealizadas) {
		this.refeicoesRealizadas = refeicoesRealizadas;
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
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
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
		return "MapaRefeicaoRealizada [refeicao=" + refeicao 
				+ ", quantidade=" + quantidade
				+ ", dia=" + dia 
				+ ", data=" + data + "]";
	}	
}
