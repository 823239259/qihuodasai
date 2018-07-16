import React, { Component } from 'react';
import { StyleSheet, View, Text, ViewPropTypes, Animated } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import _ from 'lodash';
import Button from './Button';
import ButtonCaret from './ButtonCaret';
import ModalDropdown from '../modalDropdown';
import { Layout, Variables, Colors, Enum, Config } from '../../global';

@inject('QuotationSocket', 'QuotationDetailStore') @observer
export default class TabBarDropdown extends Component {
  static propTypes = {
    goToPage: PropTypes.func,
    activeTab: PropTypes.number,
    tabs: PropTypes.array,
    backgroundColor: PropTypes.string,
    activeTextColor: PropTypes.string,
    inactiveTextColor: PropTypes.string,
    textStyle: Text.propTypes.style,
    tabStyle: ViewPropTypes.style,
    renderTab: PropTypes.func,
    underlineStyle: ViewPropTypes.style,
    // dropdown
    dropdownPage: PropTypes.number,
    dropdownOptions: PropTypes.array,
    dropdownText: PropTypes.string,
    // modalPage
    modalPage: PropTypes.number,
    // confirm trade
    showModal: PropTypes.func,
    beforeGoTo: PropTypes.func,
    // modify 
    initialPage: PropTypes.number
  }

