package br.edu.ladoss.nutrif.entitys;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

	public String getDataPretensao() {
		java.text.SimpleDateFormat dateformate = new SimpleDateFormat("dd/MM HH:mm:ss");
		return dateformate.format(dataPretensao);
	}

	public void setDataPretensao(Long dataPretensao) {
		this.dataPretensao = dataPretensao;
	}

	public void setDataPretensao(String dataPretensao){
		java.text.SimpleDateFormat dateformate = new SimpleDateFormat("dd/MM HH:mm:ss");
		try {
			this.dataPretensao = dateformate.parse(dataPretensao).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}