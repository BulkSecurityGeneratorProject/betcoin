(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('PronosticDeleteController',PronosticDeleteController);

    PronosticDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pronostic'];

    function PronosticDeleteController($uibModalInstance, entity, Pronostic) {
        var vm = this;

        vm.pronostic = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pronostic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
