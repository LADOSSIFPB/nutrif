/*
 *  Mapeamento dos serviço do Menu.
 */
nutrIFApp.factory("menu", function ($location, $rootScope) {

    var sections = [{
            name: 'Início',
            state: 'administrador.home',
            type: 'link'
        },
        {
            name: 'Refeitório',
            type: 'toggle',
            pages: [
                {
                    name: 'Aluno',
                    state: 'administrador.listar-alunos',
                    type: 'link'
                },
                {
                    name: 'Refeição',
                    state: 'administrador.listar-refeicoes',
                    type: 'link'
                },
                {
                    name: 'Campus',
                    state: 'administrador.listar-campi',
                    type: 'link'
                },
                {
                    name: 'Curso',
                    state: 'administrador.listar-cursos',
                    type: 'link'
                },
                {
                    name: 'Evento',
                    state: 'administrador.home',
                    type: 'link'
                },
                {
                    name: 'Edital',
                    state: 'administrador.listar-editais',
                    type: 'link'
                },
                {
                    name: 'Servidor',
                    state: 'administrador.home',
                    type: 'link'
                }
            ]
        },
        {
            name: 'Salão de Refeições',
            type: 'toggle',
            pages: [
                {
                    name: 'Entrada de Alunos',
                    state: 'administrador.listar-entradarefeitorio',
                    type: 'link'
                },
                {
                    name: 'Reconhecimento Facial',
                    state: '#',
                    type: 'link'
                }
            ]
        },
        {
            name: 'Estatísticas',
            type: 'toggle',
            pages: [
                {
                    name: 'Gráficos',
                    state: '#',
                    type: 'link'
                },
                {
                    name: 'Dashboard',
                    state: '#',
                    type: 'link'
                }
            ]
        }
    ];

    var self;

    return self = {

        sections: sections,

        toggleSelectSection: function (section) {
            self.openedSection = (self.openedSection === section ? null : section);
        },

        isSectionSelected: function (section) {
            return self.openedSection === section;
        },

        selectPage: function (section, page) {
            page && page.url && $location.path(page.url);
            self.currentSection = section;
            self.currentPage = page;
        }
    };

    function sortByHumanName(a, b) {
        return (a.humanName < b.humanName) ? -1 :
            (a.humanName > b.humanName) ? 1 : 0;
    }
});