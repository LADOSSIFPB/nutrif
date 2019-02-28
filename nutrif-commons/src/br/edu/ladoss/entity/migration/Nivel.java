package br.edu.ladoss.entity.migration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "nivel")
@Entity
@Table(name = "tb_nivel")
@NamedQuery(name = "Nivel.getAll", query = "from Nivel")
public class Nivel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_nivel")
	private Integer id;
	
	@Column(name = "nm_nivel")
	private String nome;

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
	
	@Override
	public String toString() {
		return "Nivel [id=" + id 
				+ ", nome=" + nome + "]";
	}
}
