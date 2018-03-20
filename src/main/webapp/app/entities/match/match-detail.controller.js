(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('MatchDetailController', MatchDetailController);

    MatchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Match', 'Team'];

    function MatchDetailController($scope, $rootScope, $stateParams, previousState, entity, Match, Team) {
        var vm = this;

        vm.match = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('betcoinApp:matchUpdate', function(event, result) {
            vm.match = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
