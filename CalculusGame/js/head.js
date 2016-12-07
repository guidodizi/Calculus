var app = angular.module('HeaderApp', []);

app.directive('headerDirective', function() {
  return {
    restrict: 'E',
    templateUrl: 'header.html'
  };
});