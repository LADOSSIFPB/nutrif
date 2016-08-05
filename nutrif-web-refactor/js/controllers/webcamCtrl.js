angular.module('NutrifApp').controller('webcamCtrl', function($scope, $mdToast,arquivoService,$http, alunoService, $stateParams,$state, $mdDialog) {

	$scope.myImage='';

	$scope.aluno={};

	var file;

	$scope.myChannel = {
		// the fields below are all optional
		videoHeight: 600,
		videoWidth: 450,
		video: null // Will reference the video element on success
	};
	var _video = null;

	$scope.patData = null;

	$scope.snapshotData = null;

	$scope.myCroppedImage= '';

	$scope.patOpts = {x: 0, y: 0, w: 25, h: 25};

	// Setup a channel to receive a video property
	// with a reference to the video element
	// See the HTML binding in main.html
	$scope.channel = {};

	$scope.onError = function () {
		$scope.$apply(
			function() {
				var _err = 'Webcam não pode ser iniciada. Você permitiu o acesso?';

				$mdToast.show(
					$mdToast.simple()
					.textContent(_err)
					.position('top right')
					.action('OK')
					.hideDelay(6000)
				);
			}
		);
	};

	$scope.onSuccess = function () {
		// The video element contains the captured camera data
		_video = $scope.myChannel.video;
		$scope.$apply(function() {
			$scope.patOpts.w = _video.width;
			$scope.patOpts.h = _video.height;
			//$scope.showDemos = true;
		});
	};

	$scope.onStream = function (stream) {
		// You could do something manually with the stream.
	};

	$scope.makeSnapshot = function() {
		if (_video) {

			var patCanvas = document.querySelector('canvas');

			if (!patCanvas) return;

			patCanvas.width = _video.width;
			patCanvas.height = _video.height;

			var ctxPat = patCanvas.getContext('2d');

			var idata = getVideoData($scope.patOpts.x, $scope.patOpts.y, $scope.patOpts.w, $scope.patOpts.h);
			ctxPat.putImageData(idata, 0, 0);

			sendSnapshotToServer(patCanvas.toDataURL());

			$scope.patData = idata;

			$scope.myImage= patCanvas.toDataURL();

			$mdDialog.show({
				controller: enviarImagemCtrl,
	      templateUrl: 'view/manager/modals/modal-foto-perfil.html',
	      parent: angular.element(document.body),
	      clickOutsideToClose:true,
	      fullscreen: false,
				locals : {
						myImage: $scope.myImage,
						aluno: $scope.aluno
				}
	    })
	    .then(function() {}, function() {});
		}
	};

	/**
	* Redirect the browser to the URL given.
	* Used to download the image by passing a dataURL string
	*/
	$scope.downloadSnapshot = function downloadSnapshot(dataURL) {
		window.location.href = dataURL;
	};

	var getVideoData = function getVideoData(x, y, w, h) {
		var hiddenCanvas = document.createElement('canvas');
		hiddenCanvas.width = _video.width;
		hiddenCanvas.height = _video.height;
		var ctx = hiddenCanvas.getContext('2d');
		ctx.drawImage(_video, 0, 0, _video.width, _video.height);
		return ctx.getImageData(x, y, w, h);
	};

	var sendSnapshotToServer = function sendSnapshotToServer(imgBase64) {

		$scope.snapshotData = imgBase64;

	};

function enviarImagemCtrl (myImage,$scope, $mdDialog, $mdToast, arquivoService,aluno) {

	    $scope.myImage = myImage;

			$scope.aluno=aluno;

			$scope.myCroppedImage = '';


	    $scope.hide = function() {

				// Conversão para arquivo da imagem capturada.

				var blob = dataURItoBlob($scope.myCroppedImage);

				file = new File([blob], 'foto_perfil_'+$scope.aluno.id+'.jpg', {type: "'image/jpeg"});

				arquivoService.upload(file,'foto_perfil_'+$scope.aluno.id+'.jpg',$scope.aluno.id)
						.success(onSuccessCallback)
						.error(onErrorCallback);

			}

			function onSuccessCallback (data, status) {
				$mdToast.show(
					$mdToast.simple()
					.textContent('Perfil do Aluno alterado com sucesso')
					.position('top right')
					.action('OK')
					.hideDelay(6000)
				);
        $mdDialog.cancel();
				$state.transitionTo('home.listar-alunos',  {reload: true});
			}

			function onErrorCallback (data, status) {
				var _message = '';

				if (!data) {
					_message = 'Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.'
				} else {
					_message = data.mensagem
				}

				$mdToast.show(
					$mdToast.simple()
					.textContent(_message)
					.position('top right')
					.action('OK')
					.hideDelay(6000)
				);

	    };

	    $scope.cancel = function() {
	        $mdDialog.cancel();
	    };

			var dataURItoBlob = function dataURItoBlob(dataURI) {

				// convert base64/URLEncoded data component to raw binary data held in a string
				var byteString;
				if (dataURI.split(',')[0].indexOf('base64') >= 0)
				byteString = atob(dataURI.split(',')[1]);
				else
				byteString = unescape(dataURI.split(',')[1]);

				// separate out the mime component
				var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

				// write the bytes of the string to a typed array
				var ia = new Uint8Array(byteString.length);
				for (var i = 0; i < byteString.length; i++) {
					ia[i] = byteString.charCodeAt(i);
				}

				return new Blob([ia], {type:mimeString});
			}

	};

	function carregamentoInicial() {

		var _matricula = $stateParams.matricula;

		alunoService.buscaAlunoPorMatricula(_matricula)
		.success(function (data, status) {
			$scope.aluno = data;
		})
		.error(onErrorCallback);
	}

	function onErrorCallback (data, status) {
		var _message = '';

		if (!data) {
			_message = 'Ocorreu um erro na comunicação com o servidor, favor chamar o suporte.'
		} else {
			_message = data.mensagem
		}
	}


	carregamentoInicial();
});
