(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pronotype', {
            parent: 'entity',
            url: '/pronotype?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pronotypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pronotype/pronotypes.html',
                    controller: 'PronotypeController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('pronotype-detail', {
            parent: 'pronotype',
            url: '/pronotype/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pronotype'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pronotype/pronotype-detail.html',
                    controller: 'PronotypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Pronotype', function($stateParams, Pronotype) {
                    return Pronotype.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pronotype',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pronotype-detail.edit', {
            parent: 'pronotype-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pronotype/pronotype-dialog.html',
                    controller: 'PronotypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pronotype', function(Pronotype) {
                            return Pronotype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pronotype.new', {
            parent: 'pronotype',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pronotype/pronotype-dialog.html',
                    controller: 'PronotypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                label: null,
                                expirationDate: null,
                                points: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pronotype', null, { reload: 'pronotype' });
                }, function() {
                    $state.go('pronotype');
                });
            }]
        })
        .state('pronotype.edit', {
            parent: 'pronotype',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pronotype/pronotype-dialog.html',
                    controller: 'PronotypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pronotype', function(Pronotype) {
                            return Pronotype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pronotype', null, { reload: 'pronotype' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pronotype.delete', {
            parent: 'pronotype',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pronotype/pronotype-delete-dialog.html',
                    controller: 'PronotypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pronotype', function(Pronotype) {
                            return Pronotype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pronotype', null, { reload: 'pronotype' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
