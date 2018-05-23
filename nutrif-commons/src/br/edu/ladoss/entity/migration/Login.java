package br.edu.ladoss.entity.migration;

import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "login")
@Entity
@Table(name = "tb_login_migration")
@NamedQuery(name = "Login.getAll", query = "from Login")
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_login")
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_pessoa", referencedColumnName="id_pessoa")
	private Pessoa pessoa;
	
	@Column(name = "nm_keyauth")
	private String keyAuth;
	
	@Column(name = "nm_useragent")
	private String userAgent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_registro", insertable = true, updatable = false)
	private Date registro;
	
	@Column(name = "nm_remoteaddr")
	private String remoteAddr;
	
	@Column(name = "is_loged")
	private boolean loged;

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@XmlElement
	public Date getRegistro() {
		return registro;
	}

	public void setRegistro(Date registro) {
		this.registro = registro;
	}
	
	@XmlElement
	public String getKeyAuth() {
		return keyAuth;
	}

	public void setKeyAuth(String keyAuth) {
		this.keyAuth = keyAuth;
	}

	@XmlElement
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	@XmlElement
	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	@XmlElement
	public boolean isLoged() {
		return loged;
	}

	public void setLoged(boolean loged) {
		this.loged = loged;
	}
	
	@Override
	public String toString() {
		return "Login[pessoa=" + pessoa.getEmail()
				+ ", registro=" + registro
				+ ", userAgent" + userAgent
				+ "]";
	}
}
