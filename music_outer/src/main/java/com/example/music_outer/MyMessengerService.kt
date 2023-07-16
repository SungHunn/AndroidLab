package com.example.music_outer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.provider.MediaStore.Audio.Media
import java.lang.Exception

class MyMessengerService : Service() {

    lateinit var messenger : Messenger //액티비티에서 받는 용도
    lateinit var replyMessenger : Messenger // 액티비티에 전달하는 용도
    lateinit var player : MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    inner class IncomingHandler(
        context : Context,
        private val applicationContext : Context = context.applicationContext
    ) : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                10 ->{
                    replyMessenger = msg.replyTo
                    if(!player.isPlaying){
                        player = MediaPlayer.create(this@MyMessengerService, R.raw.music)
                        try {
                            val replyMsg = Message()
                            replyMsg.what = 10
                            val replyBundle = Bundle()
                            replyBundle.putInt("duration", player.duration)
                            replyMsg.obj = replyBundle
                            replyMessenger.send(replyMsg)

                            player.start()
                        }catch (e : Exception){
                            e.printStackTrace()
                        }
                    }

                }
                20 -> {
                    if(player.isPlaying){
                        player.stop()
                    }

                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        messenger = Messenger(IncomingHandler(this))
        return messenger.binder
    }
}