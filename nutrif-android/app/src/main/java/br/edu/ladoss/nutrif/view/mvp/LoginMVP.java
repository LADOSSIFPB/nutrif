package br.edu.ladoss.nutrif.view.mvp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import br.edu.ladoss.nutrif.MVPApp;
import br.edu.ladoss.nutrif.model.Aluno;

/**
 * Created by LADOSS on 21/06/2017.
 */

public interface LoginMVP {

    interface Model extends MVPApp.Model {

        /**
         * Realiza uma série de métodos que juntos realizam o login do usuário. Esse
         * processo é assíncrono.
         * @param aluno atributos que já são conhecidos pela aplicação.
         */
        void doLogin(Aluno aluno);

        /**
         * Recupera um objeto Aluno do banco de dados local (celular). Se não for possível recuperar
         * é retornada uma exception.
         * @return aluno salvo no bd.
         * @throws android.content.res.Resources.NotFoundException se não for encontrado
         * nenhum aluno ou se não for recuperado todas as informações necessárias.
         */
        Aluno retrieveFromDB() throws Resources.NotFoundException;

        /**
         * Realiza o download da foto de um aluno. Se ocorrer algum
         * erro no download, é lançada uma exception.
         * @param aluno aluno a ser capturado foto.
         * @throws Throwable quando não for possível baixar a foto do aluno.
         * @return aluno com o conjunto de bytes da sua foto.
         */
        Aluno downloadPhoto(Aluno aluno) throws Throwable;

        /**
         * Redireciona o usuário para uma tela de registro.
         */
        void doRegister();

        /**
         * Retorna a matrícula de uma aluno dado o seu id como parâmetro.
         * Se não for possível recuperar a matrícula é lançada uma exception.
         * @param id retorna a matrícula do aluno
         * @return matrícula do aluno
         * @throws Throwable caso a chamada não consiga ser efetuada.
         */
        String getAlunoWithMatricula(Integer id) throws Throwable;

        /**
         * Redireciona para a tela Home.
         */
        void redirectHomeActivity();

    }

    interface View extends MVPApp.View {

        /**
         * Mostra uma mensagem de carregamento na tela. É esperado uma string que seja
         * mostrada nesse carregamento.
         * @param message - string correspondente ao texto a ser impresso.
         */
        void showMessage(String message);

        /**
         * Quando chamado, esse método realiza a conversão da interface gráfica para que o
         * usuário possa pressionar um botão e refazer o login.
         */
        void showRefresh();

    }

    interface Presenter extends MVPApp.Presenter {
        /**
         * Tempo em milissegundos de cada texto que aparecerá.
         */
        final static int TIME_ANIMATION = 5000;

        /**
         * Chama uma sequência de métodos no modelo para que seja executado o login
         * por completo. O método é executado assíncrono e deve liberar a thread principal
         * para a camada de view imediatamente. Atenção: é importante lembrar que será chamado
         * o método que trocará. É verificado se a camada de view já mandou dados que serão utilizados
         * no login.
         * @param extra - dados extras que serão utilizados para o login (email, senha)
         */
        void doLogin(Bundle extra);

        /**
         * Chama o metodo showRefreash na camada de view.
         */
        void showRefresh();

        /**
         * Troca o texto que aparece na tela de carregamento em um intervalo de TIME_ANIMATION
         * milissegundos.
         */
        void doAnimation();

    }

}
