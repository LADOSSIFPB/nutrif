angular.module('NutrifApp').controller('listarAlunosCtrl', function ($scope, $mdToast, cursoService, alunoService) {

    $scope.alunos = [{nome: 'José Renan Silva Luciano', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano2', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano2', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano2', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano2', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano2', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}},
    {nome: 'José Renan Silva Luciano2', matricula:'20131004015', curso: {nome: 'Técnico em Informática'}}];

    $scope.query = {
        order: 'nome',
        limit: 8,
        page: 1
    };

});
