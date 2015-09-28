# cordova-plugin-music-controller
Interactive multimedia controls. Based on https://github.com/homerours/cordova-music-controls-plugin

## Platforms
- Android (4.1+)
- iOS (under development)
- Windows (10+)

## Installation
```
cordova plugin add https://github.com/filfat-Studios-AB/cordova-plugin-music-controller
cordova prepare
```


## Methods
Create the media controller:
```javascript
MusicController.create({
    track: 'Speak Now',				//Requierd
	artist: 'Taylor Swift',			//Required
    cover: 'albums/speak-now.jpg',	//Required
	album: 'Speak Now',				//Optional, Only visible on Android
    isPlaying: true					//Required
}, onSuccess, onError);
```

Destroy the media controller:
```javascript
MusicController.destroy(onSuccess, onError);
```

Subscribe to the media controller events:
```javascript
function events(action) {
	switch(action){
		case 'music-controller-next':
			//Do something
			break;
		case 'music-controller-previous':
			//Do something
			break;
		case 'music-controller-pause':
			//Do something
			break;
		case 'music-controller-play':
			//Do something
			break;

		//Headset events
		case 'music-controller-headset-unplugged':
			//Do something
			break;
		case 'music-controller-headset-plugged':
			//Do something
			break;
		case 'music-controller-headset-button':
			//Do something
			break;
		default:
			break;
	}
}
//Register callback
MusicController.subscribe(events);

//Start listening for events
MusicController.listen();
```

Feature detect to avoid crash or errors on unsupported platforms:
```javascript
document.addEventListener("deviceready", function () {
	if(typeof MusicController !== 'undefined'){
		//It's safe to use MusicController here	
	}
}, function(){}, function(){});
```

## Quirks
* Cordova 5.0 or higher is required for Windows support.
* Android is qurrently the only platform that supports the headset events.
* Windows currently only supports locally stored covers.
* Windows Mobile is currently not support but the feature detection will still pass and *might* cause instabillity.
* This plugin is still under development which means that it's not yet "production ready".


## Screenshots
![Android](http://i.imgur.com/Qe1a8ZJ.png)
![Windows](http://i.imgur.com/Y4HsM0s.png)
