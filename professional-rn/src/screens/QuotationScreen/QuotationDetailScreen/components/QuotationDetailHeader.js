import React, { Component } from 'react';
import { StyleSheet, View, TouchableOpacity, Text, Alert, Platform } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Layout, Colors, Variables } from '../../../../global';

@inject('QuotationDetailStore','FutureTypeStore') @observer
export default class QuotationDetailHeader extends Component {

  _getTitle() {
    const { QuotationDetailStore } = this.props;
    console.log(this.props)
    console.log(QuotationDetailStore);
    let title = '行情圖表';
   
    if (QuotationDetailStore.product) {
      
      title = `${QuotationDetailStore.product.commodityName} ${QuotationDetailStore.product.productName}`;
    }
    return title;
  }
  render() {
    const { QuotationDetailStore, FutureTypeStore } = this.props;
    const securityType = FutureTypeStore.isFutIn?styles.viewIn:styles.viewOut
    const typeName = FutureTypeStore.isFutIn?'内':'外'
    return (

      // <View style={[styles.container, { marginLeft: Layout.quotationDetailHeaderMargin }]}>
      <View style={[styles.container]}>
        <View style={styles.viewForPosition}>
          <TouchableOpacity 
              style={styles.button} onPress={() => {
              QuotationDetailStore.toggleIsPickerVisible();
            }
          }
          >
            <Text style={{ color: Colors.quotationDetailHeaderTextColor, textAlign: 'center',marginLeft: 30, fontSize: Layout.quotationDetailHeaderFontSize }}>{this._getTitle()}</Text>
            {/* <Text style={{textAlign: 'center'}}>{this._getTitle()}</Text> */}
           
          </TouchableOpacity>
          <Icon
            name="caret-down" 
            size={16} 
            color={Colors.kCaretColor}
            style={{ position: 'absolute', bottom: -5, left: -1, transform: [{ rotate: '45deg' }] }} 
          />
          
        </View>

        <View style = {[styles.viewRight,securityType]}>
          <Text style = {styles.typeIcon}>{typeName}</Text>
        </View>
        
        
      </View>
    );
  }
}
const paddingMarginVertical = 5;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'row',
    ...Layout.center
  },
  viewForPosition: {
    position: 'relative',
    flex: 4,
    //marginRight: 10,
    ...Layout.center
  },
  viewRight: {
    flex: 0,
    width: 20,
    height: 20,
    marginLeft: 40,
    borderRadius: 3,
    ...Layout.center
  },
  viewOut: {
    backgroundColor: "#d47fff",
  },
  viewIn: {
    backgroundColor: '#f2d230'
  },
  typeIcon: {
    color: "#222d3e",
    fontSize: 12,
  },
  


  // typeIcon: {
  //   width: 15,
  //   height: Layout.navBarHeight - (paddingMarginVertical * 2),
    
  // },  
  button: {
    width: Layout.screenWidth/2,
    height: Layout.navBarHeight - (paddingMarginVertical * 2),
    alignSelf: 'center',
    backgroundColor: Colors.quotationDetailHeaderBackgroundColor,
    borderRadius: 2,
    ...Layout.center
  }
});
