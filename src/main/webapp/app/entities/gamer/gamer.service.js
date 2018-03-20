(function() {
    'use strict';
    angular
        .module('betcoinApp')
        .factory('Gamer', Gamer);

    Gamer.$inject = ['$resource', 'DateUtils'];

    function Gamer ($resource, DateUtils) {
        var resourceUrl =  'api/gamers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastconnexion = DateUtils.convertLocalDateFromServer(data.lastconnexion);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.lastconnexion = DateUtils.convertLocalDateToServer(copy.lastconnexion);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.lastconnexion = DateUtils.convertLocalDateToServer(copy.lastconnexion);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
