angular.module("NutrifApp").factory("arquivoService", function($http, config){

   var _path = config.baseUrl() + "/arquivo";
   
   var _upload = function(file,typeFile){
	 
       $http.post(_path + "/upload/"+typeFile.id,typeFile,file, {
           transformRequest: angular.identity,
           headers: {'Content-Type': undefined}
       })
   }
   
   return {
		upload: _upload
	};


});