/*
    分時
    1.LineChart
    2.BarChart

    extendObservable: https://stackoverflow.com/questions/39313061/mobx-reset-all-store-observables-back-to-initial-state
*/

import { action, computed, extendObservable, comparer, observable } from 'mobx';
import { processColor } from 'react-native';
import Color from 'color';
import _ from 'lodash';
import moment from 'moment';
import { Config, Colors, Enum } from '../../global';
import { ChartUtil } from '../../utils';

let isActive = false;
export default class TimeStore {
    constructor() {
        this.reset();
        this.startShining();
    }
    setIsActive(active) {
        isActive = active;
    }
    startShining() {
        setInterval(() => {
            this.isShining = !this.isShining;
        }, 1000);
    }
    @observable isShining = false;
    @observable dotSize = null;
    @action reset() {
        extendObservable(this, {
            data: {
                times: [],       // 為了add()，比較更新資料 - string - 2017-09-01 10:30:00 
                timeLabels: [],  // xAxix 分鐘 - 16:22   -> add lastPrices1
                prices: [],      // LineChart -yAxis    -> add lastPrices1
                volumns: []      // BarChart - yAxis    -> add freshVolume
            },
            isLoading: true,
            xIndexSelected: null // null 時 Marker - 當touch DisplayModal時, 再將xIndexSelected設為null, Marker就消失
        });
        this.totalVolumn = null;
        this.isReady = 0;
        this.totalVolumn = null;    // 紀錄最新的TotalVolumn
        this.preSettlePrice = null; // 昨日結算價 - 也就是 - 今日開盤價
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get lineXAxis() {
        return { 
            valueFormatter: this.data.timeLabels.slice(),
            granularityEnabled: true,
            granularity: 1,
            textColor: processColor(Colors.white), // x軸的字樣
            
            enabled: false,
            position: 'BOTTOM',
            drawHighlightIndicators: false,
            drawGridLines: false,                   // 不畫格線
            drawAxisLine: false,                    // 不畫X Y 軸線
        };
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get lineData() {
        return {
            dataSets: [this.getRealLineDataSet(), this.getCircleLatestDataSet(), ChartUtil.getLastestLineDataSet(this.data.prices)]
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
                // valueTextColor: processColor('white'),
                // valueTextSize: 14,
                drawHighlightIndicators: false,
            }
        };
    }
    getCircleLatestDataSet() {
        // 我只要取最新一點畫圓
        const lastIndex = this.data.prices.length - 1;
        let latestPrice = 0;
        const circleArr = this.data.prices.map((price, index) => {
            if (index === lastIndex) { 
                latestPrice = price;
                return { y: latestPrice };
            }
            return null;
        });
        // 並計算一點的顏色
        // 1. 用最新價計算 - 先保留 可能會再改回去
        // let latestCircleColor = Colors.lighteningLineColor;
        // if (this.preSettlePrice !== null) {
        //     // 如果最新价>当天的最高价，同时有行情变化的时候，圆点以红色闪动
        //     if (latestPrice > this.preSettlePrice) {
        //         latestCircleColor = Colors.red;
        //     } else {
        //         // 如果最新价<当天的最低价，同时有行情变化的时候，圆点以绿色闪动
        //         latestCircleColor = Colors.green;
        //     }
        // }
        // 2. 最新的一點，和前一點做比較
        const lastSecondPrice = this.data.prices[this.data.prices.length - 2];
        let latestCircleColor = Colors.lighteningLineColor;
        // 最新價 > 上一點的價錢，圆点以红色闪动
        if (latestPrice > lastSecondPrice) {
            latestCircleColor = Color(Colors.red);
        } else {
            // 最新價 > 上一點的價錢，圆点以绿色闪动
            latestCircleColor = Color(Colors.green);
        }
        return {
           values: circleArr,
           label: '',
           config: {
               // 是否畫圖
               visible: true,
               // line color
               color: processColor(latestCircleColor),
               // fill color
               drawFilled: false,
               // circle settings
               drawCircles: true,
               circleRadius: this.isShining ? 10 : 5,
               circleColor: processColor(this.isShining ? latestCircleColor.lighten(1).alpha(0.3) : latestCircleColor.string()),
               circleHoleColor: processColor(latestCircleColor.string()),
               // value setting 是否顯示該點表示的值
               drawValues: true,
               valueTextColor: processColor('white'),
               valueTextSize: 14,
               valueFormatter: ChartUtil.getValueFormatter(this.dotSize),
               drawHighlightIndicators: false
           }
       };
   }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get barXAxis() {
        return { 
            valueFormatter: this.data.timeLabels.slice(),
            granularityEnabled: true,
            granularity: 1,
            
            enabled: true,
            textColor: processColor(Colors.white),
            position: 'TOP',

            drawGridLines: false,               // 不畫格線
            drawAxisLine: false,                // 不畫X Y 軸線
        };
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get barData() {
        const valuesArr = this.data.volumns.map((volumn) => {
            return { y: volumn };
        });
        // 因為getLastestLineDataSet，因此bar也要加入一樣的空值
        ChartUtil.setNumOfLastestLine(valuesArr, 0);
        return {
                dataSets: [
                    {
                        values: valuesArr,
                        label: '',
                        config: {
                        color: processColor(Colors.barColor),
                        drawValues: false,
                        valueFormatter: ChartUtil.getValueFormatter(this.dotSize)                        
                        }
                    }
                ]
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
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get selectedData() {
        // 第二個判斷是，多加上了LastestLine畫最新點的延伸線，此時資料不存在，也不需要顯示marker
        if (!this.xIndexSelected || (this.xIndexSelected >= this.data.timeLabels.length)) {
            return null;
        }
        return {
            timeLabel: `${this.data.timeLabels[this.xIndexSelected]}`, //10:30
            price: this.data.prices[this.xIndexSelected],
            volumn: this.data.volumns[this.xIndexSelected]
        };
    }
    // OnRspQryHistory 畫出歷史數據圖表
    // 畫出所有的點 - 資料可能會穿過兩日 - 畫圖的方式 其實只是照著給的資料順序
    @action start(jsonData, dotSize) {
        this.dotSize = dotSize;
        const dataSent = jsonData.Parameters.Data;
        // 不取超出observableArrLimited個
        let startIndex = 0;
        if (dataSent.length >= Config.observableArrLimited) {
            startIndex = dataSent.length - Config.observableArrLimited;
        }
        let j = 0;
        for (let i = startIndex; i < dataSent.length; i++) {
            const dateTimeString = dataSent[i][Enum.chartDataColumns.DateTimeStampIndex];
            this.data.times[j] = dateTimeString;
            this.data.timeLabels[j] = this.getHourMin(dateTimeString);  // 時:分 (16:22)
            // toFixed(format to string) --> _.toNumber()
            this.data.prices[j] = _.toNumber((dataSent[i][Enum.chartDataColumns.LastPriceIndex]).toFixed(this.dotSize));
            this.data.volumns[j] = _.toNumber((dataSent[i][Enum.chartDataColumns.VolumeIndex]).toFixed(this.dotSize));
            j++;
        }
        this.isLoading = false;
    }
    /*
        1.
        無條件捨去 秒數
        但還是要更新當前 分鐘數 的資料
        2.
        分鐘數 不同
        加入一筆新的
        若超過observableArrLimited筆數 得移除 最舊的那一筆
    */
    @action add(param) {
        this.isReady++;
        if (this.isReady < Config.countOnRtnQuote) {
            return;
        }
        const dataSent = param;
        // remove second, millisecond
        const newDateTimeString = moment(dataSent.DateTimeStamp).seconds(0).milliseconds(0).format('YYYY-MM-DD HH:mm:ss');
        // 最後bar的時間
        const oldDateTimeString = this.data.times[this.data.times.length - 1];

        const lastPrice = _.toNumber(dataSent.LastPrice.toFixed(this.dotSize));
        if (oldDateTimeString === newDateTimeString) {
            this.data.prices[this.data.prices.length - 1] = lastPrice;
            this.data.volumns[this.data.volumns.length - 1] += this.getVolumn(dataSent.TotalVolume);
        } else {
            this.data.prices.push(lastPrice);
            this.data.volumns.push(this.getVolumn(dataSent.TotalVolume));
            this.data.times.push(newDateTimeString);
            this.data.timeLabels.push(this.getHourMin(newDateTimeString));
        }
        // 用來計算最新點的顏色
        this.preSettlePrice = dataSent.PreSettlePrice;
        // console.log(`TimeStore - add() price: ${lastPrice}, volumn: ${this.getVolumn(dataSent.TotalVolume)}, time: ${newDateTimeString}, timeLabel: ${this.getHourMin(newDateTimeString)}`);
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
    // 只取 時：分
    getHourMin(dateTimeString) {
        const dateTimeArr = dateTimeString.split(' ');
        const timeStr1 = dateTimeArr[1].split(':');
        return `${timeStr1[0]} : ${timeStr1[1]}`;
    }
}

