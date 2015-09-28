//  cordova-plugin-media-controller
//  Copyright © 2015 filfat Studios AB
//  Repo: https://github.com/filfat-Studios-AB/cordova-plugin-music-controller
var exec = require('cordova/exec');

module.exports = {
	updateCallback : function(){},
    
    //Create
    create: function(data, successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'MusicController', 'create', [data]);
    },
    
    //Destroy
    destroy: function(successCallback,errorCallback){
        exec(successCallback, errorCallback, 'MusicController', 'destroy', []);
    },

	//Register callback
    subscribe: function(onUpdate){
		module.exports.updateCallback = onUpdate;
    },
    
	//Start listening for events
	listen : function(){
        exec(module.exports.receiveCallbackFromNative, function(res){ }, 'MusicController', 'watch', []);
	},
    
	receiveCallbackFromNative : function(messageFromNative){
		module.exports.updateCallback(messageFromNative);
        exec(module.exports.receiveCallbackFromNative, function(res){ }, 'MusicController', 'watch', []);
	}
};
