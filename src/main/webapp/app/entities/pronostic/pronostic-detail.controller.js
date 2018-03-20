(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('PronosticDetailController', PronosticDetailController);

    PronosticDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pronostic', 'Gamer', 'Pronotype'];

    function PronosticDetailController($scope, $rootScope, $stateParams, previousState, entity, Pronostic, Gamer, Pronotype) {
        var vm = this;

        vm.pronostic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('betcoinApp:pronosticUpdate', function(event, result) {
            vm.pronostic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
