/*
  顯示Product:
  changeRate: '-0.33%'
  changeRateIsUp: true
  productName: 'CL1710'
  commodityName: '国际原油'
  contractNo: '1710'  --> MainContract --> ContractNo
  commodityNo: 'CL"    --> CommodityNo
  contractSize: 10
  dotSize: 2
  exchangeNo: 'NYMEX'
  lastPrice: '47.71'
  lastPriceIsUp: true
  miniTikeSize: 0.01
  totalVolume: 70232
*/

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { View, Text, Animated, TouchableWithoutFeedback, Platform } from 'react-native';
import Swipeout from 'react-native-swipeout';
import { inject, observer } from 'mobx-react/native';
import { Variables, Colors } from '../../global';
import styles from './Styles';

@inject('QuotationStore') @observer
export default class RowSwipeout extends Component {
  static propTypes = {
    product: PropTypes.object,
    navigator: PropTypes.object,
    index: PropTypes.number,
  }

  static defaultProps = {
  }

  constructor(props) {
    super(props);
    
    this.state = {
      fadeAnim: null,  
    };
  }
  // componentDidUpdate也行 - https://github.com/mobxjs/mobx-react#componentwillreact-lifecycle-hook
  componentWillReact() { 
    // android效能比較差，所以只在ios做animation
    if (Platform.OS === 'ios') {
      this.state.fadeAnim = new Animated.Value(0.9); // Initial value for opacity: 0
      Animated.timing(                  // Animate over time
        this.state.fadeAnim,            // The animated value to drive
        {
          toValue: 1,                   // Animate to opacity: 1 (opaque)
          duration: 500,              // Make it take a while
        }
      ).start();                        // Starts the animation    
    }
  }

  toQuotationDetail = () => {
    const { product } = this.props;
    // 表示當前選擇的合約，抓到的資料會有問題
    if (!product.LastPrice && !product.AskPrice1 && !product.BidPrice1) {
      return;
    }
    this.props.navigator.push({
      screen: 'quotation.QuotationDetailScreen',
      animationType: 'slide-horizontal',
      title: '',
      backButtonTitle: '', //返回
      passProps: {
        commodityNo: product.commodityNo, // MCH
        contractNo: product.contractNo,   // 1710
      },
      navigatorButtons: {
        rightButtons: [{ id: Variables.icon.bars.id, icon: Variables.icon.bars.source }]
      }
    });    
  }
 
  render() {
    const { QuotationStore, product } = this.props;
    const { fadeAnim } = this.state;
    const { containerStyle, textStyle, centerStyle, centerVerticalStyle, borderStyle } = styles;
    const blockBackgroundColor = product.changeRateIsUp ? Colors.green : Colors.red;
    return (
      <View style={{ flex: 1, backgroundColor: blockBackgroundColor }}>
        <Animated.View 
          style={{ opacity: fadeAnim }}
        >
          <Swipeout
            close={!(QuotationStore.sectionID === product.sectionID && QuotationStore.rowID === product.rowID)}
            left={[
                {
                    text: '关注',
                    onPress: () => { QuotationStore.focusProduct(product.productName); },
                    type: 'primary',
                }
            ]
            }
            rowID={product.rowID}
            sectionID={product.sectionID}
            autoClose={true}
            backgroundColor={Colors.quotationContentBackgroundColor}
            onOpen={(sectionID, rowID) => {
              QuotationStore.swipeout(sectionID, rowID);
            }}
          >
          <TouchableWithoutFeedback onPress={this.toQuotationDetail.bind(this, product)}>
              <View style={[containerStyle, borderStyle]}>
                  <View style={centerVerticalStyle}>
                      <Text style={[textStyle]}>{product.commodityName}</Text>
                      <Text style={[textStyle, { fontSize: 11, color: Colors.quotationGreyColor }]}>{product.productName}</Text>
                  </View>
                  <View style={centerStyle}>
                      <Text style={[textStyle, { color: product.lastPriceIsUp ? Colors.green : Colors.red }]}>{product.lastPrice}</Text>
                  </View>
                  <View style={centerStyle}>
                      <Text style={[textStyle, { color: Colors.quotationGreyColor }]}>{product.totalVolume}</Text>
                  </View>
                  <View style={[centerStyle, { flex: 1, borderRadius: 5, backgroundColor: product.changeRateIsUp ? Colors.green : Colors.red, marginVertical: 10 }]}>
                      <Text style={textStyle}>{`${product.changeRate}%`}</Text>
                  </View>
              </View>
          </TouchableWithoutFeedback>
          </Swipeout>
        </Animated.View>
      </View>
    );
  }
}
