//  cordova-plugin-music-controller
//  Copyright © 2015 filfat Studios AB
//  Repo: https://github.com/filfat-Studios-AB/cordova-plugin-music-controller
module.exports = {
	updateCallback : function(){},
    create: function(data, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, 'MusicController', 'create', [data]);
    },
    destroy: function(successCallback,errorCallback){
        cordova.exec(successCallback, errorCallback, 'MusicController', 'destroy', []);
    },

	// Register callback
    subscribe: function(onUpdate){
		module.exports.updateCallback = onUpdate;
    },
	// Start listening for events
	listen : function(){
        cordova.exec(module.exports.receiveCallbackFromNative, function(res){ }, 'MusicController', 'watch', []);
	},
	receiveCallbackFromNative : function(messageFromNative){
		module.exports.updateCallback(messageFromNative);
        cordova.exec(module.exports.receiveCallbackFromNative, function(res){ }, 'MusicController', 'watch', []);
	}
};
