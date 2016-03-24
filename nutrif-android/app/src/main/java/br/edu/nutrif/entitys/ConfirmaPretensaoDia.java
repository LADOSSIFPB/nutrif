package br.edu.nutrif.entitys;

import java.util.Date;

public class ConfirmaPretensaoDia{

	private static final long serialVersionUID = 4103942093838478635L;

	private DiaRefeicao diaRefeicao = new DiaRefeicao();

	private Date dataPretensao;

	public DiaRefeicao getDiaRefeicao() {
		return diaRefeicao;
	}

	public void setDiaRefeicao(DiaRefeicao diaRefeicao) {
		this.diaRefeicao = diaRefeicao;
	}

	public Date getDataPretensao() {
		return dataPretensao;
	}

	public void setDataPretensao(Date dataPretensao) {
		this.dataPretensao = dataPretensao;
	}

	@Override
	public String toString() {
		return "ConfirmaPretensaoDia [DiaRefeicao=" + diaRefeicao 
				+ ", dataPretensao=" + dataPretensao + "]";
	}
	
	@Override
	public boolean equals(Object o) {		
		
		if ((o instanceof ConfirmaPretensaoDia)
				&& ((ConfirmaPretensaoDia) o).getDiaRefeicao().getId() == this.diaRefeicao.getId()
				&& ((ConfirmaPretensaoDia) o).getDataPretensao().compareTo(this.dataPretensao) == 0
				&& ((ConfirmaPretensaoDia) o).getDiaRefeicao().isAtivo() == this.diaRefeicao.isAtivo()) { 
			
			return true; 
			
		} else {
			
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		
		return this.diaRefeicao.getId();
	}
}