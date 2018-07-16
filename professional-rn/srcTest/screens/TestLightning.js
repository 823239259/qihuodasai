import React, { Component } from 'react';
import { View, processColor, StyleSheet } from 'react-native';
import { extendObservable, computed } from 'mobx';
import { observer } from 'mobx-react/native';
import { LineChart } from 'react-native-charts-wrapper';
import { Colors, Config } from '../../src/global';
import { ChartUtil } from '../../src/utils';

const testPriceData1 = JSON.parse('[12254,12254,12254,12254,12254]');
const testTimeData1 = JSON.parse('["16:39:51","16:39:51","16:39:51","16:39:51","16:39:51"]');

const testPriceData2 = JSON.parse('[12258]');
const testTimeData2 = JSON.parse('["16:38:20"]');

const testPriceData3 = JSON.parse('[12257.5,12257.5]');
const testTimeData3 = JSON.parse('["16:38:20","16:38:20"]');

const testPriceData4 = JSON.parse('[12254,12254,12254,12254,12254,12254,12254,12254,12254,12254,12254,12255,12255,12255,12255,12255,12255,12254,12254,12254,12254,12254,12254,12254,12254,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5,12254.5]');
const testTimeData4 = JSON.parse('["16:39:51","16:39:51","16:39:51","16:39:51","16:39:51","16:39:51","16:39:51","16:39:52","16:39:52","16:39:52","16:39:52","16:39:53","16:39:53","16:39:53","16:39:53","16:39:53","16:39:53","16:39:54","16:39:54","16:39:54","16:39:54","16:39:54","16:39:54","16:39:55","16:39:55","16:39:55","16:39:55","16:39:55","16:39:55","16:39:56","16:39:56","16:39:56","16:39:56","16:39:56","16:39:56","16:39:56","16:39:56","16:39:57","16:39:57","16:39:57"]');

