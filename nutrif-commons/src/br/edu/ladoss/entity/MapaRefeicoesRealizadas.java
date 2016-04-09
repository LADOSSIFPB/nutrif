package br.edu.ladoss.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mapaRefeicoesRealizadas")
public class MapaRefeicoesRealizadas {
	
	private List<RefeicaoRealizada> refeicoesRealizadas;
	
	private Refeicao refeicao;
	
	private Dia dia;

	private Date dataRefeicao;

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
	public Date getDataRefeicao() {
		return dataRefeicao;
	}

	public void setDataRefeicao(Date dataRefeicao) {
		this.dataRefeicao = dataRefeicao;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MapaRefeicoesRealizadas[refeicao=" + refeicao 
				+ ", dia=" + dia 
				+ ", dataRefeicao=" + dataRefeicao + "]";
	}	
}
