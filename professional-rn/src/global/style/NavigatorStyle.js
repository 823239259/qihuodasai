import Config from '../Config';
import c from '../Colors';
import Rhino from '../theme/Rhino';
import DarkBlue from '../theme/DarkBlue';

let Colors = c;
if (Config.theme === 'Rhino') {
    Colors = Rhino;
} else if (Config.theme === 'DarkBlue') {
    Colors = DarkBlue;
}
// https://wix.github.io/react-native-navigation/#/styling-the-navigator?id=styling-the-statusbar
const navigatorStyle = {
    // RootView background
    screenBackgroundColor: Colors.screenBackgroundColor,
    // navBar setting
    navBarTextColor: Colors.navBarTextColor,
    navBarBackgroundColor: Colors.navBarBackgroundColor,
    navBarButtonColor: Colors.navBarButtonColor,
    // statusBar
    statusBarTextColorScheme: 'light', // dark, light - 修改statusBar字 成白色
};

const modalStyle = {
    ...navigatorStyle
};
const screenInnerStyle = {
    ...navigatorStyle,
    tabBarHidden: true
};
const tabBasedStyle = {
    ...navigatorStyle
};

export default {
    navigatorStyle,
    modalStyle,
    screenInnerStyle,
    tabBasedStyle
};
