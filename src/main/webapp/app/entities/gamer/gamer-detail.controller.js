(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('GamerDetailController', GamerDetailController);

    GamerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gamer', 'Pronostic'];

    function GamerDetailController($scope, $rootScope, $stateParams, previousState, entity, Gamer, Pronostic) {
        var vm = this;

        vm.gamer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('betcoinApp:gamerUpdate', function(event, result) {
            vm.gamer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
