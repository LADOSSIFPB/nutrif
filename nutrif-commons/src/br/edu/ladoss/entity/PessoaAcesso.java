package br.edu.ladoss.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pessoaAcesso")
public class PessoaAcesso extends Pessoa {

	private static final long serialVersionUID = 8011399615861874456L;

	@XmlElement
	private String keyAuth;
	
	@XmlElement
	private String matricula;
	
	public static PessoaAcesso getInstance(Pessoa pessoa) {

		// Conversão de Pessoa para PessoaAcesso.
		PessoaAcesso pessoaAcesso = new PessoaAcesso();		
		pessoaAcesso.setId(pessoa.getId());
		pessoaAcesso.setNome(pessoa.getNome());
		pessoaAcesso.setTipo(pessoa.getTipo());
		pessoaAcesso.setEmail(pessoa.getEmail());
		pessoaAcesso.setRoles(pessoa.getRoles());
		pessoaAcesso.setAtivo(pessoa.isAtivo());
		
		return pessoaAcesso;
	}
	
	public Pessoa getPessoa() {
		
		Pessoa pessoa = new Pessoa();
		
		pessoa.setId(getId());
		pessoa.setNome(getNome());
		pessoa.setEmail(getEmail());
		pessoa.setCampus(getCampus());
		pessoa.setSenha(getSenha());
		pessoa.setKeyAuth(getKeyAuth());
		pessoa.setRoles(getRoles());
		pessoa.setAtivo(isAtivo());		
		pessoa.setTipo(getTipo());
		
		return pessoa;		
	}
	
	@Override
	@XmlElement
	public String getSenha() {
		return super.getSenha();
	}
	
	
	@Override
	public String getKeyAuth() {
		return this.keyAuth;
	}

	public void setKeyAuth(String keyAuth) {
		
		// Enviar valor para a superclasse.
		super.setKeyAuth(keyAuth);
		
		this.keyAuth = keyAuth;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
