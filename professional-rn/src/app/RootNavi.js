// to store the root navigate for pop from drawr to tab.  https://github.com/wix/react-native-navigation/issues/1143
let _navigator;

function setRootNavi(navigatorRef) {
  _navigator = navigatorRef;
}

function popToRoot() {
  if (!_navigator) {
    return;
  }
  _navigator.popToRoot({
    animated: true,
    animationType: 'fade',
  });
}

function switchToTab(index) {
  if (!_navigator) {
    return;
  }
  _navigator.switchToTab({
    tabIndex:index,
  });
}

// add other navigation functions that you need and export them

export default {
  setRootNavi,
  popToRoot,
  switchToTab,
};