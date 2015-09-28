//  cordova-plugin-music-controller
//  Copyright © 2015 filfat Studios AB
//  Repo: https://github.com/filfat-Studios-AB/cordova-plugin-music-controller
/* global Windows, cordova */
var mc;
var onUpdate = function (event) { };

var onKey = function (event) {
    var Button = Windows.Media.SystemMediaTransportControlsButton;
    switch (event.button) {
        case Button.play:
            onUpdate('music-controller-play');
            break;
        case Button.pause:
            onUpdate('music-controller-pause');
            break;
        case Button.stop:
            onUpdate('music-controller-stop');
            break;
        case Button.next:
            onUpdate('music-controller-next');
            break;
        case Button.previous:
            onUpdate('music-controller-previous');
            break;
    }
};

cordova.commandProxy.add("MusicController",{
    create: function (successCallback, errorCallback, datas) {
        var data = datas[0];
        mc = Windows.Media.SystemMediaTransportControls.getForCurrentView();

        //Handle events
        mc.addEventListener("buttonpressed", onKey, false);

        //Set data
        mc.isEnabled = true;
        mc.isPlayEnabled = true;
        mc.isPauseEnabled = true;
        mc.isNextEnabled = true;
        mc.isStopEnabled = true;
        mc.isPreviousEnabled = true;
        mc.displayUpdater.type = Windows.Media.MediaPlaybackType.music;

        //Is Playing
        if (data.isPlaying)
            mc.playbackStatus = Windows.Media.MediaPlaybackStatus.playing;
        else
            mc.playbackStatus = Windows.Media.MediaPlaybackStatus.stopped;
        

		if (!/^(f|ht)tps?:\/\//i.test(data.cover)) {
		    var cover = new Windows.Foundation.Uri("ms-appdata://" + data.cover);
		    mc.displayUpdater.thumbnail = cover;
		} else {
		    //TODO: Store image locally
		}
		mc.displayUpdater.musicProperties.albumArtist = data.artist;
		mc.displayUpdater.musicProperties.albumTitle = data.album;
		mc.displayUpdater.musicProperties.artist = data.artist;
		mc.displayUpdater.musicProperties.title = data.track;
		//mc.displayUpdater.musicProperties.trackNumber = data.trackNumber;
		mc.displayUpdater.update();
    },
    destroy: function (successCallback, errorCallback, datas) {
        //Remove events
        mc.displayUpdater.clearAll();
    },
    watch: function (_onUpdate, errorCallback, datas) {
        //Set callback
	    onUpdate = _onUpdate;
    }
});