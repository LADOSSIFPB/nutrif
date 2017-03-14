package br.edu.ladoss.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "aluno")
@Entity
@Table(name = "tb_aluno")
@NamedQuery(name = "Aluno.getAll", query = "from Aluno")
@DiscriminatorValue(value = "2")
public class Aluno extends Pessoa {

	private static final long serialVersionUID = 4769549068297565213L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_aluno")
	private Integer id;

	@Column(name = "nm_matricula", length = 12, unique = true)
	private String matricula;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_curso")
	private Curso curso;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_turma")
	private Turma turma;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_turno")
	private Turno turno;
	
	@Column(name = "nm_keyconfirmation")
	private String keyConfirmation;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_foto_perfil")
	private Arquivo fotoPerfil;

	@Column(name = "is_acesso")
	private boolean acesso;
	
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
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@XmlElement
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	@XmlElement
	public String getKeyConfirmation() {
		return keyConfirmation;
	}

	public void setKeyConfirmation(String keyConfirmation) {
		this.keyConfirmation = keyConfirmation;
	}

	public Arquivo getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(Arquivo fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	@XmlElement
	public boolean isAcesso() {
		return acesso;
	}

	public void setAcesso(boolean acesso) {
		this.acesso = acesso;
	}
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}		
	
	@Override
	public String toString() {
		return "Aluno [" + super.toString() + ", curso=" + curso 
				+ ", matricula=" + matricula + "]";
	}

	
}
