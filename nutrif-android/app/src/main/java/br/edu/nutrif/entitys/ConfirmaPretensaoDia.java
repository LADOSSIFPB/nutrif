package br.edu.nutrif.entitys;

import java.util.Date;

public class ConfirmaPretensaoDia{

	private static final long serialVersionUID = 4103942093838478635L;

	private DiaRefeicao diaRefeicao = new DiaRefeicao();

	private Long dataPretensao;

	public DiaRefeicao getDiaRefeicao() {
		return diaRefeicao;
	}

	public void setDiaRefeicao(DiaRefeicao diaRefeicao) {
		this.diaRefeicao = diaRefeicao;
	}

	public Date getDataPretensao() {
		return new Date(dataPretensao);
	}

	public void setDataPretensao(Long dataPretensao) {
		this.dataPretensao = dataPretensao;
	}

}