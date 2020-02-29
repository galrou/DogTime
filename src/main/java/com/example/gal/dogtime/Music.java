package com.example.gal.dogtime;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by gal on 15/06/2018.
 */

public class Music extends Service {
    MediaPlayer player;

    /**
     * this function binds the service with  an activity(here i dont bind with any activity)
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called by the system every time a client explicitly starts the service
     * here i picking the nusic i want to play and start it
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        player=MediaPlayer.create(this, R.raw.dog);
        player.setLooping(true);
        player.start();
        return START_STICKY;
    }

    /**
     * called by the system to notify a Service that it is no longer used and is being removed
     * here i stop the music
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
