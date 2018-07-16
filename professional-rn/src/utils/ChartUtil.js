import { processColor } from 'react-native';
import { Config } from '../global';

// 1. line: 增加一條平行的最新線，最新線的值 - value
// 2. bar:  增加都是value = 0
function setNumOfLastestLine(arr, value) {
    let chartLastestLine = Config.chartLastestLine;
    // 當<就，變成同等長度的線
    if (arr.length < chartLastestLine) {
        chartLastestLine = arr.length;
    }
    // 表示資料筆數可能遠大於40，目前分時圖，沒有限制筆數，但閃電圖、K線圖有限制40(chartDataLimited)
    if (arr.length > Config.chartDataLimited) {
        chartLastestLine = arr.length / Config.TimeChartPortion;
    }

    for (let i = 0; i < chartLastestLine; i++) {
        arr.push({ y: value });
    }
}
export default {
    getLastestLineDataSet(dataArrObservable) {
        const lastestLineArr = dataArrObservable.map(() => {
            return null;
        });
        // get latest time
        const latestIndex = dataArrObservable.length - 1;
        const lastest = dataArrObservable[latestIndex];
        // 連接最新點 - real line 跟 latest line - copy chartLastestLine latest time
        lastestLineArr[latestIndex] = { y: lastest };
        // line: 增加一條平行的最新線，最新線的值 - value
        setNumOfLastestLine(lastestLineArr, lastest);
        
        return {
            values: lastestLineArr,
            label: '',
            
            config: {
                lineWidth: 1,
                drawCircles: false,
                // highlightColor: processColor('red'),
                color: processColor('orange'),
                drawFilled: false,
                drawValues: false,
                drawHighlightIndicators: false,
                // valueTextSize: 15,
                // valueFormatter: "##.000",
                dashedLine: {
                  lineLength: 5,
                  spaceLength: 5
                }
              }
        };
    },
    setNumOfLastestLine,
    getValueFormatter(dotSize) {
        console.log(`dotSize: ${dotSize}`);
        let valueFormatter = '##';
        if (dotSize) {
            valueFormatter = `${valueFormatter}.`;
        }
        for (let i = 0; i < dotSize; i++) {
            valueFormatter = `${valueFormatter}0`;
        }
        console.log(`getValueFormatter ${valueFormatter}`);
        return valueFormatter;
    },
    getVolumnValueFormatter() {
        return '#';
    }
};
