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
import { View, Text, Animated, TouchableOpacity } from 'react-native';
import { observer } from 'mobx-react/native';
import { Layout, Variables, Colors } from '../../global';
import styles from './Styles';

@observer
export default class FocusedGrid extends Component {
  static propTypes = {
    product: PropTypes.object,
    navigator: PropTypes.object,
    index: PropTypes.number,
    height: PropTypes.number
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
    this.state.fadeAnim = new Animated.Value(0.9); // Initial value for opacity: 0
    Animated.timing(                  // Animate over time
      this.state.fadeAnim,            // The animated value to drive
      {
        toValue: 1,                   // Animate to opacity: 1 (opaque)
        duration: 500,              // Make it take a while
      }
    ).start();                        // Starts the animation    
  }

  toQuotationDetail = () => {
    const { product } = this.props;
    // 表示當前選擇的合約，抓到的資料會有問題
    if (!product.last && !product.ask && !product.bid) {
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
    const { product } = this.props;
    const { fadeAnim } = this.state;
    const { textStyle, gridPriceFontSize, gridPercentageFontSize, gridSmallFontSize, gridVolumnFontSize } = styles;
    // 三個Grid 中間四個margin
    const gridWidth = (Layout.screenWidth - (Layout.inset * 2)) / 3;
    // this.props.index % 3 表示每一行的第一個
    return (
      <View style={{ flex: 1, height: this.props.height, width: gridWidth, marginLeft: (this.props.index % 3 === 0) ? 0 : Layout.inset, backgroundColor: product.changeRateIsUp ? Colors.green : Colors.red }}>
        <Animated.View 
          style={{ flex: 1, opacity: fadeAnim }}
        >
          <TouchableOpacity style={{ flex: 1, flexDirection: 'row', justifyContent: 'center', backgroundColor: 'rgb(53, 53, 53)', borderRadius: 10 }} onPress={this.toQuotationDetail}>
            <View style={{ flex: 1, flexDirection: 'column', paddingTop: 10, paddingBottom: 5, paddingHorizontal: 5, justifyContent: 'space-between' }}>
              <Text style={[textStyle, { textAlign: 'center' }]}>{product.commodityName}</Text>
              <Text style={[textStyle, { fontSize: gridSmallFontSize, color: Colors.quotationGreyColor, textAlign: 'center' }]}>{product.productName}</Text>
              {/* 最新價 */}
              <Text style={[{ fontSize: gridPriceFontSize, textAlign: 'center', color: product.lastPriceIsUp ? Colors.green : Colors.red }]}>{product.lastPrice}</Text>
              <Text style={[{ fontSize: gridPercentageFontSize, textAlign: 'center', color: product.lastPriceIsUp ? Colors.green : Colors.red }]}>{`${product.changeRate}%`}</Text>
              <Text style={[textStyle, { fontSize: gridVolumnFontSize, textAlign: 'center', color: Colors.quotationGreyColor }]}>{product.totalVolume}</Text>
            </View>
          </TouchableOpacity>
        </Animated.View>
      </View>
    );
  }
}
