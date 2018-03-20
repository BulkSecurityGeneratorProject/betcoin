(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('GroupDetailController', GroupDetailController);

    GroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Group', 'Team'];

    function GroupDetailController($scope, $rootScope, $stateParams, previousState, entity, Group, Team) {
        var vm = this;

        vm.group = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('betcoinApp:groupUpdate', function(event, result) {
            vm.group = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
