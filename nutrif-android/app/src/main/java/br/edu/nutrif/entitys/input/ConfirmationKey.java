package br.edu.nutrif.entitys.input;

/**
 * Created by juan on 15/03/16.
 */
public class ConfirmationKey {

    private String matricula;
    private String keyConfirmation;

    public ConfirmationKey(String matricula, String keyConfirmation) {
        this.matricula = matricula;
        this.keyConfirmation = keyConfirmation;
    }

    public String getKeyConfirmation() {
        return keyConfirmation;
    }

    public void setKeyConfirmation(String keyConfirmation) {
        this.keyConfirmation = keyConfirmation;
    }
}
