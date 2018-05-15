package br.edu.ladoss.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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

	@Column(name = "nm_pessoa", nullable = false)
	private String nome;
	
	@Column(name = "nm_cpf", length = 11, unique = true)
	private String cpf;
	
	@Column(name = "nm_senha", nullable = true)
	private String senha;
	
	@Transient
	private String keyAuth;

	@Column(name = "nm_email", unique = true, nullable = false)
	private String email;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_campus", nullable = false)
	private Campus campus;
	
	@Column(name = "tp_pessoa", insertable = false, updatable = false)
    private String tipo;
	
	@Column(name = "is_ativo", nullable = false, insertable = true, updatable = true)
	private boolean ativo;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "tb_pessoa_role", 
		joinColumns = @JoinColumn(name = "fk_id_pessoa"), 
		inverseJoinColumns = @JoinColumn(name = "fk_id_role")
	)
	private List<Role> roles;
	
	public static String TIPO_FUNCIONARIO = "1";
	public static String TIPO_ALUNO = "2";
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_insercao", nullable = false,
		    columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date dataInsercao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_modificacao", nullable = true)
	private Date dataModificacao;
	
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
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
	
	@XmlElement
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@XmlElement
	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	
	@XmlElement
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@XmlElement
	public Date getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
	@Override
	public String toString() {
		return "Pessoa [id=" + id 
				+ ", nome=" + nome
				+ ", cpf=" + cpf
				+ ", email=" + email 
				+ ", campus=" + campus 
				+ ", tipo=" + tipo				
				+ ", ativo=" + ativo 
				+ ", roles=" + roles + "]";
	}		
}
