angular.module('NutrifApp').controller('webcamCtrl', function($scope, $mdToast,arquivoService,$http, alunoService, $stateParams) {

	$scope.myImage=null;

	$scope.aluno={};

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

			$scope.myImage=patCanvas.toDataURL();

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

	/**
	* This function could be used to send the image data
	* to a backend server that expects base64 encoded images.
	*
	* In this example, we simply store it in the scope for display.
	*/
	var sendSnapshotToServer = function sendSnapshotToServer(imgBase64) {

		$scope.snapshotData = imgBase64;

		// Conversão para arquivo da imagem capturada.
		var blob = dataURItoBlob(imgBase64);

		var file = new File([blob], 'fileName.jpeg', {type: "'image/jpeg"});

		var img = new Image(file);

		previewFile(file);

		console.log(file);
		console.log(img);
	};

	var handleFiles = function handleFiles(img) {

		var ctx = document.getElementById('canvas').getContext('2d');

		img.onload = function() {
			ctx.drawImage(img, 20, 20);
		}
	}

	var previewFile = function previewFile(file) {
		var preview = document.getElementById('teste');
		var reader  = new FileReader();

		reader.onloadend = function () {
			console.log("onloadend");
			preview.src = reader.result;
		}

		if (file) {
			reader.readAsDataURL(file);
			preview.src = reader.result;
		} else {
			preview.src = "";
		}
	}

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

	$scope.enviar = function(img){

		var typeFile = {
			id: 1
		};

		arquivoService.upload(img,typeFile)
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
	}

	function carregamentoInicial() {

		var _matricula = $stateParams.matricula;

		alunoService.buscaAlunoPorMatricula(_matricula)
		.success(function (data, status) {
			$scope.aluno = data;
		})
		.error(onErrorCallback);
	}


	carregamentoInicial();
});
