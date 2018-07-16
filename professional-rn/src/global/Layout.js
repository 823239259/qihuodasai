/**
 * 影響整個畫面Layout
 * 1. fontSize大小
 * 2. margin
 * 3. padding
 * 4. lineHeight
 * 
 * 定義三個Size，根據
 * 1.small:  iphone5/SE    => 568 規範 500 <= screenHeight < 650 
 * 2.medium: iphone6/7     => 667 規範 650 <= screenHeight < 700 
 * 3.large:  iphone7plus/X => 736 規範 700 <= screenHeight < 768
 * 4.pad:    ipad          => 768 測試起來 ipad 9.7 / 10.5 / 12.9 都顯示 768
 */
import { Dimensions, Platform, StatusBar } from 'react-native';
import Enum from './Enum';

const screenWidth = Dimensions.get('window').width;
const screenHeight = Dimensions.get('window').height;
/*
* 定義三個Size，根據
* 1.small: iphone5/SE     => 568 規範 500 <= screenHeight < 650 
* 2.medium: iphone6/7     => 667 規範 650 <= screenHeight < 700 
* 3.large: iphone7plus/X  => 736, 812 規範 700 <= screenHeight < 768
*/
let deviceSize = Enum.deviceSize.medium;
if (screenHeight < 650) {
    deviceSize = Enum.deviceSize.small;
} else if (screenHeight < 700) {
    deviceSize = Enum.deviceSize.medium;
} else if (screenHeight < 768) {
    deviceSize = Enum.deviceSize.large;
} else {
    deviceSize = Enum.deviceSize.pad;
    if (screenHeight === 812) { // iphoneX
        deviceSize = Enum.deviceSize.large;
    }
}

const inset = 10;
let bottomInset = 0;
let isIphoneX = false;
if (Platform.OS === 'ios' && screenHeight === 812) {
    isIphoneX = true;
    bottomInset = 34;
}

let tabBarHeight = 49;
if (Platform.OS === 'android') {
    tabBarHeight = 56;
}

let statusBarHeight = 20;
if (Platform.OS === 'android') {
    statusBarHeight = StatusBar.currentHeight;
} else {
    if (isIphoneX) {
        statusBarHeight = 44;
    }
}

let navBarHeight = 44;
if (Platform.OS === 'android') {
    navBarHeight = 80 - statusBarHeight;
}
const upperHeight = statusBarHeight + navBarHeight;
const bottomHeight = tabBarHeight + bottomInset;

const center = { justifyContent: 'center', alignItems: 'center' };
const fontNormal = { fontSize: 16, fontWeight: '400' };
const fontBold = { fontSize: 18, fontWeight: '500' };
const fontBolder = { fontSize: 28, fontWeight: '600' };

