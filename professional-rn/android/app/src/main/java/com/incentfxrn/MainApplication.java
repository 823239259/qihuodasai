package com.incentfxrn;

import com.bugsnag.BugsnagReactNative;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.github.wuxudong.rncharts.MPAndroidChartPackage;
import com.oblador.vectoricons.VectorIconsPackage;
import com.reactnativenavigation.NavigationApplication;

import java.util.Arrays;
import java.util.List;

// 原本react-native啟動方式
//public class MainApplication extends Application implements ReactApplication {
//
//  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
//    @Override
//    public boolean getUseDeveloperSupport() {
//      return BuildConfig.DEBUG;
//    }
//
//    @Override
//    protected List<ReactPackage> getPackages() {
//      return Arrays.<ReactPackage>asList(
//          new MainReactPackage(),
//      );
//    }
//
//    @Override
//    protected String getJSMainModuleName() {
//      return "index";
//    }
//  };
//
//  @Override
//  public ReactNativeHost getReactNativeHost() {
//    return mReactNativeHost;
//  }
//
//  @Override
//  public void onCreate() {
//    super.onCreate();
//    SoLoader.init(this, /* native exopackage */ false);
//  }
//}
// react-native-navigation啟動方式
public class MainApplication extends NavigationApplication {

    @Override
    public boolean isDebug() {
        // Make sure you are using BuildConfig from your own application
        return BuildConfig.DEBUG;
    }

    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                BugsnagReactNative.getPackage(),
                new MPAndroidChartPackage(),
                new VectorIconsPackage()
        );
    }

    @Override
    public List<ReactPackage> createAdditionalReactPackages() {
        return getPackages();
    }
    @Override
    public String getJSMainModuleName() {
        return "index";
    }
}
