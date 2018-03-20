(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('GamerDialogController', GamerDialogController);

    GamerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gamer', 'Pronostic'];

    function GamerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gamer, Pronostic) {
        var vm = this;

        vm.gamer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pronostics = Pronostic.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gamer.id !== null) {
                Gamer.update(vm.gamer, onSaveSuccess, onSaveError);
            } else {
                Gamer.save(vm.gamer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('betcoinApp:gamerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lastconnexion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
