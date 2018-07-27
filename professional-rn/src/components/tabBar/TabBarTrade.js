import React, { Component } from 'react';
import { StyleSheet, View, Text, ViewPropTypes } from 'react-native';
import Button from './Button';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../global';

export default class TabBarTrade extends Component {
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
    borderStyle: ViewPropTypes.style
  }
  static defaultProps = {
    activeTextColor: Colors.tabBarDropdownActiveTextColor,
    inactiveTextColor: 'black',
    backgroundColor: null
  }
  renderTabOption(name, page) {
  }
  renderTab(name, page, isTabActive, onPressHandler) {

    const { activeTextColor, inactiveTextColor, textStyle, } = this.props;
    const textColor = isTabActive ? activeTextColor : inactiveTextColor;
    const fontWeight = isTabActive ? 'bold' : 'normal';
    
    // 控制border的顯示
    const { borderWidth, borderColor } = this.props.borderStyle;
    const borderStyle = {
      borderTopWidth: borderWidth,
      borderLeftWidth: borderWidth,
      borderBottomWidth: borderWidth,
      borderColor
    };

    if (this.tabsLength === page + 1) {
      borderStyle.borderRightWidth = borderWidth;
    }

    return <Button
      style={[borderStyle, { flex: 1 }]}
      key={name}
      accessible={true}
      accessibilityLabel={name}
      accessibilityTraits='button'
      onPress={() => onPressHandler(page)}
    >
      <View style={[styles.tab, this.props.tabStyle, ]}>
        <Text style={[{color: textColor, fontWeight, }, textStyle, ]}>
          {name}
        </Text>
      </View>
    </Button>;
  }

  render() {
    this.tabsLength = this.props.tabs.length;
    // const containerWidth = this.props.containerWidth;
    // const numberOfTabs = this.props.tabs.length;
    // const tabUnderlineStyle = {
    //   position: 'absolute',
    //   width: containerWidth / numberOfTabs,
    //   height: 4,
    //   backgroundColor: 'navy',
    //   bottom: 0,
    // };

    // const translateX = this.props.scrollValue.interpolate({
    //   inputRange: [0, 1],
    //   outputRange: [0,  containerWidth / numberOfTabs],
    // });
    
    
    return (
      <View style={[styles.tabs, {backgroundColor: this.props.backgroundColor, }, this.props.style, ]}>
        {this.props.tabs.map((name, page) => {
          const isTabActive = this.props.activeTab === page;
          const renderTab = this.props.renderTab || this.renderTab;
          return this.renderTab(name, page, isTabActive, this.props.goToPage);
        })}
        {/* <Animated.View
          style={[
            tabUnderlineStyle,
            {
              transform: [
                { translateX },
              ]
            },
            this.props.underlineStyle,
          ]}
        /> */}
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
    height: Layout.tabBarTradeHeight,
    flexDirection: 'row',
    justifyContent: 'space-around',
    // borderWidth: 1,
    // borderTopWidth: 0,
    // borderLeftWidth: 0,
    // borderRightWidth: 0,
    // borderColor: '#ccc',
  },
});