export default {
    screenWidth,
    screenHeight,
    center,
    // column
    columPadding: 5,
    // font
    fontNormal,
    fontBold,
    fontBolder,
    
    deviceSize,
    inset,
    iphoneXPaddingButton: 34,
    isIphoneX,
    // 原生預設的 iOS TabBar高度 https://stackoverflow.com/questions/25770047/ios-8-ipad-tab-bar-height-is-different-than-ios-7
    // Android是56 https://material.io/guidelines/components/bottom-navigation.html
    tabBarHeight, // ios: 49, android: 56,
    statusBarHeight,  // iphone: 20, iphoneX: 44, android: StatusBar.currentHeight
    // https://stackoverflow.com/questions/35436643/how-to-find-height-of-status-bar-in-android-through-react-native 
    navBarHeight,       // iphone: 36, iphoneX: 44 包含StatubBarHeight，動態算出 iOS: 56, android: 80
    // 因為iphoneX 在加入 upperHeight & bottomHeight
    upperHeight,  // statusBar + navbarHeight
    bottomHeight, // iphoneX: tabBarHeight + BottomInset
    contentHeight: null,    // iphone: 623, iphoneX: 641 cal動態找出: quotationScreen.layout.height
    contentWithBottomHeight: null,

    
    buttonHeight: 40,
    buttonLargeHeight: 50,
    doneButtonHeight: 40,
    // dialog
    dialogHeight: 200,
    dialogWidth: screenWidth * 0.67,
    dialogContentPadding: (dz => {
        let size = null;
        if (dz === Enum.deviceSize.small) {
            size = 12;
        } else if (dz === Enum.deviceSize.medium) {
            size = 14;
        } else if (dz === Enum.deviceSize.large) {
            size = 14;
        } else if (dz === Enum.deviceSize.pad) {
            size = 20;
        }
        return size;
    })(deviceSize),
    dialogContentFontSize: (dz => {
        let size = null;
        if (dz === Enum.deviceSize.small) {
            size = 12;
        } else if (dz === Enum.deviceSize.medium) {
            size = 14;
        } else if (dz === Enum.deviceSize.large) {
            size = 14;
        } else if (dz === Enum.deviceSize.pad) {
            size = 16;
        }
        return size;
    })(deviceSize),
    // TabBarModal
    tabBarModalWidth: screenWidth - 20,
    tabBarButtonSize: 18,
    // quotationDetailHeader - 因為左邊的back button 和 右邊的bar button size不同，沒辦法透過center排在正中間，需要一些調整
    quotationDetailHeaderMargin: 20,
    quotationDetailHeaderFontSize: deviceSize === Enum.deviceSize.small ? 12 : 14,
    // quotation
    quotationHeaderPaddingHorizontal: 15,
    quotationHeaderHeight: 50,

    quotationContentPaddingHorizontal: inset,
    quotationContentHeight: 50,

    quotaitonGridHeight: 120,

    quotationBorderWidth: 0.5,
    quotationAdHeight: 30,

    // handicap
    handicapHeaderTextStyle: { 
        fontSize: (dz => {
            let size = null;
            if (dz === Enum.deviceSize.small) {
                size = 14;
            } else if (dz === Enum.deviceSize.medium) {
                size = 16;
            } else if (dz === Enum.deviceSize.large) {
                size = 16;
            } else if (dz === Enum.deviceSize.pad) {
                size = 20;
            }
            return size;
        })(deviceSize),
        fontWeight: (dz => {
            let size = null;
            if (dz === Enum.deviceSize.small) {
                size = '400';
            } else if (dz === Enum.deviceSize.medium) {
                size = '500';
            } else if (dz === Enum.deviceSize.large) {
                size = '500';
            } else if (dz === Enum.deviceSize.pad) {
                size = '600';
            }
            return size;
        })(deviceSize),
        margin: (dz => {
            let size = null;
            if (dz === Enum.deviceSize.small) {
                size = 5;
            } else if (dz === Enum.deviceSize.medium) {
                size = 10;
            } else if (dz === Enum.deviceSize.large) {
                size = 10;
            } else if (dz === Enum.deviceSize.pad) {
                size = 10;
            }
            return size;
        })(deviceSize),
    },
    handicapRowMarginLeft: deviceSize === Enum.deviceSize.small ? 10 : 20,
    handicapRowStyle: { flexDirection: 'row', borderWidth: 1, borderBottomWidth: 0 },
    handicapRowBottomStyle: { borderBottomWidth: 1 },
    handicapContentTextStyle: { 
        fontSize: (dz => {
            let size = null;
            if (dz === Enum.deviceSize.small) {
                size = 12;
            } else if (dz === Enum.deviceSize.medium) {
                size = 14;
            } else if (dz === Enum.deviceSize.large) {
                size = 14;
            } else if (dz === Enum.deviceSize.pad) {
                size = 16;
            }
            return size;
        })(deviceSize),
        fontWeight: (dz => {
            let size = null;
            if (dz === Enum.deviceSize.small) {
                size = '300';
            } else if (dz === Enum.deviceSize.medium) {
                size = '400';
            } else if (dz === Enum.deviceSize.large) {
                size = '400';
            } else if (dz === Enum.deviceSize.pad) {
                size = '500';
            }
            return size;
        })(deviceSize),
        lineHeight: (dz => {
            let size = null;
            if (dz === Enum.deviceSize.small) {
                size = 28;
            } else if (dz === Enum.deviceSize.medium) {
                size = 32;
            } else if (dz === Enum.deviceSize.large) {
                size = 32;
            } else if (dz === Enum.deviceSize.pad) {
                size = 34;
            }
            return size;
        })(deviceSize),
    },
    // TradeLastView
    tradeLastHeight: (dz => {
        let size = null;
        if (dz === Enum.deviceSize.small) {
            size = 100;
        } else if (dz === Enum.deviceSize.medium) {
            size = 120;
        } else if (dz === Enum.deviceSize.large) {
            size = 120;
        } else if (dz === Enum.deviceSize.pad) {
            size = 120;
        }
        return size;
    })(deviceSize),
    tradeLastTextStyle: (dz => {
        if (dz === Enum.deviceSize.small) {
            return { fontSize: 14, fontWeight: '300' };
        } else if (dz === Enum.deviceSize.medium) {
            return { fontSize: 16, fontWeight: '400' };
        } else if (dz === Enum.deviceSize.large) {
            return { fontSize: 16, fontWeight: '400' };
        } else if (dz === Enum.deviceSize.pad) {
            return { fontSize: 18, fontWeight: '500' };
        }
    })(deviceSize),
    tradeLastRightHeaderStyle: (dz => {
        if (dz === Enum.deviceSize.small) {
            return { fontSize: 24, fontWeight: '400' };
        } else if (dz === Enum.deviceSize.medium) {
            return { fontSize: 26, fontWeight: '600' };
        } else if (dz === Enum.deviceSize.large) {
            return { fontSize: 26, fontWeight: '600' };
        } else if (dz === Enum.deviceSize.pad) {
            return { fontSize: 32, fontWeight: '700' };
        }
    })(deviceSize),
    // TabBarDropdown
    tabBarDropdownHeight: 50,
    tabBarDropdownActiveBorderBottomSize: 3,   // 設定: 2, 3, 4
    tabBarDropdownActiveBorderBottomHeight: 3, // tab捲動條 厚度: 3, 4
    tabBarDropdownFontSize: deviceSize === Enum.deviceSize.small ? 14 : 16,
    // TabBarTrade
    tabBarTradeHeight: 40,
    tabBarTradeFontSize: 16,
    // Accordion
    accordionColumnHeight: 40,
    accordionButtonHeight: 30,
    accordionButtonWidth: 80,
    // chart
    markerWidth: 120,
    displayWidth: 60,
    displayHeight: 30,
    highlightIndicatorWidth: 1,
    // tradeNum,
    tradeNumLabelWidth: 100,
    // tradeView
    tradeViewInfoBox: { flexDirection: 'row', padding: 10 },
    tradeViewRow: { height: 25, flexDirection: 'row' },
    tradeViewFontSize: (dz => {
        let size = null;
        if (dz === Enum.deviceSize.small) {
            size = 12;
        } else if (dz === Enum.deviceSize.medium) {
            size = 14;
        } else if (dz === Enum.deviceSize.large) {
            size = 14;
        } else if (dz === Enum.deviceSize.pad) {
            size = 16;
        }
        return size;
    })(deviceSize),
    // field
    fieldGetVerificationCodeButtonPadding: deviceSize === Enum.deviceSize.small ? 8 : 10,
    fieldGetVerificationCodeButtonFontSize: deviceSize === Enum.deviceSize.small ? 12 : 16,
    fieldMargin: 20,
    fieldErrorHeight: 20,
    fieldHorizontalMargin: inset,
    // radio
    radioLabelFontSize: 16,
    radioMargin: 10,
    historyTradeRadioFontSize: 12,
    // account
    accountContainerPadding: inset,
    // tradeList
    cardHeight: 130,
    cardMargin: 10,
    applyTradeChooseDepositMargin: 5,
    applyTradeChooseDepositInfoRowPadding: 10,
    // information
    informationHeight: 150,
    informationFontSize: deviceSize === Enum.deviceSize.small ? 12 : 14,
    informationTextLength: 100
};
