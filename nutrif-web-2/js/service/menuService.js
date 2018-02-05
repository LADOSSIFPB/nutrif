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
            name: 'Aluno',
            state: 'administrador.listar-alunos',
            type: 'link'
        },

        {
            name: 'Refeitório',
            type: 'toggle',
            pages: [
                {
                    name: 'Entrada',
                    state: '#',
                    type: 'link'
                },
                {
                    name: 'Reconhecimento Facial',
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