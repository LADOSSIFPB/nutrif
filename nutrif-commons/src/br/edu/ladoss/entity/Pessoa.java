package br.edu.ladoss.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement(name = "pessoa")
@Entity
@Table(name = "tb_pessoa")
@NamedQuery(name = "Pessoa.getAll", query = "from Pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tp_pessoa", discriminatorType = DiscriminatorType.INTEGER)
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 3773602055618799026L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pessoa")
	private Integer id;

	@Column(name = "nm_pessoa")
	private String nome;
	
	@Column(name = "nm_senha")
	private String senha;
	
	@Column(name = "nm_keyauth")
	private String keyAuth;

	@Column(name = "nm_email", unique = true)
	private String email;
	
	@Column(name = "tp_pessoa", insertable = false, updatable = false)
    private String tipo;
	
	@Column(name = "is_ativo")
	private boolean ativo;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "tb_pessoa_role", 
		joinColumns = @JoinColumn(name = "fk_id_pessoa"), 
		inverseJoinColumns = @JoinColumn(name = "fk_id_role")
	)
	private List<Role> roles;
	
	public static String TIPO_FUNCIONARIO = "1";
	public static String TIPO_ALUNO = "2";
	
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
	
	@JsonIgnore
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@JsonIgnore
	public String getKeyAuth() {
		return keyAuth;
	}

	public void setKeyAuth(String keyAuth) {
		this.keyAuth = keyAuth;
	}
	
	@XmlElement
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@XmlElement
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", email=" + email 
				+ ", tipo=" + tipo + ", ativo=" + ativo + ", roles=" + roles + "]";
	}	
}
