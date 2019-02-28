package br.edu.ladoss.entity.migration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "motivo")
@Entity
@Table(name = "tb_motivo")
@NamedQuery(name = "Motivo.getAll", query = "from Motivo")
public class Motivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_motivo")
	private Integer id;
	
	@Column(name = "nm_motivo")
	private String nome;
	
	@Column(name = "is_definitivo", nullable = false, insertable = true, updatable = true)
	private boolean defitivo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isDefitivo() {
		return defitivo;
	}

	public void setDefitivo(boolean defitivo) {
		this.defitivo = defitivo;
	}
	
	@Override
	public String toString() {
		return "Motivo [id=" + id 
				+ ", nome=" + nome
				+ ", definitivo=" + defitivo				
				+ "]";
	}
}
