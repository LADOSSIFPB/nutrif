package br.edu.ladoss.nutrif.model;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;

public class Matricula implements Serializable {

	private Integer id;

	private String numero;

	private Aluno aluno;

	private Curso curso;

	private boolean ativo;
	
	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@XmlElement
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@XmlElement
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "Matricula [id=" + id 
					+ ", numero=" + numero
					+ ", aluno=" + aluno
					+ ", curso=" + curso + "]";
	}
}
