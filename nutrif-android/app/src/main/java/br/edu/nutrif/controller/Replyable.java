package br.edu.nutrif.controller;

import br.edu.nutrif.entitys.output.Erro;

/**
 * Created by juan on 21/03/16.
 */
public interface Replyable<Entity> {
    public void onSuccess(Entity entity);
    public void onFailure(Erro erro);
    public void failCommunication(Throwable throwable);
}
