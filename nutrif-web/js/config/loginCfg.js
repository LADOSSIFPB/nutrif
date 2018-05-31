/*
 *  Configuração de conexão de serviço.
 */
nutrIFApp.factory('loginCfg', function () {
  return {
    state: {
        admin:"administrador.home",
        inspetor:"inspetor.home",
        aluno:"aluno.home"
    }
  }
});