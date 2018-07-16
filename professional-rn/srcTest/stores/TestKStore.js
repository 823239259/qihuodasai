/*
    K線
    CombinedChart -> 1.CandlestickChart 2.BarChart 3. LineChart(MA)
*/

import { action, computed, extendObservable, comparer } from 'mobx';
import { processColor } from 'react-native';
import _ from 'lodash';
import moment from 'moment';
import { Config, Colors, Enum } from '../../src/global';
import { ChartUtil } from '../../src/utils';

const dateLabels1 = JSON.parse('["2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27","2018-02-27"]');
const prices1 = JSON.parse('[{"shadowH":1335.2,"shadowL":1335,"open":1335.1,"close":1335.1},{"shadowH":1335.2,"shadowL":1335.1,"open":1335.1,"close":1335.2},{"shadowH":1335.2,"shadowL":1335.1,"open":1335.1,"close":1335.1},{"shadowH":1335.2,"shadowL":1335,"open":1335.2,"close":1335.2},{"shadowH":1335.2,"shadowL":1335.1,"open":1335.2,"close":1335.1},{"shadowH":1335.1,"shadowL":1335.1,"open":1335.1,"close":1335.1},{"shadowH":1335.2,"shadowL":1334.9,"open":1335,"close":1334.9},{"shadowH":1334.9,"shadowL":1334.7,"open":1334.9,"close":1334.7},{"shadowH":1334.9,"shadowL":1334.7,"open":1334.7,"close":1334.8},{"shadowH":1335.1,"shadowL":1334.9,"open":1334.9,"close":1335},{"shadowH":1335,"shadowL":1334.9,"open":1335,"close":1334.9},{"shadowH":1334.9,"shadowL":1334.7,"open":1334.8,"close":1334.9},{"shadowH":1335.1,"shadowL":1334.8,"open":1334.9,"close":1335},{"shadowH":1335.1,"shadowL":1334.9,"open":1335,"close":1335},{"shadowH":1335.1,"shadowL":1334.8,"open":1335,"close":1334.8},{"shadowH":1334.9,"shadowL":1334.7,"open":1334.8,"close":1334.7},{"shadowH":1334.7,"shadowL":1334.2,"open":1334.7,"close":1334.3},{"shadowH":1334.5,"shadowL":1334.2,"open":1334.3,"close":1334.3},{"shadowH":1334.5,"shadowL":1334.2,"open":1334.3,"close":1334.4},{"shadowH":1334.5,"shadowL":1334.3,"open":1334.4,"close":1334.5},{"shadowH":1334.5,"shadowL":1334.4,"open":1334.5,"close":1334.5},{"shadowH":1334.5,"shadowL":1334.3,"open":1334.5,"close":1334.3},{"shadowH":1334.3,"shadowL":1334.2,"open":1334.3,"close":1334.3},{"shadowH":1334.4,"shadowL":1334.3,"open":1334.3,"close":1334.3},{"shadowH":1334.4,"shadowL":1334.3,"open":1334.3,"close":1334.4},{"shadowH":1334.7,"shadowL":1334.3,"open":1334.4,"close":1334.6},{"shadowH":1334.6,"shadowL":1334.6,"open":1334.6,"close":1334.6},{"shadowH":1334.9,"shadowL":1334.5,"open":1334.6,"close":1334.9},{"shadowH":1335.1,"shadowL":1334.9,"open":1334.9,"close":1335},{"shadowH":1335,"shadowL":1334.9,"open":1335,"close":1335},{"shadowH":1335,"shadowL":1334.8,"open":1335,"close":1334.8},{"shadowH":1335,"shadowL":1334.7,"open":1334.8,"close":1335},{"shadowH":1335.2,"shadowL":1334.9,"open":1335,"close":1335.2},{"shadowH":1335.3,"shadowL":1335.1,"open":1335.2,"close":1335.2},{"shadowH":1335.3,"shadowL":1335.2,"open":1335.2,"close":1335.2},{"shadowH":1335.2,"shadowL":1335,"open":1335.2,"close":1335},{"shadowH":1335.2,"shadowL":1335,"open":1335,"close":1335.2},{"shadowH":1335.2,"shadowL":1335,"open":1335.2,"close":1335},{"shadowH":1335.2,"shadowL":1335,"open":1335,"close":1335.2},{"shadowH":1335.2,"shadowL":1335.1,"open":1335.2,"close":1335.1}]');
const timeLabels1 = JSON.parse('["16:54","16:55","16:56","16:57","16:58","16:59","17:00","17:01","17:02","17:03","17:04","17:05","17:06","17:07","17:08","17:09","17:10","17:11","17:12","17:13","17:14","17:15","17:16","17:17","17:18","17:19","17:20","17:21","17:22","17:23","17:24","17:25","17:26","17:27","17:28","17:29","17:30","17:31","17:32","17:33"]');
const times1 = JSON.parse('["2018-02-27 16:54:00","2018-02-27 16:55:00","2018-02-27 16:56:00","2018-02-27 16:57:00","2018-02-27 16:58:00","2018-02-27 16:59:00","2018-02-27 17:00:00","2018-02-27 17:01:00","2018-02-27 17:02:00","2018-02-27 17:03:00","2018-02-27 17:04:00","2018-02-27 17:05:00","2018-02-27 17:06:00","2018-02-27 17:07:00","2018-02-27 17:08:00","2018-02-27 17:09:00","2018-02-27 17:10:00","2018-02-27 17:11:00","2018-02-27 17:12:00","2018-02-27 17:13:00","2018-02-27 17:14:00","2018-02-27 17:15:00","2018-02-27 17:16:00","2018-02-27 17:17:00","2018-02-27 17:18:00","2018-02-27 17:19:00","2018-02-27 17:20:00","2018-02-27 17:21:00","2018-02-27 17:22:00","2018-02-27 17:23:00","2018-02-27 17:24:00","2018-02-27 17:25:00","2018-02-27 17:26:00","2018-02-27 17:27:00","2018-02-27 17:28:00","2018-02-27 17:29:00","2018-02-27 17:30:00","2018-02-27 17:31:00","2018-02-27 17:32:00","2018-02-27 17:33:00"]');
const volumns1 = JSON.parse('[43,15,13,40,51,28,76,60,101,88,25,61,36,65,93,40,143,257,124,98,97,71,57,30,65,148,30,79,39,57,44,58,39,59,41,56,79,70,54,79]');

