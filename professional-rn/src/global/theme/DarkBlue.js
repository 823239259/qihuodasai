import Color from 'color';

const bg = Color('rgb(22, 29, 38)');
const bgDarker = 'rgb(28, 39, 57)';
const black = Color('black');
const grey = 'grey';
const tabBarBackgroundColor = 'rgb(28, 37, 52)';
const iconButtonColor = 'rgb(139, 139, 139)';
const navBarBackgroundColor = 'rgb(34, 45, 62)';
const greenText = 'rgb(48, 144, 91)';
const greyText = Color('rgb(152, 152, 152)');
const greyIcon = 'rgb(58, 71, 96)';
const red = 'rgb(188, 66, 95)';
const redText = 'rgb(188, 66, 95)';
const white = 'white';
const tradeViewInfoBoxBackgroundColor = 'rgb(19,27,37)';
const activeColor = 'rgb(73, 121, 211)';
const lightBlue = 'rgb(172, 204, 251)';
const darkBlue = 'rgb(50, 97, 161)';
const boxBlue = 'rgb(56, 71, 96)';
const boxActiveBlue = 'rgb(50, 97, 161)';
const tabBarFieldBackgroundColor = Color('rgb(28,39,57)');
const contentBackgroundColor = 'rgb(22, 31, 43)';
const borderBlueColor = 'rgb(53, 105, 178)';
const boxShadowBottom = {
  borderWidth: 1,
  // borderRadius: '#000',
  borderColor: '#000',
  // borderBottomWidth: 0,
  shadowColor: '#000',
  shadowOffset: { width: 0, height: 3 },
  shadowOpacity: 0.8,
  shadowRadius: 2,
  elevation: 1
};
const titleTextColor = 'rgb(105, 121, 142)';
const yellow = 'rgb(235, 240, 105)';