class Store {
    constructor() {
        this.reset();
    }
    reset() {
        extendObservable(this, {
            data: {
                prices: testPriceData4,
                times: testTimeData4
            }
        });
    }
    getLastestTimeData(dataArrObservable) {
        const dataArr = dataArrObservable.slice();
        // 資料數量 >= 2，才要加入lastestline
        if (dataArr.length < 2) {
            return dataArr;
        }
        // get latest time
        const lastest = dataArr[dataArr.length - 1];
        // copy chartLastestLine latest time
        let chartLastestLine = Config.chartLastestLine;
        if (dataArr.length < chartLastestLine) {
            chartLastestLine = dataArr.length;
        }
        for (let i = 0; i < chartLastestLine; i++) {
            dataArr.push(lastest);
        }
        return dataArr;
    }
    @computed get lineXAxis() {
        const dataArr = this.getLastestTimeData(this.data.times);

        return { 
            valueFormatter: dataArr,
            position: 'BOTTOM',
            textColor: processColor(Colors.white), // x軸的字樣
            // granularityEnabled: true,
            // granularity: 1
            // drawLabels: false,
            drawGridLines: false,
            drawAxisLine: true,
            // gridColor: 'red',
            // gridLineColor: 'purple',
            // gridLinesColor: 'brown',
            // axisLineColor: 'green',
            // axixColor: 'pink'
        };
    }
    lineYAxis = {
        left: {
            enabled: false,
        },
        right: {
            valueFormatter: '##.0',
            textColor: processColor(Colors.white), // y軸的字樣
            position: 'INSIDE_CHART',              // y軸顯示在內側
            drawGridLines: false,
        }
    }
    @computed get lineData() {
        const dataSets = [this.getRealLineDataSet(), ...this.getCircleDataSets()];
        // 資料數量 >= 2，才要加入lastestline
        if (this.data.prices.length >= 2) {
            dataSets.push(ChartUtil.getLastestLineDataSet(this.data.prices));
        }
        return {
            dataSets
        };
    }
    getRealLineDataSet() {
        const lineArr = this.data.prices.map((price) => {
            return { y: price };
        });
        return {
            values: lineArr,
            label: '',
            
            config: {
                // 是否畫圖
                visible: true,
                lineWidth: 1,
                drawCubicIntensity: 0.4,
                drawHighlightIndicators: false,
                // line color
                color: processColor(Colors.lighteningLineColor),
                // fill color
                drawFilled: true,
                fillColor: processColor(Colors.lighteningLineColor),
                fillAlpha: 50,
                // circle settings
                drawCircles: false,
                // value setting 是否顯示該點表示的值
                drawValues: false,
                // axisDependency: 'LEFT'
                valueFormatter: '##.00',
            }
        };
    }
    // https://stackoverflow.com/questions/1960473/get-all-unique-values-in-an-array-remove-duplicates
    getCircleDataSets() {
        // 取出 獨特 值
        const uniqueValue = new Set(this.data.prices.slice());
        // 做一個全是null的array
        const nullArray = this.data.prices.map(() => {
            return null;
        });
        // 做一個1. 只有唯一值，2.剩下都補0
        const uniqueDataSets = [];
        // reverse是為了唯一值要是最新的位置
        this.data.prices.reverse().forEach((price, index) => {
            if (uniqueValue.has(price)) {
                uniqueDataSets.push(price);
                uniqueValue.delete(price);
            } else {
                uniqueDataSets.push(null);
            }
        });
        // 再reverse一次，為了取正確index
        uniqueDataSets.reverse();
        // 有幾個 獨特 值 就畫幾條線
        const result = [];
        uniqueDataSets.forEach((u, index) => {
            if (u !== null) {
                const na = nullArray.slice();
                na[index] = u;
                result.push({
                    values: na,
                    label: '',   
                    config: {
                        // 是否畫圖
                        visible: true,
                        lineWidth: 0,
                        color: processColor(Colors.lighteningLineColor),
                        drawFilled: false,
                        // circle settings
                        drawCircles: true,
                        circleRadius: 5,
                        circleColor: processColor(Colors.lighteningLineColor),
                        circleHoleColor: processColor(Colors.lighteningLineColor),
                        // value setting 是否顯示該點表示的值
                        drawValues: true,
                        valueTextColor: processColor('white'),
                        valueTextSize: 14,
                        valueFormatter: '',
                        drawHighlightIndicators: false,
                    }
                });
            }
        }); 
        return result;
    }
    lineSetting = {
        legend: {
            enabled: false,
            // textColor: processColor('blue'),
            // textSize: 12,
            // position: 'BELOW_CHART_RIGHT',
            // form: 'SQUARE',
            // formSize: 14,
            // xEntrySpace: 10,
            // yEntrySpace: 5,
            // formToTextSpace: 5,
            // wordWrapEnabled: true,
            // maxSizePercent: 0.5,
            // custom: {
            //     colors: [processColor('red'), processColor('blue'), processColor('green')],
            //     labels: ['Company X', 'Company Y', 'Company Dashed']
            // }
        },
        marker: {
            enabled: false,
            backgroundTint: processColor('teal'),
            markerColor: processColor('#F0C0FF8C'),
            textColor: processColor('white'),
        }
    }
}
@observer
export default class TestLightning extends Component {
    constructor(props) {
        super(props);
        this.store = new Store();
    }
    render() {
        return (
            <View style={{ flex: 1 }}>
                <View style={styles.container}>
                    <LineChart
                        style={styles.chart}

                        data={this.store.lineData}
                        xAxis={this.store.lineXAxis}
                        yAxis={this.store.lineYAxis}

                        chartDescription={{ text: '' }}
                        chartBackgroundColor={processColor(Colors.chartBackgroundColor)}
                        // decorator
                        legend={this.store.lineSetting.legend}
                        marker={this.store.lineSetting.marker}
                        // grid - false後 之後的設定也沒用 留著做紀錄
                        drawGridBackground={false}
                        // borders - false後 之後的設定也沒用 留著做紀錄
                        drawBorders={false}
                        borderColor={processColor(Colors.chartBorderColor)}
                        // borderWidth={1}

                        animation={{ durationX: 0 }}

                        touchEnabled={true}
                        dragEnabled={true}
                        scaleEnabled={false}
                        scaleXEnabled={false}
                        scaleYEnabled={false}
                        pinchZoom={false}
                        doubleTapToZoomEnabled={false}
                        dragDecelerationEnabled={true}
                        dragDecelerationFrictionCoef={0.99}
                        keepPositionOnRotation={false}
                    />
                </View>
            </View>
        );
    }
}
// drawGridBackground={false}
// chartBackgroundColor={processColor('pink')}
// chartDescription={this.state.description}
// gridBackgroundColor={processColor('#ffffff')}
// drawBarShadow={false}
// drawValueAboveBar={true}
// drawHighlightArrow={true}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        // backgroundColor: Colors.bg
    },
    chart: {
        flex: 1
    }
});
