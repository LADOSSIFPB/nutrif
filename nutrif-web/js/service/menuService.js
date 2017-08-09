nutrifApp.factory("menu", function ($location, $rootScope) {

    var sections = [];

    sections.push(
            {
                name: 'Aluno',
                type: 'toggle',
                pages: [
                    {
                        name: 'Entrar no Restaurante',
                        state: 'home.entrar-restaurante',
                        type: 'link',                    
                        icon: 'image/icon/download-button.svg'
                    },
                    {
                        name: 'Listar',
                        state: 'home.listar-alunos',
                        type: 'link',                    
                        icon: 'image/icon/user-shape.svg'
                    },
                    {
                        name: 'Migrar Sábado Letivo',
                        state: 'home.migrar-sabado',
                        type: 'link',                    
                        icon: 'image/icon/swap-horizontal.svg'
                    }]
            }, 
            {
                name: 'Funcionário',
                state: 'home.listar-funcionarios',
                type: 'link',
                icon: 'image/icon/employee-icon.svg'
            },
            {
                name: 'Edital',
                type: 'toggle',
                pages: [
                    {
                        name: 'Listar',
                        state: 'home.listar-edital',
                        type: 'link',                    
                        icon: 'image/icon/copy-content.svg'
                    }]
            },
            {
                name: 'Refeição',
                state: 'home.listar-refeicoes',
                type: 'link',
                icon: 'image/icon/restaurant-teste.svg'
            },
            {
                name: 'Evento',
                state: 'home.listar-eventos',
                type: 'link',
                icon: 'image/icon/connection-indicator.svg'
            },
            {
                name: 'Curso',
                state: 'home.listar-cursos',
                type: 'link',
                icon: 'image/icon/graduate-cap.svg'
            },
            {
                name: 'Análise',
                type: 'toggle',
                pages: [
                    {
                        name: 'Dashboard',
                        state: 'home.dashboard',
                        type: 'link',                    
                        icon: 'image/icon/dashteste.svg'
                    },
                    {
                        name: 'Estatística',
                        state: 'home.estatisticas',
                        type: 'link',                    
                        icon: 'image/icon/poll-symbol-on-black-square-with-rounded-corners.svg'
                    }]
            }
        );

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