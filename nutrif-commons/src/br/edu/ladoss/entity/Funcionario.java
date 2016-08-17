package br.edu.ladoss.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "funcionario")
@Entity
@Table(name = "tb_funcionario")
@NamedQuery(name = "Funcionario.getAll", query = "from Funcionario")
@DiscriminatorValue(value = "1")
public class Funcionario extends Pessoa {

	private static final long serialVersionUID = 7914104914276090901L;

	public static Funcionario setFuncionario(Pessoa pessoa) {
		
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

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_funcionario")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Funcionario [" + super.toString() + "]";
	}
}
