(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('GamerDeleteController',GamerDeleteController);

    GamerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gamer'];

    function GamerDeleteController($uibModalInstance, entity, Gamer) {
        var vm = this;

        vm.gamer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gamer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
