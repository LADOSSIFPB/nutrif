package br.edu.ladoss.entity.migration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pessoaAcesso")
public class AlunoAcesso extends Aluno {
	
	public AlunoAcesso(int idPessoa) {
		super(idPessoa);
	}

	private static final long serialVersionUID = 8011399615861874456L;

	@XmlElement
	private String keyAuth;
	
	public static AlunoAcesso getInstance(Pessoa pessoa) {

		// Conversão de Pessoa para PessoaAcesso.
		AlunoAcesso pessoaAcesso = new AlunoAcesso(pessoa.getId());		
		pessoaAcesso.setId(pessoa.getId());
		pessoaAcesso.setNome(pessoa.getNome());
		pessoaAcesso.setTipo(pessoa.getTipo());
		pessoaAcesso.setEmail(pessoa.getEmail());
		pessoaAcesso.setKeyAuth(pessoa.getKeyAuth());
		pessoaAcesso.setRoles(pessoa.getRoles());
		pessoaAcesso.setAtivo(pessoa.isAtivo());
		
		return pessoaAcesso;
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
}
