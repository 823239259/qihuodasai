import { Colors } from '../global';

export default {
    appStyle: {
        keepStyleAcrossPush: false, // 讓iOS跟Android一致 https://wix.github.io/react-native-navigation/#/styling-the-navigator?id=disabling-persistent-styling-properties-on-ios
    },
    tabsStyle: { // optional, **iOS Only** add this if you want to style the tab bar beyond the defaults
        tabBarButtonColor: Colors.tabBarButtonColor, // change the color of the tab icons and text (also unselected)
        tabBarSelectedButtonColor: Colors.tabBarSelectedButtonColor, // change the color of the selected tab icon and text (only selected)
        tabBarBackgroundColor: Colors.tabBarBackgroundColor, // change the background color of the tab bar
        tabBarTranslucent: true, // necessarily!!!! change the translucent of the tab bar to false
        tabBarHideShadow: false
    },
};

