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

	public enum Tipo {
		
		ADMIN(1), INSPETOR(2), COMENSAL(3), NUTRICIONISTA(4);
		
		private int id;
		
		private Tipo(int id){
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_role")
	private Integer id; 

	@Column(name = "nm_role")
	private String nome;
	
	@Column(name = "nm_descricao")
	private String descricao;

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
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id 
				+ ", nome=" + nome 
				+ ", descricao=" + descricao + "]";
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
