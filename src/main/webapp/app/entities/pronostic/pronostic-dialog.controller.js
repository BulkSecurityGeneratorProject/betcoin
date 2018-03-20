(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('PronosticDialogController', PronosticDialogController);

    PronosticDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pronostic', 'Gamer', 'Pronotype'];

    function PronosticDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pronostic, Gamer, Pronotype) {
        var vm = this;

        vm.pronostic = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.gamers = Gamer.query();
        vm.pronotypes = Pronotype.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pronostic.id !== null) {
                Pronostic.update(vm.pronostic, onSaveSuccess, onSaveError);
            } else {
                Pronostic.save(vm.pronostic, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('betcoinApp:pronosticUpdate', result);
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
