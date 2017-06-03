package br.edu.ladoss.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mapaRefeicao")
public class MapaRefeicao <T>{
	
	private List<T> lista;
	
	private int quantidade;

	private Refeicao refeicao;
	
	private Dia dia;
	
	private Edital edital;
	
	private Aluno aluno;
	
	private Date data;	

	@XmlElement
	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

	@XmlElement
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
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
	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}

	@XmlElement
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	@Override
	public String toString() {
		return "MapaRefeicao [quantidade=" + quantidade
				+ ", refeicao=" + refeicao 
				+ ", dia=" + dia
				+ ", edital=" + edital 
				+ ", aluno=" + aluno 
				+ ", data=" + data 
				+ ", lista=" + lista + "]";
	}
}
