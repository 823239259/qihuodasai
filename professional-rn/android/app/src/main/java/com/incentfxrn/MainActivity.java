package com.incentfxrn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.react.ReactActivity;
import com.reactnativenavigation.controllers.SplashActivity;
//原本react-native啟動方式
//public class MainActivity extends ReactActivity {
//
//    /**
//     * Returns the name of the main component registered from JavaScript.
//     * This is used to schedule rendering of the component.
//     */
//    @Override
//    protected String getMainComponentName() {
//        return "incentfxRN";
//    }
//}
// react-native-navigation啟動方式
public class MainActivity extends SplashActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);
    }
}
