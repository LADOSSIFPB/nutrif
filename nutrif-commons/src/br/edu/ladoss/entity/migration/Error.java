package br.edu.ladoss.entity.migration;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "error")
@Entity
@Table(name = "tb_error_migration")
@NamedQuery(name = "Error.getAll", query = "from Error")
public class Error {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_campus")
	private int id;
	
	@Column(name = "nr_codigo")
	private int codigo;
	
	@Column(name = "nm_mensagem")
	private String mensagem;
	
	@Column(name = "nm_detalhe")
	private String detalhe;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_registro", insertable = true, updatable = false)
	private Date registro;

	public Error() {}
	
	public Error(int codigo, String mensagem) {
		this.codigo = codigo;
		this.mensagem = mensagem;
	}
	
	public Error(int codigo, String mensagem, String detalhe) {
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.detalhe = detalhe;
	}
	
	@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@XmlElement
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@XmlElement
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@XmlElement
	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	@XmlElement
	public Date getRegistro() {
		return registro;
	}

	public void setRegistro(Date registro) {
		this.registro = registro;
	}
	
	@Override
	public String toString() {
		return "Erro [codigo:" + codigo 
				+ "; mensagem:" + mensagem 
				+ "; detalhe:" + detalhe
				+ "; registro:" + registro + "]";
	}
}
