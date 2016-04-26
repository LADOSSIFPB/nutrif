package br.edu.ladoss.nutrif.entitys;

import java.util.Date;

/**
 * Created by juan on 19/03/16.
 */
public class PretensaoRefeicao {

    private Integer id;

    private ConfirmaPretensaoDia confirmaPretensaoDia = new ConfirmaPretensaoDia();

    private Long dataSolicitacao;

    private String keyAccess;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public ConfirmaPretensaoDia getConfirmaPretensaoDia() {
        return confirmaPretensaoDia;
    }

    public void setConfirmaPretensaoDia(ConfirmaPretensaoDia confirmaPretensaoDia) {
        this.confirmaPretensaoDia = confirmaPretensaoDia;
    }

    
    public Date getHoraPretensao() {

        return new Date(dataSolicitacao);
    }

    public void setHoraPretensao(Long horaPretensao) {
        this.dataSolicitacao = horaPretensao;
    }

    
    public String getKeyAccess() {
        return keyAccess;
    }

    public void setKeyAccess(String keyAccess) {
        this.keyAccess = keyAccess;
    }

}