export default class TestKStore {
    constructor() {
        this.reset();
    }
    @action reset() {
        extendObservable(this, {
            data: {
                times: times1,      // times - string - 2017-09-01 10:30:00 
                dateLabels: dateLabels1, // xAxix - 日K(1440)用date - string - 2017-09-01
                timeLabels: timeLabels1, // xAxis - 其他都是用time   - string - 10:30
                prices: prices1,     // yAxis - Candlestick [{shadowH(高), shadowL(低), open(開), close(收)}]
                volumns: volumns1     // yAxix - Bar 
            },
            klineType: Enum.kTypes.one.value, // 1, 5, 15, 30, 1440
            isLoading: true,
            xIndexSelected: null // null 時 Marker - 當touch DisplayModal時, 再將xIndexSelected設為null, Marker就消失
        });
        this.dotSize = null;
        this.totalVolumn = null; // 紀錄最新的TotalVolumn
        this.isReady = 0;
        this.isLoading = false;
        this.ma = {              // 用來計算handleSelect
            five: 0,
            ten: 0,
            twenty: 0,
            thirty: 0
        };
    }
    @computed get candleXAxis() {
        let valueFormatter;
        if (this.klineType === Enum.kTypes.day.value) {
            valueFormatter = this.data.dateLabels.slice();
        } else {
            valueFormatter = this.data.timeLabels.slice();
        }
        return { 
            valueFormatter,
            granularityEnabled: true,
            granularity: 1,
            textColor: processColor(Colors.white), // x軸的字樣

            enabled: false,
            position: 'BOTTOM',
            drawHighlightIndicators: false,
            drawGridLines: false,                   // 不畫格線
            drawAxisLine: false,                    // 不畫X Y 軸線

            axisMaximum: Config.kChartAxisMaximum
        };
    }
    @computed get candleData() {
        // const volumnArr = this.data.volumns.map((volumn) => {
        //     return { y: volumn };
        // });
        
        this.ma.five = this.calculateMa(Enum.ma.five.value, this.data.prices.slice());
        this.ma.ten = this.calculateMa(Enum.ma.ten.value, this.data.prices.slice());
        this.ma.twenty = this.calculateMa(Enum.ma.twenty.value, this.data.prices.slice());
        this.ma.thirty = this.calculateMa(Enum.ma.thirty.value, this.data.prices.slice());
        return {
            candleData: {
                dataSets: [{
                    // prices: [{ shadowH: 101.76, shadowL: 100.4, open: 100.78, close: 101.03 }],
                    values: this.data.prices.slice(),
                    label: '',
                    config: {
                        // value setting 是否顯示該點表示的值
                        drawValues: false,
                        valueTextColor: processColor('white'),
                        valueTextSize: 14,
                        // axisDependency: 'LEFT',
                        valueFormatter: '##.00',
    
                        shadowColor: processColor('black'),
                        shadowWidth: 1,
                        shadowColorSameAsCandle: true,
                        increasingColor: processColor(Colors.candlestickIncreasingColor),

                        increasingPaintStyle: 'fill',
                        decreasingColor: processColor(Colors.candlestickDecreasingColor),
                        drawHighlightIndicators: false,
                        // highlightColor: 'red'
                    }
    
                }],                
            },
            lineData: {
                dataSets: [
                    this.getMADataSets(this.ma.five, Enum.ma.five.text, Enum.ma.five.color),
                    this.getMADataSets(this.ma.ten, Enum.ma.ten.text, Enum.ma.ten.color),
                    this.getMADataSets(this.ma.twenty, Enum.ma.twenty.text, Enum.ma.twenty.color),
                    this.getMADataSets(this.ma.thirty, Enum.ma.thirty.text, Enum.ma.thirty.color),
                    // ChartUtil.getLastestLineDataSet(this.data.prices)
                ]           
            }
        };
    }
    @computed get barXAxis() {
        let valueFormatter;
        if (this.klineType === Enum.kTypes.day.value) {
            valueFormatter = this.data.dateLabels.slice();
        } else {
            valueFormatter = this.data.timeLabels.slice();
        }
        return { 
            valueFormatter,
            granularityEnabled: true,
            granularity: 1,
            
            enabled: true,
            textColor: processColor(Colors.white),
            position: 'TOP',

            drawGridLines: false,               // 不畫格線
            drawAxisLine: false,                // 不畫X Y 軸線
            axisMaximum: Config.kChartAxisMaximum
        };
    }
    @computed get barData() {
        const volumnArr = this.data.volumns.map((volumn) => {
            return { y: volumn };
        });
        // 因為getLastestLineDataSet，因此bar也要加入一樣的空值
        // ChartUtil.setNumOfLastestLine(volumnArr, 0);
        return {
            dataSets: [{
                // values: [{y: 100}, {y: 105}, {y: 102}, {y: 110}, {y: 114}, {y: 109}, {y: 105}, {y: 99}, {y: 95}],
                values: volumnArr,
                label: '',
                config: {
                    color: processColor(Colors.barColor),
                    drawValues: false,
                    valueFormatter: '##.00',
                    // axisDependency: 'RIGHT',

                    // drawHighlightIndicators: true,
                    
                //   barSpacePercent: 40,
                //   barShadowColor: processColor('lightgrey'),
                //   highlightAlpha: 90,
                //   highlightColor: processColor('red'),

                //   shadowColor: processColor('black'),
                //   shadowWidth: 1,
                //   shadowColorSameAsCandle: true,
                //   increasingColor: processColor('#71BD6A'),
                //   increasingPaintStyle: 'fill',
                //   decreasingColor: processColor('#D14B5A')
                }
            }]
        };
    }
    // 畫上 yAxix limitLines
    yAxis = {
        left: {
            enabled: false
        },
        right: {
            valueFormatter: '##.0',
            textColor: processColor(Colors.white), // y軸的字樣
            position: 'INSIDE_CHART',              // y軸顯示在內側
            drawGridLines: false,
            drawAxisLine: false,
        }
    }
    setting = {
        legend: {
            enabled: false
        },
        marker: {
            enabled: false
        }
    }
    getMADataSets(values, label, color) {
        return {   // MA30
            values,
            label,

            config: {
                // 是否畫圖
                visible: true,
                lineWidth: 1,
                drawCubicIntensity: 0.4,
                // axisDependency: 'LEFT',
                // line color
                color: processColor(color),
                drawFilled: false,
                drawCircles: false,
                drawValues: false,
                drawHighlightIndicators: false
            }
        };
    }
    @computed get selectedData() {
        if (!this.xIndexSelected) {
            return null;
        }
        const open = this.data.prices[this.xIndexSelected].open;
        const close = this.data.prices[this.xIndexSelected].close;
        let rate = ((close - open) / open) * 100;
        rate = rate > 0 ? (`+${rate.toFixed(2)}`) : rate.toFixed(2);
        return {
            header: `${this.data.dateLabels[this.xIndexSelected]} ${this.data.timeLabels[this.xIndexSelected]}`, //2017-09-01 10:30
            timeLabel: this.data.timeLabels[this.xIndexSelected],
            open, // 開盤
            shadowH: this.data.prices[this.xIndexSelected].shadowH, // 最高
            shadowL: this.data.prices[this.xIndexSelected].shadowL, // 最低
            close, // 收盤
            rate, // 漲跌
            volumn: this.data.volumns[this.xIndexSelected], // 成交量
            ma5: this.ma.five[this.xIndexSelected] ? this.ma.five[this.xIndexSelected].y : '-',
            ma10: this.ma.ten[this.xIndexSelected] ? this.ma.ten[this.xIndexSelected].y : '-',
            ma20: this.ma.twenty[this.xIndexSelected] ? this.ma.twenty[this.xIndexSelected].y : '-',
            ma30: this.ma.thirty[this.xIndexSelected] ? this.ma.thirty[this.xIndexSelected].y : '-',
        };
    }
    @action handleSelect(entry) {
        this.xIndexSelected = entry.x;
    }
    @action hideMarker() {
        this.xIndexSelected = null;
    }
    // 每一個candle點，包含自己 往前加到maNo 再取平均
    calculateMa(maNo, pricesArr) {
        const values = [];
        for (let i = 0; i < pricesArr.length; i++) {
            if (i < maNo) {
                values.push(null);
            } else {
                let sum = 0;
                for (let j = 0; j < maNo; j++) {
                    sum += pricesArr[i - j].close;
                }
                values.push({ y: _.toNumber((sum / maNo).toFixed(this.dotSize)) });
            }
        }
        return values;
    }
}

