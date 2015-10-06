# cordova-plugin-media-controller
Interactive multimedia controls.

[cordova-plugin-media-controller on NPM](https://www.npmjs.com/package/cordova-plugin-media-controller)


## Platforms
| <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Android_robot.svg/511px-Android_robot.svg.png" width="48px" height="48px" alt="Android logo"> | <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/2000px-Apple_logo_black.svg.png" width="48px" height="48px" alt="iOS logo"> | <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/Windows_logo_-_2012.svg/2000px-Windows_logo_-_2012.svg.png" width="48px" height="48px" alt="Windows logo">
|:---:|:---:|:---:|
| 4.1+ ✔ | InDev ✘ | 10+ ✔ |

## Installation
```
cordova plugin add https://github.com/filfat-Studios-AB/cordova-plugin-music-controller
cordova prepare
```


## Methods
Create the media controller:
```javascript
MusicController.create({
    track: 'Shake It Off',			//Requierd
	artist: 'Taylor Swift',			//Required
    cover: 'albums/1989.jpg',		//Required
	album: '1989',					//Optional, Only visible on Android
    length: 219,					//Required, Only for iOS
    position: 146,					//Required, Only for iOS
	isPlaying: true,				//Required
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
* Android is currently the only platform that supports the headset events (Windows Mobile handles them for you).
* Windows currently only supports locally stored covers.
* Windows Mobile requires to have at least one audio player active before showing up.
* This plugin is still under development which means that it's not yet "production ready".


## Screenshots
![Android](http://i.imgur.com/Qe1a8ZJ.png)
![Windows](http://i.imgur.com/Y4HsM0s.png)
![Windows Mobile](http://i.imgur.com/FUEwyeF.png)


## License
See "LICENSE".
Based on https://github.com/homerours/cordova-music-controls-plugin
