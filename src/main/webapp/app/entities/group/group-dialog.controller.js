(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('GroupDialogController', GroupDialogController);

    GroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Group', 'Team'];

    function GroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Group, Team) {
        var vm = this;

        vm.group = entity;
        vm.clear = clear;
        vm.save = save;
        vm.winners = Team.query({filter: 'group-is-null'});
        $q.all([vm.group.$promise, vm.winners.$promise]).then(function() {
            if (!vm.group.winner || !vm.group.winner.id) {
                return $q.reject();
            }
            return Team.get({id : vm.group.winner.id}).$promise;
        }).then(function(winner) {
            vm.winners.push(winner);
        });
        vm.seconds = Team.query({filter: 'group-is-null'});
        $q.all([vm.group.$promise, vm.seconds.$promise]).then(function() {
            if (!vm.group.second || !vm.group.second.id) {
                return $q.reject();
            }
            return Team.get({id : vm.group.second.id}).$promise;
        }).then(function(second) {
            vm.seconds.push(second);
        });
        vm.teams = Team.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.group.id !== null) {
                Group.update(vm.group, onSaveSuccess, onSaveError);
            } else {
                Group.save(vm.group, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('betcoinApp:groupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
