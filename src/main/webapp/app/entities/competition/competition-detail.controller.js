(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('CompetitionDetailController', CompetitionDetailController);

    CompetitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competition', 'Team'];

    function CompetitionDetailController($scope, $rootScope, $stateParams, previousState, entity, Competition, Team) {
        var vm = this;

        vm.competition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('betcoinApp:competitionUpdate', function(event, result) {
            vm.competition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
