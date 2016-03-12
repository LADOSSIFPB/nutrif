package br.edu.ladoss.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuario")
@Entity
@Table(name = "tb_usuario")
@NamedQuery(name = "Usuario.getAll", query = "from Usuario")
@DiscriminatorValue(value = "2")
public class Usuario extends Pessoa {

	private static final long serialVersionUID = -768857682921470842L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", unique = true)
	private Integer id;
	
	@Column(name = "nm_senha")
	private String senha;
	
	@Column(name = "nm_key")
	private String key;

	@XmlElement
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@XmlElement
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Usuario [nome=" + super.getNome() + ", key=" + key + "]";
	}
}
