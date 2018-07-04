package br.edu.ladoss.entity;

import javax.persistence.Column;
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

import br.edu.ladoss.data.DataEntity;

@XmlRootElement(name = "curso")
@Entity
@Table(name = "tb_curso")
@NamedQuery(name = "Curso.getAll", query = "from Curso")
public class Curso implements DataEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_curso")
	private Integer id;

	@Column(name = "nm_curso")
	private String nome;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_campus")
	private Campus campus;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_nivel")
	private Nivel nivel;

	public Curso() {
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
	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	
	@Override
	public String toString() {
		return "Curso [id=" + id + ", nome=" + nome + ", nivel=" + nivel + "]";
	}
}
