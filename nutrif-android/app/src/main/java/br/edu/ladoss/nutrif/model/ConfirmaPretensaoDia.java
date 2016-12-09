package br.edu.ladoss.nutrif.model;

public class ConfirmaPretensaoDia{

	private static final long serialVersionUID = 4103942093838478635L;

	private DiaRefeicao diaRefeicao = new DiaRefeicao();

	private Long dataPretensao;

	public DiaRefeicao getDiaRefeicao() {
		return diaRefeicao;
	}

	public Long getDataPretensao(){
		return dataPretensao;
	}

	public void setDataPretensao(Long dataPretensao) {
		this.dataPretensao = dataPretensao;
	}

}