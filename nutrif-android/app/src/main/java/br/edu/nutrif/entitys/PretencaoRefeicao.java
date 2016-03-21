package br.edu.nutrif.entitys;

import br.edu.nutrif.entitys.input.Id;

/**
 * Created by juan on 19/03/16.
 */
public class PretencaoRefeicao {
    private DiaRefeicao diaRefeicao;
    private String dataHoraRequisicao;
    private String keyAccess;
    public PretencaoRefeicao(){
        diaRefeicao = new DiaRefeicao();
        dataHoraRequisicao = "";
        keyAccess = "";
    }

    public String getDataHoraRequisicao() {
        return dataHoraRequisicao;
    }

    public void setDataHoraRequisicao(String dataHoraRequisicao) {
        this.dataHoraRequisicao = dataHoraRequisicao;
    }

    public String getKeyAccess() {
        return keyAccess;
    }

    public void setKeyAccess(String keyAccess) {
        this.keyAccess = keyAccess;
    }

    public DiaRefeicao getDiaRefeicao() {
        return diaRefeicao;
    }

    public void setDiaRefeicao(DiaRefeicao diaRefeicao) {
        this.diaRefeicao = diaRefeicao;
    }
}
