angular.module('eObrazovanjeApp').controller(
	'ProfessorController', [
		'$rootScope',
		'$scope',
		'$http',
		'$routeParams',
		'$location',
		'authService',
		function($rootScope, $scope, $http, $routeParams, authService, $location) {
			$rootScope.userId = localStorage.getItem('userId');
			$scope.getProfessor = function(id) {
				$http.get('api/professors/' + id).success(
					function(data, status) {
						$scope.professor = data;

					}).error(function() {
					$scope.redAlert = true;

				});
			};
			$scope.pageNumber = 0;

			$scope.getAllProfessors = function() {
				$http.get('api/professors', {
						params: {
							"text": $scope.text,
							"pageNumber": $scope.pageNumber

						}
					})
					.success(function(data, status) {
						$scope.professors = data.content;
						$scope.pageNum = data.number + 1;
						$scope.pageNumMax = data.totalPages;
					}).error(function() {
						alert('Oops, something went wrong!');
					});
				$scope.resetFilter = function() {

				}
			};

			$scope.deleteProfessor = function(id) {
				$http.delete('api/professors/delete/' + id).success(
					function(data, status) {
						$scope.deleted = data;
						$scope.blueAlert = true;
						$scope.getAllProfessors();

					}).error(function() {
					$scope.redAlert = true;

				});
			};

			$scope.hideAlerts = function() {
				$scope.redAlert = false;
				$scope.blueAlert = false;
				$scope.orangeAlert = false;
			};

			$scope.initProfessor = function() {
				$scope.professor = {};

				if ($routeParams && $routeParams.id) {
					// ovo je edit stranica
					$http.get('api/professors/' + $routeParams.id).success(
						function(data) {
							$scope.professor = data;
						}).error(function() {

					});
				}
			};

			$scope.saveProfessor = function() {
				if ($scope.professor.id) {
					// edit stranica
					$http.put('api/professors/edit/' + $scope.professor.id,
						$scope.professor).success(function() {
							if ($scope.isAdmin()) {
								window.location ="#/professors";
							} else if($scope.isProfessor()){
								window.location ="#/subjects/getFor/"+$rootScope.userId;
							}
							
					}).error(function() {
						alert("neka greska edita");
					});
				} else {
					// add stranica
					$http.post('api/professors/add/', $scope.professor).success(
						function() {
							if ($scope.isAdmin()) {
								window.location ="#/professors";
							} else if($scope.isProfessor()){
								window.location ="#/subjects/getFor/"+$rootScope.userId;
							}
						}).error(function() {
						alert('greska dodavanja!')
					});
				}
			};
			// paginacija
			$scope.previousPage = function() {
				if ($scope.pageNumber != 0) {
					$scope.pageNumber = $scope.pageNumber - 1;
				}
				$http.get('api/professors', {
						params: {
							"pageNumber": $scope.pageNumber

						}
					})
					.success(function(data, status) {
						$scope.professors = data.content;
						$scope.pageNum = data.number + 1;
					}).error(function() {
						alert('Oops, something went wrong!');
					});

			};
			$scope.firstPage = function() {
				$scope.pageNumber = 0;

				$http.get('api/professors', {
						params: {
							"pageNumber": $scope.pageNumber

						}
					})
					.success(function(data, status) {
						$scope.professors = data.content;
						$scope.pageNum = data.number + 1;
					}).error(function() {
						alert('Oops, something went wrong!');
					});

			};
			$scope.nextPage = function() {
				if ($scope.pageNumber + 1 < $scope.pageNumMax) {
					$scope.pageNumber = $scope.pageNumber + 1;
				}
				$http.get('api/professors', {
						params: {
							"pageNumber": $scope.pageNumber

						}
					})
					.success(function(data, status) {
						$scope.professors = data.content;
						$scope.pageNum = data.number + 1;
					}).error(function() {
						alert('Oops, something went wrong!');
					});
			};
			$scope.lastPage = function() {
				$scope.pageNumber = $scope.pageNumMax - 1;
				$http.get('api/professors', {
						params: {
							"pageNumber": $scope.pageNumber
						}
					})
					.success(function(data, status) {
						$scope.professors = data.content;
						$scope.pageNum = data.number + 1;
					}).error(function() {
						alert('Oops, something went wrong!');
					});

			};
			
			$scope.upload = function(){
				var id = $routeParams.id;
				var fd= new FormData();
				angular.forEach($scope.files,function(file){
					fd.append('file', file)
				});
				$http.post('api/documents/profilePic/'+id, fd,{
					transformRequest:angular.identity,	
					headers:{'Content-Type': undefined}
				})
				.success(function(data){
					$scope.professor.picturePath = data;
				});
				
			};
			
		}
	]);