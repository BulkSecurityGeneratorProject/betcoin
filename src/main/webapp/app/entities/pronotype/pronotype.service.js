(function() {
    'use strict';
    angular
        .module('betcoinApp')
        .factory('Pronotype', Pronotype);

    Pronotype.$inject = ['$resource', 'DateUtils'];

    function Pronotype ($resource, DateUtils) {
        var resourceUrl =  'api/pronotypes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.expirationDate = DateUtils.convertLocalDateFromServer(data.expirationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.expirationDate = DateUtils.convertLocalDateToServer(copy.expirationDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.expirationDate = DateUtils.convertLocalDateToServer(copy.expirationDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
