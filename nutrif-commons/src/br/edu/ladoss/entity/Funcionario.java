package br.edu.ladoss.entity;

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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "funcionario")
@Entity
@Table(name = "tb_funcionario")
@NamedQuery(name = "Funcionario.getAll", query = "from Funcionario")
@DiscriminatorValue(value = "1")
public class Funcionario extends Pessoa {

	private static final long serialVersionUID = 7914104914276090901L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_funcionario", insertable=true)
	private Integer id;
	
	@Column(name = "id_migracao")
	private int idMigracao;
	
	@Column(name = "nr_siape", length = 13, unique = true)
	private String siape;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_setor")
	private Setor setor;

	public Funcionario() {
		super();
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
	public int getIdMigracao() {
		return idMigracao;
	}

	public void setIdMigracao(int idMigracao) {
		this.idMigracao = idMigracao;
	}
	
	public String getSiape() {
		return siape;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}
	
	@Override
	public String toString() {
		return "Funcionario [" + super.toString() + "]";
	}
}
