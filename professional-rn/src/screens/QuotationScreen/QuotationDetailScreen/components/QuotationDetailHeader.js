import React, { Component } from 'react';
import { StyleSheet, View, TouchableOpacity, Text, Alert, Platform } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Layout, Colors, Variables } from '../../../../global';

@inject('QuotationDetailStore') @observer
export default class QuotationDetailHeader extends Component {

  _getTitle() {
    const { QuotationDetailStore } = this.props;
    let title = '行情圖表';
    if (QuotationDetailStore.product) {
      title = `${QuotationDetailStore.product.commodityName} ${QuotationDetailStore.product.productName}`;
    }
    return title;
  }
  render() {
    const { QuotationDetailStore } = this.props;
    return (

      <View style={[styles.container, { marginLeft: Layout.quotationDetailHeaderMargin }]}>
        <View style={styles.viewForPosition}>
          <TouchableOpacity 
              style={styles.button} onPress={() => {
              QuotationDetailStore.toggleIsPickerVisible();
            }
          }
          >
            <Text style={{ color: Colors.quotationDetailHeaderTextColor, textAlign: 'center', fontSize: Layout.quotationDetailHeaderFontSize }}>{this._getTitle()}</Text>
          </TouchableOpacity>
          <Icon
            name="caret-down" 
            size={16} 
            color={Colors.kCaretColor}
            style={{ position: 'absolute', bottom: -5, left: -2, transform: [{ rotate: '45deg' }] }} 
          />
        </View>
      </View>
    );
  }
}
const paddingMarginVertical = 5;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    ...Layout.center
  },
  viewForPosition: {
    position: 'relative',
    ...Layout.center
  },
  button: {
    width: Layout.screenWidth / 2,
    height: Layout.navBarHeight - (paddingMarginVertical * 2),
    alignSelf: 'center',
    backgroundColor: Colors.quotationDetailHeaderBackgroundColor,
    borderRadius: 2,

    ...Layout.center
  }
});
