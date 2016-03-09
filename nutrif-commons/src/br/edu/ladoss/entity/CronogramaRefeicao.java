package br.edu.ladoss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cronogramaRefeicao")
@Entity
@Table(name = "tb_cronograma_refeicao")
public class CronogramaRefeicao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cronograma_refeicao")
	private Integer id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_aluno")
	private Aluno aluno;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_dia")
	private Dia dia;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_refeicao")
	private Refeicao refeicao;

	public CronogramaRefeicao() {
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
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@XmlElement
	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	@XmlElement
	public Refeicao getRefeicao() {
		return refeicao;
	}

	public void setRefeicao(Refeicao refeicao) {
		this.refeicao = refeicao;
	}

	@Override
	public String toString() {
		return "CronogramaRefeicao [id=" + id + ", aluno=" + aluno + ", dia=" + dia + ", refeicao="
				+ refeicao + "]";
	}

}
