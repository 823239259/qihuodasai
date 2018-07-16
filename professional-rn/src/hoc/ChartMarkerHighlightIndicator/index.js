/**
 * 1.Marker
 * 2.HighlightIndicator
 * 3.DisplayIndicator
 */
import React from 'react';
import { View, StyleSheet, PanResponder } from 'react-native';
import { observer } from 'mobx-react/native';
import { Layout, Colors, Enum, Config } from '../../global';
import DisplayIndicator from './DisplayIndicator';
import { Marker } from '../../screens/QuotationScreen/QuotationDetailScreen/components';

@observer
export const ChartMarkerHighlightIndicator = (WrappedComponent, chartType, options) => {
    return class extends React.Component {
        wrappedComponentInstance;
        wrappedComponentRef;

        constructor(props) {
            super(props);
            // inject任何一個xxxStore，就會是ChartMarkerHighlightIndicator的store
            const storeKeyName = Object.keys(this.props).find((key) => {
                return (key.indexOf('Store') !== -1);
            });
            this.store = this.props[storeKeyName];
            // Chart(View)放在ScrollableTabView裡面，高度可能要做調整
            this.options = options || { top: 0 };

            this.containerHeight = 0;
            this.upperChartHeight = 0;
            this.containerWidth = 0;
            this.highlightTimeout = null;
            this.state = { tabbed: false };

            this._panResponder = {};
            // vertical HightlightIndicator
            this.vHightlightIndicator = {
                ref: null,
                previousLeft: 0,
                previousTop: 0,
                styles: {
                    style: {
                        left: 0,
                        top: 0,
                        backgroundColor: Colors.unHighlightColor
                    }
                },
            };
            // horizontal HighlightIndicator
            this.hHightlightIndicator = {
                ref: null,
                previousLeft: 0,
                previousTop: 0,
                styles: {
                    style: {
                        left: 0,
                        top: 0,
                        backgroundColor: Colors.unHighlightColor
                    }
                },
            };
            // vertical displayIndicator
            this.vDisplayIndicator = {
                ref: null,
                previousLeft: 0,
                previousTop: 0,
                styles: {
                    style: {
                        left: 0,
                        top: 0,
                        backgroundColor: Colors.unHighlightColor
                    }
                }
            };
            // horizontal displayIndicator
            this.hDisplayIndicator = {
                ref: null,
                previousLeft: 0,
                previousTop: 0,
                styles: {
                    style: {
                        left: 0,
                        top: 0,
                        backgroundColor: Colors.unHighlightColor
                    }
                }
            };
            /**
             * Object.assign();
             * 1.最後可以取得的object - props
             * 2.預設的props - 會被傳入的props蓋掉
             * 3.傳入的props
             */
            this.newProps = Object.assign({
                    style: null
                },
                this.props,
                { ref: this.proc.bind(this) } // 取得wrappredComponent ref
            );
        }
        proc(wrappedComponentInstance) {
            if (wrappedComponentInstance) {
                this.wrappedComponentInstance = wrappedComponentInstance;
                this.store = this.wrappedComponentInstance.store;
                this.wrappedComponentRef = wrappedComponentInstance.refInput;
            }
        }
        componentWillMount() {
            this._panResponder = PanResponder.create({
              onStartShouldSetPanResponder: this._handleStartShouldSetPanResponder,
              onMoveShouldSetPanResponder: this._handleMoveShouldSetPanResponder,
              onPanResponderGrant: this._handlePanResponderGrant,
              onPanResponderMove: this._handlePanResponderMove,
              onPanResponderRelease: this._handlePanResponderEnd,
              onPanResponderTerminate: this._handlePanResponderEnd,
            });
        }
        componentDidMount() {
            this._updateNativeStyles();
        }
        _setStateTabbedAfterRefReady(tabbed) {
            if (this.vHightlightIndicator.ref &&
                this.hHightlightIndicator.ref) {
                    this.setState({ tabbed });
            }
        }
        _highlight() {
            this.vHightlightIndicator.styles.style.backgroundColor = Colors.highlightColor;
            this.hHightlightIndicator.styles.style.backgroundColor = Colors.highlightColor;
            this.vDisplayIndicator.styles.style.backgroundColor = Colors.markerBackgroundColor;
            this.hDisplayIndicator.styles.style.backgroundColor = Colors.markerBackgroundColor;
            this._updateNativeStyles();
        }
        _unHighlight() {
            this.vHightlightIndicator.styles.style.backgroundColor = Colors.unHighlightColor;
            this.hHightlightIndicator.styles.style.backgroundColor = Colors.unHighlightColor;
            this.vDisplayIndicator.styles.style.backgroundColor = Colors.unHighlightColor;
            this.hDisplayIndicator.styles.style.backgroundColor = Colors.unHighlightColor;
            this._updateNativeStyles();
        }
        _updateNativeStyles() {
            this.vHightlightIndicator.ref && this.vHightlightIndicator.ref.setNativeProps(this.vHightlightIndicator.styles);
            this.hHightlightIndicator.ref && this.hHightlightIndicator.ref.setNativeProps(this.hHightlightIndicator.styles);
            this.vDisplayIndicator.ref && this.vDisplayIndicator.ref.setNativeProps(this.vDisplayIndicator.styles);
            this.hDisplayIndicator.ref && this.hDisplayIndicator.ref.setNativeProps(this.hDisplayIndicator.styles);
        }
        _handleStartShouldSetPanResponder(e: Object, gestureState: Object): boolean {
            // Should we become active when the user presses down on the circle?
            return true;
        }
    
        _handleMoveShouldSetPanResponder(e: Object, gestureState: Object): boolean {
            // Should we become active when the user moves a touch over the circle?
            return true;
        }
    
        _handlePanResponderGrant = (e: Object, gestureState: Object) => {
            this.vHightlightIndicator.previousLeft = gestureState.x0;
            this.vHightlightIndicator.styles.style.left = gestureState.x0;
            
            this.hDisplayIndicator.previousLeft = gestureState.x0 - (Layout.displayWidth / 2);
            this.hDisplayIndicator.styles.style.left = gestureState.x0 - (Layout.displayWidth / 2);
    
            this.hHightlightIndicator.previousTop = gestureState.y0;
            this.hHightlightIndicator.styles.style.top = gestureState.y0 - this.options.top;
            this.vDisplayIndicator.previousTop = gestureState.y0 - (Layout.displayHeight / 2);
            this.vDisplayIndicator.styles.style.top = gestureState.y0 - (Layout.displayHeight / 2) - this.options.top;

            this.vDisplayIndicator.styles.style.left = this.containerWidth - (Layout.displayWidth + Layout.inset);
            this.hDisplayIndicator.styles.style.top = this.upperChartHeight;
            
            this._highlight();
            this._setStateTabbedAfterRefReady(true);
            clearTimeout(this.highlightTimeout);
        }
        _handlePanResponderMove = (e: Object, gestureState: Object) => {
            this.vHightlightIndicator.styles.style.left = this.vHightlightIndicator.previousLeft + gestureState.dx;
            this.hDisplayIndicator.styles.style.left = this.hDisplayIndicator.previousLeft + gestureState.dx;
            this.hHightlightIndicator.styles.style.top = (this.hHightlightIndicator.previousTop + gestureState.dy) - this.options.top;
            this.vDisplayIndicator.styles.style.top = (this.vDisplayIndicator.previousTop + gestureState.dy) - this.options.top;
            this._updateNativeStyles();
        }
        _handlePanResponderEnd = (e: Object, gestureState: Object) => {
            
            this.vHightlightIndicator.previousLeft += gestureState.dx;
            this.hDisplayIndicator.previousLeft += gestureState.dx;
            this.hHightlightIndicator.previousTop += gestureState.dy;
            this.vDisplayIndicator.previousTop += gestureState.dy;
            this.highlightTimeout = setTimeout(() => {
                this._unHighlight();
                this._setStateTabbedAfterRefReady(false);
            }, Config.chartHighlightTime);
        }
        _renderMarker() {
            if (this.store) {
                const { xIndexSelected, data, selectedData } = this.store;
                if (xIndexSelected && this.state.tabbed && selectedData) {
                    let header = '';
                    if (chartType === Enum.chart.display.k) {
                        header = selectedData.header;
                    } else {
                        header = selectedData.timeLabel;
                    }
                    return (
                        <Marker 
                            units={data.times.length} 
                            xIndexSelected={xIndexSelected}
                            header={header}
                            content={this._getMarkerContent(selectedData)}
                            hide={() => this.store.hideMarker()}
                        />
                    );
                }
            }
        }
        _getMarkerContent(selectedData) {
            let markerContent = [
                { text: '价格', value: selectedData.price },
                { text: '成交量', value: selectedData.volumn, color: Colors.barColor }
            ];
            if (chartType === Enum.chart.display.k) {
                markerContent = [
                    { text: '开盘', value: selectedData.open },
                    { text: '最高', value: selectedData.shadowH },
                    { text: '最低', value: selectedData.shadowL },
                    { text: '收盘', value: selectedData.close },
                    { text: '涨跌', value: selectedData.rate },
                    { text: '成交量', value: selectedData.volumn, color: Colors.barColor },
                    { text: 'MA5', value: selectedData.ma5, color: Enum.ma.five.color },
                    { text: 'MA10', value: selectedData.ma10, color: Enum.ma.ten.color },
                    { text: 'MA20', value: selectedData.ma20, color: Enum.ma.twenty.color },
                    { text: 'MA30', value: selectedData.ma30, color: Enum.ma.thirty.color },
                ];
            }
            return markerContent;
        }
        _setContainerHeight = (e) => {
            this.containerHeight = e.nativeEvent.layout.height;
            this.upperChartHeight = this.containerHeight * 0.66;
            this.containerWidth = e.nativeEvent.layout.width;
        }
        _renderHDisplayIndicator() {
            if (this.store) {
                const { xIndexSelected, selectedData } = this.store;
                if (xIndexSelected && this.state.tabbed && selectedData) {
                    return (
                        <DisplayIndicator 
                                ref={(hDisplayIndicatorRef) => {
                                    this.hDisplayIndicator.ref = hDisplayIndicatorRef;
                                }}
                                style={[styles.hDisplayIndicator]}
                                text={selectedData.timeLabel}
                        />
                    );
                }
            }
        }
        _renderVDisplayIndicator() {
            if (this.store) {
                const { xIndexSelected, selectedData } = this.store;
                if (xIndexSelected && this.state.tabbed && selectedData) {
                    let textDisplay = '';
                    if (chartType === Enum.chart.display.k) {
                        textDisplay = selectedData.close.toString();
                        if (this.vDisplayIndicator.styles.style.top > this.upperChartHeight) {
                            textDisplay = selectedData.volumn.toString();
                        }
                    } else {
                        textDisplay = selectedData.price.toString();
                        if (this.vDisplayIndicator.styles.style.top > this.upperChartHeight) {
                            textDisplay = selectedData.volumn.toString();
                        }
                    }
                    return (
                        <DisplayIndicator
                            ref={(vDisplayIndicatorRef) => {
                                this.vDisplayIndicator.ref = vDisplayIndicatorRef;
                            }}
                            style={[styles.vDisplayIndicator]}
                            text={textDisplay}
                        />
                    );
                }
            }
        }
        render() {
            return (
                <View
                  onLayout={this._setContainerHeight}
                  style={styles.container}
                  {...this._panResponder.panHandlers}
                >
                 
                    <WrappedComponent store={this.store} {...this.newProps} />
                    <View
                        ref={(vHightlightIndicatorRef) => {
                            this.vHightlightIndicator.ref = vHightlightIndicatorRef;
                        }}
                        style={[styles.vHightlightIndicator]}
                    />
                    <View
                        ref={(hHightlightIndicatorRef) => {
                            this.hHightlightIndicator.ref = hHightlightIndicatorRef;
                        }}
                        style={[styles.hHightlightIndicator]}
                    />
                    { this._renderHDisplayIndicator() }
                    { this._renderVDisplayIndicator() }
                    { this._renderMarker() }
                </View>
            );
        }
    };
};

const styles = StyleSheet.create({
    vHightlightIndicator: {
        position: 'absolute',
        width: Layout.highlightIndicatorWidth,
        height: Layout.screenHeight,
        left: 0,
        top: 0,
    },
    hHightlightIndicator: {
        position: 'absolute',
        width: Layout.screenWidth,
        height: Layout.highlightIndicatorWidth,
        left: 0,
        top: 0,
    },
    container: {
      position: 'relative',
      flex: 1,
    },
});
