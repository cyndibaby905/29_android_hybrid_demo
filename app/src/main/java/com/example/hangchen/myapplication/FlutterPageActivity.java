package com.example.hangchen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.util.HashMap;
import java.util.Map;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;


public class FlutterPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        FlutterView flutterView = Flutter.createView(this,getLifecycle(),"defaultRoute");
        //注册方法通道
        new MethodChannel(flutterView, "samples.chenhang/navigation").setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                        //如果方法名为打开新页面
                        if(call.method.equals("openNativePage")) {
                            //新建Intent，打开原生页面
                            Intent intent = new Intent(FlutterPageActivity.this, SomeNativePageActivity.class);
                            startActivity(intent);
                            result.success(0);
                        }
                        //如果方法名为关闭Flutter页面
                        else if(call.method.equals("closeFlutterPage")) {
                            //销毁自身(Flutter容器)
                            finish();
                            result.success(0);
                        }
                        else {
                            //方法未实现
                            result.notImplemented();
                        }
                    }
                });
        setContentView(flutterView);
    }

}
