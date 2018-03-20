(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .controller('CompetitionDialogController', CompetitionDialogController);

    CompetitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Competition', 'Team'];

    function CompetitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Competition, Team) {
        var vm = this;

        vm.competition = entity;
        vm.clear = clear;
        vm.save = save;
        vm.winners = Team.query({filter: 'competition-is-null'});
        $q.all([vm.competition.$promise, vm.winners.$promise]).then(function() {
            if (!vm.competition.winner || !vm.competition.winner.id) {
                return $q.reject();
            }
            return Team.get({id : vm.competition.winner.id}).$promise;
        }).then(function(winner) {
            vm.winners.push(winner);
        });
        vm.seconds = Team.query({filter: 'competition-is-null'});
        $q.all([vm.competition.$promise, vm.seconds.$promise]).then(function() {
            if (!vm.competition.second || !vm.competition.second.id) {
                return $q.reject();
            }
            return Team.get({id : vm.competition.second.id}).$promise;
        }).then(function(second) {
            vm.seconds.push(second);
        });
        vm.thirds = Team.query({filter: 'competition-is-null'});
        $q.all([vm.competition.$promise, vm.thirds.$promise]).then(function() {
            if (!vm.competition.third || !vm.competition.third.id) {
                return $q.reject();
            }
            return Team.get({id : vm.competition.third.id}).$promise;
        }).then(function(third) {
            vm.thirds.push(third);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.competition.id !== null) {
                Competition.update(vm.competition, onSaveSuccess, onSaveError);
            } else {
                Competition.save(vm.competition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('betcoinApp:competitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
