package br.edu.ladoss.entity;

import javax.xml.bind.annotation.XmlElement;

public class PessoaAcesso extends Pessoa {

	private static final long serialVersionUID = 8011399615861874456L;

	public static PessoaAcesso getInstance(Pessoa pessoa) {

		// Conversão de Funcionário para Funcionário de Acesso.
		PessoaAcesso pessoaAcesso = new PessoaAcesso();		
		pessoaAcesso.setId(pessoa.getId());
		pessoaAcesso.setNome(pessoa.getNome());
		pessoaAcesso.setTipo(pessoa.getTipo());
		pessoaAcesso.setEmail(pessoa.getEmail());
		pessoaAcesso.setKeyAuth(pessoa.getKeyAuth());
		pessoaAcesso.setRoles(pessoa.getRoles());
		
		return pessoaAcesso;
	}
	
	@XmlElement
	@Override
	public String getSenha() {
		return super.getSenha();
	}
	
	@XmlElement
	@Override
	public String getKeyAuth() {
		return super.getKeyAuth();
	}
}
