angular.module('NutrifApp').controller('webcamCtrl', function($scope, $mdToast) {
	
	$scope.myImage='';
      
	  
	$scope.myChannel = {
    // the fields below are all optional
    videoHeight: 400,
    videoWidth: 600,
    video: null // Will reference the video element on success
    };
    var _video = null;
	
    $scope.patData = null;
	
	$scope.teste=null;
	
	$scope.snapshotData = null;

	$scope.myCroppedImage='';

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
			
			var teste = patCanvas.toDataURL();
			
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
    };
	
			 
  

    
});