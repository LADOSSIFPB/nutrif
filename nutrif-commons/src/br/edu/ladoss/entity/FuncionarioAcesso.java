package br.edu.ladoss.entity;

import javax.xml.bind.annotation.XmlElement;

public class FuncionarioAcesso extends Pessoa {

	private static final long serialVersionUID = 8011399615861874456L;

	public static FuncionarioAcesso getInstance(Funcionario funcionario) {

		// Conversão de Funcionário para Funcionário de Acesso.
		FuncionarioAcesso funcionarioAcesso = new FuncionarioAcesso();		
		funcionarioAcesso.setId(funcionario.getId());
		funcionarioAcesso.setNome(funcionario.getNome());
		funcionarioAcesso.setTipo(funcionario.getTipo());
		funcionarioAcesso.setEmail(funcionario.getEmail());
		funcionarioAcesso.setKeyAuth(funcionario.getKeyAuth());
		funcionarioAcesso.setRoles(funcionario.getRoles());
		
		return funcionarioAcesso;
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
