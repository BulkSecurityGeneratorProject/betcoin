(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('PronotypeDeleteController',PronotypeDeleteController);

    PronotypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pronotype'];

    function PronotypeDeleteController($uibModalInstance, entity, Pronotype) {
        var vm = this;

        vm.pronotype = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pronotype.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
