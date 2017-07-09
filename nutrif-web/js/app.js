/*
    Módulos adicionados e dependências:
        - ui.router: angular-ui-router.minjs, 
        - ngMaterial: angular-material.min.js, angular-material.min.css, angular-animate.min.js, angular-aria.min.js, angular-sanitize.min.js
        
        - Módulos complementares: LoadingBar, Cookies, DataTable, ImageCrop, Timeline, TableToCSV, ExpasionPanels, Charts.
 */
var nutrifApp = angular.module('AngularApp', ['ngRoute', 'ui.router', 'ngMaterial', 'ngAnimate', 'ngAria', 'angular-loading-bar', 'angular-loading-bar', 'ngCookies', 'md.data.table', 'webcam', 'ngImgCrop', 'angular-timeline', 'ngTableToCsv', 'material.components.expansionPanels', 'chart.js']);