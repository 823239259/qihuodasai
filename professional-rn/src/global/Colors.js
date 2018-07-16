import Color from 'color';

const tintColor = '#2f95dc';
const highlightColor = '#5e8fb2';

const bg = Color('#111720');
const black = Color('black');
const white = Color('white');
const greenText = 'rgb(89, 165, 87)';
const greyText = 'rgb(132, 143, 161)';
const redText = 'rgb(216, 92, 97)';

export default {
  tintColor,
  tabIconDefault: 'rgb(138, 138, 138)', //'#888'
  tabIconSelected: highlightColor, //tintColor
  tabBar: '#fefefe',
  errorBackground: 'red',
  errorText: '#fff',
  warningBackground: '#EAEB5E',
  warningText: '#666804',
  noticeBackground: tintColor,
  noticeText: '#fff',

  
  bg, // 深藍 rgb(6, 35, 65)',
  tabBarBackgroundColor: 'rgb(27, 29, 34)',
  tabBarButtonColor: 'rgb(171, 178, 191)',
  tabBarSelectedButtonColor: 'rgb(100, 145, 232)',


  red: 'rgb(236, 82, 82)',
  redText,
  green: 'rgb(75, 166, 74)',
  greenText,
  // bootstrap colors: https://getbootstrap.com/docs/4.0/components/alerts/
  primary: 'rgb(196, 225, 255)',
  secondary: 'rgb(227, 228, 231)',
  success: 'rgb(205, 235, 212)',
  danger: 'rgb(248, 209, 212)',
  warning: 'rgb(255, 242, 196)',
  info: 'rgb(202, 233, 239)',
  
  grey: 'grey',
  greyText,
  white,
  black: 'black',
  blackBorder: 'rgb(19, 21, 30)',
  // TabBarDropdown
  tabBarDropdownActiveTextColor: 'rgb(73, 121, 211)',
  tabBarDropdownInactiveTextColor: 'rgb(166, 172, 192)',
  // TabBarTrade
  tabBarTradeBackgroundColor: 'rgb(27, 36, 51)',
  tabBarTradeActiveTextColor: 'rgb(54, 108, 181)',
  tabBarTradeInactiveTextColor: greyText,
  // Lightening
  lighteningLineColor: 'rgb(112, 155, 227)',
  // barchart
  barColor: 'rgb(235, 240, 105)', //'rgba(242, 159, 69, 0.6)',
  candlestickIncreasingColor: 'rgb(235, 82, 83)',
  candlestickDecreasingColor: 'rgb(76, 166, 74)',
  // kchart
  ma: {
    fiveColor: 'rgb(47, 126, 170)',
    tenColor: 'rgb(170, 48, 170)',
    twentyColor: 'rgb(170, 104, 48)',
    thirtyColor: 'rgb(169, 170, 48)'
  },
  dropdownBackgroundColor: bg.lighten(0.2),
  dropdownHightlightBackgroundColor: bg.lighten(0.5),

  modalBackgroundColor: black.alpha(0.1),

  separatorBackgroundColor: 'rgb(25, 28, 36)',
  separatorBorderColor: 'rgb(19, 22, 30)',

  markerBackgroundColor: bg.lighten(0.6).alpha(0.8),
  // dialog
  dialogBackgroundColor: 'rgb(211, 211, 211)',
  dialogBorderColor: 'rgb(174, 174, 174)',
  dialogButtonTextColor: 'rgb(44, 87, 151)',
  // picker
  pickerDoneContainerColor: 'rgb(247, 247, 247)',
  // doneButton
  doneButtonBackgroundColor: '#D2D5DB',
  doneButtonTextColor: '#497df7',
  
  getColorText: (value) => {
    let color = greyText;
    if (value < 0) {
        color = greenText;
    } else if (value > 0) {
        color = redText;
    }
    return color;
  }
};
