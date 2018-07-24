/*
    閃電圖
    1.LineChart
*/

import { action, computed, extendObservable, comparer, observable} from 'mobx';
import { processColor } from 'react-native';
import { Config, Colors } from '../../global';
import { ChartUtil } from '../../utils';

let isActive = false;
export default class LightningStore {
    @observable dotSize = null;

    constructor() {
        this.reset();
    }
    setIsActive(active) {
        isActive = active;
    }
    @action reset() {
        extendObservable(this, {
            data: {
                prices: [],
                times: []
            }
        });
    }
    // LineChart
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get lineXAxis() {
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
    @computed get lineYAxis() {
        return {
            left: {
                enabled: false,
            },
            right: {
                valueFormatter: ChartUtil.getValueFormatter(this.dotSize),
                textColor: processColor(Colors.white), // y軸的字樣
                position: 'INSIDE_CHART',              // y軸顯示在內側
                drawGridLines: false,
            }
        };
    }
    // 兩條線 -> 一條線只是畫圓 標注出有變化的值
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get lineData() {
        const dataSets = [this.getRealLineDataSet(), ...this.getCircleDataSets()];
        // 資料數量 >= 2，才要加入lastestline
        if (this.data.prices.length >= 2) {
            dataSets.push(ChartUtil.getLastestLineDataSet(this.data.prices));
        }
        return {
            dataSets
        };
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
    getRealLineDataSet() {
        const valuesArr = this.data.prices.map((price) => {
            return { y: price };
        });
        return {
            values: valuesArr,
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
                valueFormatter: ChartUtil.getValueFormatter(this.dotSize),
                
                // 測試
                // selectionShift: 13
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
                        valueFormatter: ChartUtil.getValueFormatter(this.dotSize),
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
    @action start(product) {
        this.dotSize = product.dotSize;
        if (product.time_flag) {
            this.data.prices.push(product.last);
            this.data.times.push((product.time_flag).split(' ')[1]);
        }
    }
    @action add(param, product) {
        this.dotSize || (this.dotSize = product.dotSize);
        // 最多Config.chartDataLimited筆
        if (this.data.prices.length === Config.chartDataLimited) {
            this.data.prices.shift();
            this.data.times.shift();
        }
        if (param.time_flag) {
            this.data.prices.push(param.last);
            this.data.times.push((param.time_flag).split(/[' '|'.']/)[1]);
        }
    }
}

