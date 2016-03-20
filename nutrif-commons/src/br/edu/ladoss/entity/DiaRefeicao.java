package br.edu.ladoss.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.edu.ladoss.data.DataEntity;

@XmlRootElement(name = "diaRefeicao")
@Entity
@Table(name = "tb_dia_refeicao")
@NamedQuery(name = "DiaRefeicao.getAll", query = "from DiaRefeicao")
public class DiaRefeicao implements DataEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dia_refeicao")
	private Integer id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_id_aluno")
	private Aluno aluno;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_dia")
	private Dia dia;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_refeicao")
	private Refeicao refeicao;

	@Column(name = "is_ativo", columnDefinition = "boolean default true", nullable = false)
	private boolean ativo;
		
	public DiaRefeicao() {
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
	
	@XmlElement
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "DiaRefeicao [id=" + id + ", aluno=" + aluno 
				+ ", dia=" + dia + ", refeicao=" + refeicao + "]";
	}
}
