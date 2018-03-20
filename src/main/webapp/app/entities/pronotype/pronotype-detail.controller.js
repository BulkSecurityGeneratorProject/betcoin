(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('PronotypeDetailController', PronotypeDetailController);

    PronotypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pronotype', 'Pronostic'];

    function PronotypeDetailController($scope, $rootScope, $stateParams, previousState, entity, Pronotype, Pronostic) {
        var vm = this;

        vm.pronotype = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('betcoinApp:pronotypeUpdate', function(event, result) {
            vm.pronotype = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
