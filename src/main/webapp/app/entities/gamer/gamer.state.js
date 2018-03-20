(function() {
    'use strict';

    angular
        .module('betcoinApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gamer', {
            parent: 'entity',
            url: '/gamer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gamers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gamer/gamers.html',
                    controller: 'GamerController',
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
        .state('gamer-detail', {
            parent: 'gamer',
            url: '/gamer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gamer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gamer/gamer-detail.html',
                    controller: 'GamerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Gamer', function($stateParams, Gamer) {
                    return Gamer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gamer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gamer-detail.edit', {
            parent: 'gamer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gamer/gamer-dialog.html',
                    controller: 'GamerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gamer', function(Gamer) {
                            return Gamer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gamer.new', {
            parent: 'gamer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gamer/gamer-dialog.html',
                    controller: 'GamerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pseudo: null,
                                email: null,
                                points: null,
                                lastconnexion: null,
                                isadmin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gamer', null, { reload: 'gamer' });
                }, function() {
                    $state.go('gamer');
                });
            }]
        })
        .state('gamer.edit', {
            parent: 'gamer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gamer/gamer-dialog.html',
                    controller: 'GamerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gamer', function(Gamer) {
                            return Gamer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gamer', null, { reload: 'gamer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gamer.delete', {
            parent: 'gamer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gamer/gamer-delete-dialog.html',
                    controller: 'GamerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gamer', function(Gamer) {
                            return Gamer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gamer', null, { reload: 'gamer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
