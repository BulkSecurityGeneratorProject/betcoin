'use strict';

describe('Controller Tests', function() {

    describe('Pronostic Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPronostic, MockGamer, MockPronotype;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPronostic = jasmine.createSpy('MockPronostic');
            MockGamer = jasmine.createSpy('MockGamer');
            MockPronotype = jasmine.createSpy('MockPronotype');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Pronostic': MockPronostic,
                'Gamer': MockGamer,
                'Pronotype': MockPronotype
            };
            createController = function() {
                $injector.get('$controller')("PronosticDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'betcoinApp:pronosticUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
