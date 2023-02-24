package com.example.reproduce_pictureinpicture_121183

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.os.Build
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private lateinit var mContext: Context
    private lateinit var mPictureInPictureParamsBuilder: PictureInPictureParams.Builder

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        flutterEngine
            .platformViewsController
            .registry
            .registerViewFactory("<platform-view-type>", NativeViewFactory())

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "<platform-view-type>").setMethodCallHandler {
                call, result ->
            when (call.method) {
                "doPIPDebug" -> {
                    mContext = context

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mPictureInPictureParamsBuilder = PictureInPictureParams.Builder()
                        (mContext as Activity).enterPictureInPictureMode(mPictureInPictureParamsBuilder.build())
                    }

                    result.success(true)
                }
            }
        }
    }
}