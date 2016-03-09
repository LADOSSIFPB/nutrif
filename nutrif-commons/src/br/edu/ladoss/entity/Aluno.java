package br.edu.ladoss.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "aluno")
@Entity
@Table(name = "tb_aluno")
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "nome")
	String nome;

	@Column(name = "fk_id_curso")
	Curso curso;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_aluno_cronograma_refeicao", joinColumns = @JoinColumn(name = "fk_id_aluno"), inverseJoinColumns = @JoinColumn(name = "fk_id_cronograma"))
	List<CronogramaRefeicao> refeicoes;

	public Aluno() {
		super();
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlElement
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@XmlElement
	public List<CronogramaRefeicao> getRefeicoes() {
		return refeicoes;
	}

	public void setRefeicoes(List<CronogramaRefeicao> refeicoes) {
		this.refeicoes = refeicoes;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", curso=" + curso + ", refeicoes="
				+ refeicoes + "]";
	}

}