  static defaultProps = {
    activeTextColor: Colors.tabBarDropdownActiveTextColor,
    inactiveTextColor: Colors.tabBarDropdownInactiveTextColor,
    backgroundColor: Colors.tabBarDropdownBackgroundColor,
    textStyle: { fontSize: Layout.tabBarDropdownFontSize },
    // dropdown
    dropdownPage: -1,
    dropdownOptions: ['option 1', 'option 2', 'option 3'],
    dropdownText: 'text',
    // modalPage
    modalPage: -1
  }
  constructor(props) {
    super(props);
    this.state = {
      layoutDropdown: {}
    };
  }
  componentDidMount() {
      if (this.props.initialPage !== undefined) {
        setTimeout(() => {
          this.props.goToPage(this.props.initialPage);
        }, 0);
      }
  }
  // 查詢K線資料
  _requestKData(rowData) {
    const { QuotationSocket, QuotationDetailStore } = this.props;
    let kType;
    _.forOwn(Enum.kTypes, (v, k) => {
        if (rowData === v.text) {
          kType = v.value;
        }
    });
    QuotationDetailStore.currentKType = kType;
    QuotationSocket.sendHistoryMessage(kType);
  }
  // 點選Tab直接轉到那一頁
  renderTabButton(name, page, isTabActive, onGoToPageHandler) {
    const { activeTextColor, inactiveTextColor, textStyle, } = this.props;
    const textColor = isTabActive ? activeTextColor : inactiveTextColor;
    const fontWeight = isTabActive ? 'bold' : 'normal';
    return (
        <Button
        style={{ flex: 1 }}
        key={name}
        accessible={true}
        accessibilityLabel={name}
        accessibilityTraits='button'
        onPress={() => {
              this.props.beforeGoTo && this.props.beforeGoTo(page);
              onGoToPageHandler(page);
            }
          }
        >
            <View style={[styles.tab, this.props.tabStyle]}>
                <Text style={[{ color: textColor, fontWeight }, textStyle]}>
                    {name}
                </Text>
            </View>
        </Button>
    );
  }
  // 點選交易中心時，得先判斷是否有登入
  renderTabModal(name, page, isTabActive, onGoToPageHandler) {
    const { activeTextColor, inactiveTextColor, textStyle, } = this.props;
    const textColor = isTabActive ? activeTextColor : inactiveTextColor;
    const fontWeight = isTabActive ? 'bold' : 'normal';
    return (
        <Button
        style={{ flex: 1 }}
        key={name}
        accessible={true}
        accessibilityLabel={name}
        accessibilityTraits='button'
        onPress={() => { 
            if (Variables.trade.isLogged) {
              this.props.beforeGoTo && this.props.beforeGoTo(page);
              onGoToPageHandler(page);
            } else {
              this.props.showModal();
            }
          }
        }
        >
            <View style={[styles.tab, this.props.tabStyle]}>
                <Text style={[{ color: textColor, fontWeight }, textStyle]}>
                    {name}
                </Text>
            </View>
        </Button>
    );
  }
  // 點選K線，顯示Dropdown，點選Dropdown的選項後，才移到那一頁
  renderTabDropdown(name, page, isTabActive, onGoToPageHandler) {
    const { activeTextColor, inactiveTextColor, textStyle, } = this.props;
    const color = isTabActive ? activeTextColor : inactiveTextColor;
    const fontWeight = isTabActive ? 'bold' : 'normal';
    // 原本是在onDropdownWillShow 直接call onGoToPageHandler. 修改為 ->
    // onSelect: TabView 不直接轉到K線圖，而是先選擇了dropdown選項(1,5,15分)後 1.發出request 2.跳轉頁面goToPage(onGoToPageHandler)
    return (
        <ModalDropdown
            key={name}
            layoutDropdown={this.state.layoutDropdown}
            style={[styles.dropdown_1, this.props.tabStyle]}
            tabStyle={styles.tab}
            options={this.props.dropdownOptions}
            defaultValue={this.props.dropdownText}
            onSelect={(rowId, rowData) => { 
              this._requestKData(rowData);
              this.props.beforeGoTo && this.props.beforeGoTo(page);
              onGoToPageHandler(page);
            }}
        >
            <ButtonCaret color={color} fontWeight={fontWeight} textStyle={textStyle} />
        </ModalDropdown>
    );
  }
  renderTab(name, page, isTabActive, onGoToPageHandler) {
    let tab;
    if (page === this.props.dropdownPage) {
        tab = this.renderTabDropdown(name, page, isTabActive, onGoToPageHandler);
    } else if (page === this.props.modalPage) {
        tab = this.renderTabModal(name, page, isTabActive, onGoToPageHandler);
    } else {
        tab = this.renderTabButton(name, page, isTabActive, onGoToPageHandler);
    }
    return tab;
  }
  render() {
    const containerWidth = this.props.containerWidth;
    const numberOfTabs = this.props.tabs.length;
    const signleTabWidth = containerWidth / numberOfTabs;
    const activeBorderBottomWidth = signleTabWidth / Layout.tabBarDropdownActiveBorderBottomSize;

    const tabUnderlineStyle = {
      position: 'absolute',
      width: containerWidth / numberOfTabs,
      height: Layout.tabBarDropdownActiveBorderBottomHeight,
      // backgroundColor: 'orange',
      bottom: 0,
    };

    const translateX = this.props.scrollValue.interpolate({
      inputRange: [0, 1],
      outputRange: [0, signleTabWidth],
    });
    // onLayout：得到並計算出dropdown的x,y,width,height
    return (
      <View 
        style={[styles.tabs, { backgroundColor: this.props.backgroundColor, }, this.props.style]}
        onLayout={(e) => {
          const { layout } = e.nativeEvent;
          const singleTabWidth = (layout.width / numberOfTabs);
          const singleTabHeight = layout.height;
          this.setState({ layoutDropdown: {
              x: singleTabWidth * this.props.dropdownPage,
              y: singleTabHeight + Layout.navBarHeight,
              width: singleTabWidth,
              height: singleTabHeight
            } 
          }); 
        }}
      >
        {
            this.props.tabs.map((name, page) => {
                const isTabActive = this.props.activeTab === page;
                const renderTab = this.props.renderTab || this.renderTab.bind(this);
                return renderTab(name, page, isTabActive, this.props.goToPage);
            })
        }
        <Animated.View
          style={[
            tabUnderlineStyle,
            {
              transform: [
                { translateX },
              ],
              flexDirection: 'row',
              justifyContent: 'center'
            },
            this.props.underlineStyle,
          ]}
        >
          <View style={{ backgroundColor: Colors.tabSliderColor, width: activeBorderBottomWidth }} />
        </Animated.View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  tab: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    // paddingBottom: 10,
  },
  tabs: {
    height: Layout.tabBarDropdownHeight,
    flexDirection: 'row',
    justifyContent: 'space-around',
    borderWidth: StyleSheet.hairlineWidth,
    borderTopWidth: 0,
    borderLeftWidth: 0,
    borderRightWidth: 0,
    borderColor: Colors.blackBorder,
  },
  dropdown_1: {
    flex: 1
  }
});
