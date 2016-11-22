package br.edu.ladoss.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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

@XmlRootElement(name = "funcionario")
@Entity
@Table(name = "tb_funcionario")
@NamedQuery(name = "Funcionario.getAll", query = "from Funcionario")
@DiscriminatorValue(value = "1")
public class Funcionario extends Pessoa {

	private static final long serialVersionUID = 7914104914276090901L;

	public Funcionario() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_funcionario")
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_setor")
	private Setor setor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_insercao", nullable = false,
		    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private Date dataInsercao;

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement
	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	
	@XmlElement
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	public static Funcionario getFuncionario(Pessoa pessoa) {
		
		Funcionario funcionario = new Funcionario();
		
		funcionario.setId(pessoa.getId());
		funcionario.setNome(pessoa.getNome());
		funcionario.setEmail(pessoa.getEmail());
		funcionario.setCampus(pessoa.getCampus());
		funcionario.setSenha(pessoa.getSenha());
		funcionario.setKeyAuth(pessoa.getKeyAuth());
		funcionario.setRoles(pessoa.getRoles());
		funcionario.setAtivo(pessoa.isAtivo());		
		funcionario.setTipo(pessoa.getTipo());
		
		return funcionario;		
	}
	
	@Override
	public String toString() {
		return "Funcionario [" + super.toString() + "]";
	}
}
