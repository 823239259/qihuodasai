/*
    K線
    CombinedChart -> 1.CandlestickChart 2.BarChart 3. LineChart(MA)
*/

import { action, computed, extendObservable, comparer, observable } from 'mobx';
import { processColor } from 'react-native';
import _ from 'lodash';
import moment from 'moment';
import { Config, Colors, Enum } from '../../global';
import { ChartUtil } from '../../utils';

let isActive = false;
export default class KStore {
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
                times: [],      // times - string - 2017-09-01 10:30:00 
                dateLabels: [], // xAxix - 日K(1440)用date - string - 2017-09-01
                timeLabels: [], // xAxis - 其他都是用time   - string - 10:30
                prices: [],     // yAxis - Candlestick [{shadowH(高), shadowL(低), open(開), close(收)}]
                volumns: []     // yAxix - Bar 
            },
            klineType: Enum.kTypes.one.value, // 1, 5, 15, 30, 1440
            isLoading: true,
            xIndexSelected: null // null 時 Marker - 當touch DisplayModal時, 再將xIndexSelected設為null, Marker就消失
        });
        this.totalVolumn = null; // 紀錄最新的TotalVolumn
        this.isReady = 0;
        this.isLoading = true;
        this.ma = {              // 用來計算handleSelect
            five: 0,
            ten: 0,
            twenty: 0,
            thirty: 0
        };
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get candleXAxis() {
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

            axisMaximum: 45
        };
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get candleData() {        
        this.ma.five = this.calculateMa(Enum.ma.five.value, this.data.prices.slice());
        this.ma.ten = this.calculateMa(Enum.ma.ten.value, this.data.prices.slice());
        this.ma.twenty = this.calculateMa(Enum.ma.twenty.value, this.data.prices.slice());
        this.ma.thirty = this.calculateMa(Enum.ma.thirty.value, this.data.prices.slice());
        return {
            candleData: {
                dataSets: [{
                    values: this.data.prices.slice(),
                    label: '',
                    config: {
                        // value setting 是否顯示該點表示的值
                        drawValues: false,
                        valueTextColor: processColor('white'),
                        valueTextSize: 14,
                        valueFormatter: ChartUtil.getValueFormatter(this.dotSize),
    
                        shadowColor: processColor('black'),
                        shadowWidth: 1,
                        shadowColorSameAsCandle: true,
                        increasingColor: processColor(Colors.candlestickIncreasingColor),

                        increasingPaintStyle: 'fill',
                        decreasingColor: processColor(Colors.candlestickDecreasingColor),
                        drawHighlightIndicators: false,
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
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get barXAxis() {
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
            axisMaximum: 45,
        };
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get barData() {
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
                    valueFormatter: ChartUtil.getValueFormatter(this.dotSize),
                }
            }]
        };
    }
    @computed get candleYAxis() {
        return {
            left: {
                enabled: false,
            },
            right: {
                valueFormatter: ChartUtil.getValueFormatter(this.dotSize),
                textColor: processColor(Colors.white), // y軸的字樣
                position: 'INSIDE_CHART',              // y軸顯示在內側
                drawGridLines: false,
                drawAxisLine: false,
            }
        };
    }
    barYAxis = {
        left: {
            enabled: false,
        },
        right: {
            valueFormatter: ChartUtil.getVolumnValueFormatter(),
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
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get selectedData() {
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
    @action start(jsonData, dotSize) {
        this.reset();
        this.dotSize = dotSize;
        const dataSent = jsonData.data.Lines;
        // 只取最新的chartDataLimited筆資料 -> 修改原本的寫法 xxArr.slice(): 1.他要把全部資料全跑完 2.再另外取出最新的40筆 
        // 確定資料是照順序排，那只要取最後chartDataLimited筆資料就好了
        this.klineType = jsonData.data.period;
        // 日K資料不多
        let startIndex = 0;
        if (dataSent.length >= Config.chartDataLimited) {
            startIndex = dataSent.length - Config.chartDataLimited;
        }
        let j = 0;
        for (let i = startIndex; i < dataSent.length; i++) {
            const dateTimeString = dataSent[i][Enum.chartDataColumns.DateTimeStampIndex];
            this.data.times[j] = dateTimeString; // 2017-09-01 10:30:00 
            const dateTimeArr = dateTimeString.split(' ');
            this.data.dateLabels[j] = dateTimeArr[0];   // 2017-09-01
            const timeString = dateTimeArr[1]; // 10:30:00 
            const timeArr = timeString.split(':');
            this.data.timeLabels[j] = `${timeArr[0]}:${timeArr[1]}`; // 10:30  
            this.data.prices[j] = {
                shadowH: _.toNumber((dataSent[i][Enum.chartDataColumns.HighPriceIndex]).toFixed(this.dotSize)),
                shadowL: _.toNumber((dataSent[i][Enum.chartDataColumns.LowPriceIndex]).toFixed(this.dotSize)),
                open: _.toNumber((dataSent[i][Enum.chartDataColumns.OpenPriceIndex]).toFixed(this.dotSize)),
                close: _.toNumber((dataSent[i][Enum.chartDataColumns.LastPriceIndex]).toFixed(this.dotSize))
            };
            this.data.volumns[j] = _.toNumber((dataSent[i][Enum.chartDataColumns.VolumeIndex]).toFixed(this.dotSize));
            j++;
        }
        this.isLoading = false;
    }
    // klineType = 1 -> 除去秒數部分
    @action add(param) {
        this.isReady++;
        if (this.isReady < Config.countOnRtnQuote || this.klineType === Enum.kTypes.day.value) {
            return;
        }
        const dataSent = param;
        const oldestDateTimeString = this.data.times[this.data.times.length - 1];
        const oldTime = Math.round(moment(oldestDateTimeString).valueOf() / 1000);
        const newTime = this.getRangeTime(dataSent.time_flag);
        const lastPrice = _.toNumber(dataSent.last.toFixed(this.dotSize));
        if (oldTime === newTime) {
            const length = this.data.prices.length;
            let shadowH = this.data.prices[length - 1].shadowH;
            let shadowL = this.data.prices[length - 1].shadowL;
            const open = this.data.prices[length - 1].open;
            const close = lastPrice;
            if (lastPrice >= shadowH) {
                shadowH = lastPrice;
            }
            if (shadowL >= shadowH) {
                shadowL = lastPrice;
            }
            this.data.prices[length - 1] = { shadowH, shadowL, open, close };
            this.data.volumns[this.data.volumns.length - 1] += this.getVolumn(dataSent.volume);

        } else {
            // 2017-09-01 10:30:00('YYYY-MM-DD HH:mm:ss') -> 不需要ss
            const dateTimeString = moment.unix(newTime).format('YYYY-MM-DD HH:mm');
            const dateTimeArr = dateTimeString.split(' ');
            if (this.data.times.length === Config.chartDataLimited) {
                this.data.times.shift();
                this.data.dateLabels.shift();
                this.data.timeLabels.shift();
                this.data.prices.shift();
                this.data.volumns.shift();
            }
            this.data.times.push(dateTimeString);
            this.data.dateLabels.push(dateTimeArr[0]);
            this.data.timeLabels.push(dateTimeArr[1]);
            this.data.prices.push({ shadowH: lastPrice, shadowL: lastPrice, open: lastPrice, close: lastPrice });
            this.data.volumns.push(this.getVolumn(dataSent.volume));
        }
    }
    @action handleSelect(entry) {
        this.xIndexSelected = entry.x;
    }
    @action hideMarker() {
        this.xIndexSelected = null;
    }
    /*
        我決定把每一次的OnRtnQuote - TotalVolumn記錄下來 判斷和下一次的修改量 
        if 若在一分鐘內的變化 
                都放在最後的bar(volumn上) 
        else 
            超出一分鐘 就放在下一個bar
    */
    getVolumn(totalVolumn) {
        let newVolumn = 0;
        if (this.totalVolumn === null) {
            this.totalVolumn = totalVolumn;
            return newVolumn; //表示volumn無變化
        }
        newVolumn = totalVolumn - this.totalVolumn;
        this.totalVolumn = totalVolumn; //更新最新的totalVolumn
        return newVolumn;
    }
    // 消除range以下的數字，回傳是seconds
    getRangeTime(dateTimeString) {
        let range = ''
        switch(this.klineType){
            case 'TIME_SHARING': range = 0;
            break;
            case 'KLINE_1MIN': range = 1;
            break;
            case 'KLINE_5MIN': range = 5;
            break;
            case 'KLINE_15MIN': range = 15;
            break;
            case 'KLINE_30MIN': range = 30;
            break;
            case 'KLINE_1DAY': range = 1440;
            break;
        }
        const milliseconds = moment(dateTimeString).valueOf();
        const onlySeconds = Math.round(milliseconds / 1000);
        // 先除以 (60 * range)
        const timeFractionRemoved = parseInt(onlySeconds / (60 * range));
        return timeFractionRemoved * 60 * range;
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