export default {
  activeColor,
  lightBlue,
  darkBlue,
  boxBlue,
  boxActiveBlue,
  lightTextColor: lightBlue,
  iconButtonColor,
  red,
  redText,
  green: 'rgb(66, 147, 103)',
  greenText,
  yellow,
  // bootstrap colors: https://getbootstrap.com/docs/4.0/components/alerts/
  primary: 'rgb(196, 225, 255)',
  secondary: 'rgb(227, 228, 231)',
  success: 'rgb(205, 235, 212)',
  danger: 'rgb(248, 209, 212)',
  warning: 'rgb(255, 242, 196)',
  info: 'rgb(202, 233, 239)',
  
  grey,
  greyText: greyText.string(),
  greyTextLight: greyText.alpha(0.3),
  greyIcon,
  white,
  black: 'black',
  blackBorder: 'rgb(19, 21, 30)',
  greyBorder: grey,
  titleTextColor,
  bg,
  bgDarker,
  // RootView background
  screenBackgroundColor: bg,
  // TabBasedApp
  tabBarBackgroundColor,
  tabBarButtonColor: iconButtonColor,
  tabBarSelectedButtonColor: 'rgb(156, 197, 255)',
  // navBar setting
  navBarTextColor: 'rgb(94, 110, 131)', 
  navBarBackgroundColor,
  navBarButtonColor: iconButtonColor,
  // drawerTrade
  drawerTradeBackgroundColor: bg,
  drawButtonColor: boxBlue,
  // quotation 行情
  quotationHeaderBackgroundColor: 'rgb(18, 23, 29)',
  quotationHeaderFirstColumnTextColor: white,
  quotationHeaderColumnTextColor: greyText,
  quotationContentBackgroundColor: contentBackgroundColor,
  quotationGreyColor: 'rgb(141, 141, 141)',
  // handicap
  handicapTextColor: greyText,
  handicapRowBottomColor: 'rgb(23, 35, 54)',
  // tradeLastView
  tradeLastViewContentTextColor: greyText,
  tradeLastViewContentValueTextColor: white,
  // TabBarDropdown
  tabBarDropdownBackgroundColor: 'rgb(18, 23, 31)',
  tabBarDropdownActiveTextColor: white,
  tabSliderColor: activeColor,
  tabBarDropdownInactiveTextColor: 'rgb(166, 172, 192)',
  kCaretColor: 'rgb(64, 106, 173)',
  // TabBarTrade
  tabBarTradeBackgroundColor: 'rgb(27, 36, 51)',
  tabBarTradeActiveTextColor: 'rgb(54, 108, 181)',
  tabBarTradeInactiveTextColor: greyText.string(),
  // Accordion
  accordionItemBackgroundColor: contentBackgroundColor,
  accordionItemActiveBackgroundColor: 'rgb(16, 45, 94)',
  accordionButtonColor: boxBlue,
  accordionFooterColor: '#111720',
  // chart
  chartBackgroundColor: 'rgb(14, 12, 12)',
  chartBorderColor: 'rgb(28, 25, 24)',
  // highlightIndicator
  highlightColor: 'grey',
  unHighlightColor: 'transparent',
  // Lightening
  lighteningLineColor: 'rgb(112, 155, 227)',
  // barchart
  barColor: yellow,
  candlestickIncreasingColor: 'rgb(235, 82, 83)',
  candlestickDecreasingColor: 'rgb(76, 166, 74)',
  // tradeNum
  tradeNumLabelColor: greyText,
  // tradeView
  tradeViewInfoBoxBackgroundColor,
  tradeViewInfoBox: { 
    backgroundColor: tradeViewInfoBoxBackgroundColor,
    ...boxShadowBottom
  },
  tradeViewInfoAccountTextColor: lightBlue,
  // radio
  radioActiveColor: activeColor,
  radioNonActiveColor: 'rgb(44, 44, 44)',
  // tradeTextInput - 价格
  tradeTextInputBackgroundColor: 'rgb(51, 65, 87)',
  tradeTextInputPriceBackgroundColor: activeColor,
  // kchart
  ma: {
    fiveColor: 'rgb(47, 126, 170)',
    tenColor: 'rgb(170, 48, 170)',
    twentyColor: 'rgb(170, 104, 48)',
    thirtyColor: 'rgb(169, 170, 48)'
  },
  dropdownBackgroundColor: bg.lighten(0.2),
  dropdownHightlightBackgroundColor: bg.lighten(0.5),

  modalBackgroundColor: black.alpha(0.4),

  separatorBackgroundColor: 'rgb(25, 28, 36)',
  separatorBorderColor: 'rgb(19, 22, 30)',

  markerBackgroundColor: bg.lighten(0.6).alpha(0.8),
  // dialog
  dialogBackgroundColor: 'rgb(211, 211, 211)',
  dialogBorderColor: 'rgb(174, 174, 174)',
  dialogButtonTextColor: 'rgb(44, 87, 151)',
  // tabBarModal
  tabBarModalBackgroundColor: boxBlue,
  tabBarModalBorderColor: black,
  tabBarButtonTextColor: white,
  tabBarFieldBackgroundColor,
  tabBarFieldDisableBackgroundColor: tabBarFieldBackgroundColor.lighten(0.6).alpha(0.8),
  // quotationDetailHeader
  quotationDetailHeaderTextColor: 'rgb(108, 121, 140)',
  quotationDetailHeaderBackgroundColor: bgDarker,
  // picker
  pickerDoneContainerColor: 'rgb(247, 247, 247)',
  // doneButton
  doneButtonBackgroundColor: '#D2D5DB',
  doneButtonTextColor: '#497df7',
  // modal
  formTextInputBorderColor: borderBlueColor,
  // submit
  submitActiveBackgroundCoor: red,
  submitBackgroundColor: 'gray',
  // account 
  accountBalance: {
    backgroundColor: tradeViewInfoBoxBackgroundColor,
    borderTopWidth: 0,
    borderRightWidth: 0,
    borderLeftWidth: 0,
    ...boxShadowBottom
  },
  // fund
  fundRowBackgroundColor: contentBackgroundColor,
  // applyTrade
  cardBackgroundColor: 'rgb(27, 36, 51)',
  applyTradeAccordionColor: tabBarBackgroundColor,
  // ButtonCommon
  buttonCommonTinyOnTheRightTextColor: 'rgb(166, 204, 255)',
  buttonCommonTinyOnTheRightBackgroundColor: 'rgb(49, 62, 85)',
  // information
  informationSeparator: 'rgb(52, 103, 175)',
  // historyTrade
  historyTrade: {
    radioBackgroundColor: 'rgb(18, 23, 29)',
  },
  connectionStatus: {
    backgroundColor: 'rgb(0, 31, 58)',
    textColor: white,
    inactiveTextColor: 'rgba(255, 255, 255, 0.5)'
  },
  getColorText: (value) => {
    let color = greyText.string();
    if (value < 0) {
        color = greenText;
    } else if (value > 0) {
        color = redText;
    }
    return color;
  }
};
