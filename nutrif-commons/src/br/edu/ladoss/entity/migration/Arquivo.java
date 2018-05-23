package br.edu.ladoss.entity.migration;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.edu.ladoss.enumeration.TipoArquivo;

@XmlRootElement(name = "arquivo")
@Entity
@Table(name = "tb_arquivo_migration")
@NamedQuery(name = "Arquivo.getAll", query = "from Arquivo")
public class Arquivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_arquivo")
	private Integer id;
	
	@Column(name = "nm_real_arquivo")
	private String nomeRealArquivo;

	@Column(name = "nm_sistema_arquivo")
	private String nomeSistemaArquivo;

	@Column(name = "nm_extensao_arquivo")
	private String extensaoArquivo;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_submetedor", referencedColumnName="id_pessoa")
	private Pessoa submetedor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_registro", insertable = true, updatable = false)
	private Date registro;

	@Column(name = "tp_arquivo")
	@Enumerated(EnumType.ORDINAL)
	private TipoArquivo tipoArquivo;
	
	@Column(name = "is_ativo")
	private boolean ativo;

	@Transient
	private byte[] file;

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement
	public String getNomeRealArquivo() {
		return nomeRealArquivo;
	}

	public void setNomeRealArquivo(String nomeRealArquivo) {
		this.nomeRealArquivo = nomeRealArquivo;
	}

	@XmlElement
	public String getNomeSistemaArquivo() {
		return nomeSistemaArquivo;
	}

	public void setNomeSistemaArquivo(String nomeSistemaArquivo) {
		this.nomeSistemaArquivo = nomeSistemaArquivo;
	}

	@XmlElement
	public String getExtensaoArquivo() {
		return extensaoArquivo;
	}

	public void setExtensaoArquivo(String extensaoArquivo) {
		this.extensaoArquivo = extensaoArquivo;
	}

	@XmlElement
	public Date getRegistro() {
		return registro;
	}

	public void setRegistro(Date registro) {
		this.registro = registro;
	}

	@XmlElement
	public TipoArquivo getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(TipoArquivo tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	@XmlElement
	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@XmlElement
	public Pessoa getSubmetedor() {
		return submetedor;
	}

	public void setSubmetedor(Pessoa submetedor) {
		this.submetedor = submetedor;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Arquivo [id=" + id + ", nomeReal=" + nomeRealArquivo 
				+ ", nomeSistema="+ nomeSistemaArquivo +", " + tipoArquivo 
				+ ", registro=" + registro + ", ativo=" + ativo + "]";
	}
}