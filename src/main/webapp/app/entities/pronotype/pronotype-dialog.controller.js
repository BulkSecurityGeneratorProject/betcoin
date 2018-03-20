(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('PronotypeDialogController', PronotypeDialogController);

    PronotypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pronotype', 'Pronostic'];

    function PronotypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pronotype, Pronostic) {
        var vm = this;

        vm.pronotype = entity;
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
            if (vm.pronotype.id !== null) {
                Pronotype.update(vm.pronotype, onSaveSuccess, onSaveError);
            } else {
                Pronotype.save(vm.pronotype, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('betcoinApp:pronotypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.expirationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
