package br.edu.ladoss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pretensaoRefeicao")
@Entity
@Table(name = "tb_pretensao_refeicao")
@NamedQuery(name = "PretensaoRefeicao.getAll", query = "from PretensaoRefeicao")
public class PretensaoRefeicao {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pretensao_refeicao", unique = true) //TODO: columnDefinition = "INT(10) UNSIGNED AUTO_INCREMENT"
	private Integer id;
	
	@EmbeddedId	  
	private ConfirmaPretensaoDia confirmaPretensaoDia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_solicitacao", insertable = true, updatable = false)
	private Date dataSolicitacao;

	@Column(name = "nm_keyaccess", unique = true)
	private String keyAccess;	
	
	@Column(name = "is_ativo", columnDefinition = "boolean default true", 
			nullable = false, insertable = true, updatable = true)
	private boolean ativo;
	
	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public ConfirmaPretensaoDia getConfirmaPretensaoDia() {
		return confirmaPretensaoDia;
	}

	public void setConfirmaPretensaoDia(ConfirmaPretensaoDia confirmaPretensaoDia) {
		this.confirmaPretensaoDia = confirmaPretensaoDia;
	}

	@XmlElement
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	@XmlElement
	public String getKeyAccess() {
		return keyAccess;
	}

	public void setKeyAccess(String keyAccess) {
		this.keyAccess = keyAccess;
	}
	
	@XmlElement
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public String toString() {
		
		return "PretencaoRefeicao[id=" + id 
				+ ", ConfirmaPretensaoDia=" + confirmaPretensaoDia 
				+ ", dataSolicitacao=" + dataSolicitacao
				+ ", ativo=" + ativo + "]";
	}
}
