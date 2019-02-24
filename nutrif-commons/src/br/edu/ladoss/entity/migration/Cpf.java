package br.edu.ladoss.entity.migration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cpf")
@Entity
@Table(name = "tb_cpf_migration")
@NamedQuery(name = "Cpf.getAll", query = "from Cpf")
public class Cpf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cpf")
	private Integer id;
	
	@Column(name = "nr_matricula")
	private String matricula;
	
	@Column(name = "nm_cpf")
	private String numero;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
