package br.edu.ladoss.nutrif.entitys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public String getDataPretensao() {
		Date date= new Date(dataPretensao.longValue());
		SimpleDateFormat df2 = new SimpleDateFormat("dd/MM HH:mm");
		String dateText = df2.format(date);
		return dateText;
	}

	public void setDataPretensao(Long dataPretensao) {
		this.dataPretensao = dataPretensao;
	}

	public void setDataPretensao(String dataPretensao){
		java.text.SimpleDateFormat dateformate = new SimpleDateFormat("dd/MM HH:mm");
		try {
			this.dataPretensao = dateformate.parse(dataPretensao).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}