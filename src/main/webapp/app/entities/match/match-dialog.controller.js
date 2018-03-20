(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('MatchDialogController', MatchDialogController);

    MatchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Match', 'Team'];

    function MatchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Match, Team) {
        var vm = this;

        vm.match = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.teams = Team.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.match.id !== null) {
                Match.update(vm.match, onSaveSuccess, onSaveError);
            } else {
                Match.save(vm.match, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('betcoinApp:matchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
