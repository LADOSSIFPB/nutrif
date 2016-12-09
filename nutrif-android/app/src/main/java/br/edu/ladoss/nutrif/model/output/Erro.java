package br.edu.ladoss.nutrif.model.output;

public class Erro {

	private int codigo;
	
	private String mensagem;

	public Erro() {}
	
	public Erro(int codigo, String mensagem) {
		this.codigo = codigo;
		this.mensagem = mensagem;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@Override
	public String toString() {
		return "Erro [codigo:" + codigo + "; mensagem:" + mensagem + "]";
	}
}