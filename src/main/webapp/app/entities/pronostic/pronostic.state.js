(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pronostic', {
            parent: 'entity',
            url: '/pronostic?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pronostics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pronostic/pronostics.html',
                    controller: 'PronosticController',
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
        .state('pronostic-detail', {
            parent: 'pronostic',
            url: '/pronostic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pronostic'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pronostic/pronostic-detail.html',
                    controller: 'PronosticDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Pronostic', function($stateParams, Pronostic) {
                    return Pronostic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pronostic',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pronostic-detail.edit', {
            parent: 'pronostic-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pronostic/pronostic-dialog.html',
                    controller: 'PronosticDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pronostic', function(Pronostic) {
                            return Pronostic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pronostic.new', {
            parent: 'pronostic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pronostic/pronostic-dialog.html',
                    controller: 'PronosticDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                resourceid: null,
                                score1: null,
                                score2: null,
                                winner: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pronostic', null, { reload: 'pronostic' });
                }, function() {
                    $state.go('pronostic');
                });
            }]
        })
        .state('pronostic.edit', {
            parent: 'pronostic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pronostic/pronostic-dialog.html',
                    controller: 'PronosticDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pronostic', function(Pronostic) {
                            return Pronostic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pronostic', null, { reload: 'pronostic' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pronostic.delete', {
            parent: 'pronostic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pronostic/pronostic-delete-dialog.html',
                    controller: 'PronosticDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pronostic', function(Pronostic) {
                            return Pronostic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pronostic', null, { reload: 'pronostic' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
