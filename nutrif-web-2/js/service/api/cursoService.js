/*
 *  Mapeamento dos serviço de Curso.
 */
nutrIFApp.factory("cursoService", function ($http, config) {

    var _path = config.baseUrl() + "/curso";

    var _listarCursos = function (curso) {
        return $http.get(_path + "/listar");
    };

    var _cadastrarCurso = function (curso) {
        return $http.post(_path + "/inserir", curso);
    }

    var _buscarCursoPorNome = function (nome) {
        return $http.get(_path + "/listar/nome/" + nome);
    };

    var _getCursoById = function (id) {
        return $http.get(_path + "/id/" + id);
    };

    var _atualizarCurso = function (curso) {
        return $http.post(_path + "/atualizar", curso);
    };

    return {
        listarCursos: _listarCursos,
        cadastrarCurso: _cadastrarCurso,
        buscarCursoPorNome: _buscarCursoPorNome,
        getCursoById: _getCursoById,
        atualizarCurso: _atualizarCurso
    };
});