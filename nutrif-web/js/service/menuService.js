nutrifApp.factory("menu", function ($location, $rootScope) {

    var sections = [{
          name: 'Início',
          state: 'home',
          type: 'link'
        }];

    sections.push({
        name: 'Aluno',
        type: 'toggle',
        pages: [
            {
                name: 'Cadastrar',
                type: 'link',
                state: 'aluno.cadastrar',
                icon: ''
            },
            {
                name: 'Listar',
                type: 'link',
                state: 'aluno.listar',
                icon: ''
            }]
    });

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