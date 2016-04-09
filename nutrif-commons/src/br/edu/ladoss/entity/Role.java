package br.edu.ladoss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "role")
@Entity
@Table(name = "tb_role")
@NamedQuery(name = "Role.getAll", query = "from Role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_role")
	private Integer id; 

	@Column(name = "nm_role")
	private String nome;
	
	// Regras de acesso para os usuários.
	public static int ADMIN_ROLE = 1;
	public static int INSPETOR_ROLE = 2;
	public static int COMENSAL_ROLE = 3;

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
		return "Role [id=" + id +", nome=" + nome + "]";
	}
	
	@Override
	public boolean equals(Object value) {
		
		if (value instanceof String) {
			return this.nome.equals(value);
		}
		
		if (value instanceof Role) {
			
			Role role = (Role) value;
			
			if (this.id == role.getId() 
					&& this.nome.equals(role.getNome())) {
				return true;
			}
		}
		
		return false;
	}
}
