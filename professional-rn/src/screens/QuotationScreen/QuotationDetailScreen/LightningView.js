/*
  閃電圖
  LineChart 一直更新
*/
import React, { Component } from 'react';
import { View, processColor, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { LineChart } from 'react-native-charts-wrapper';
import { Colors } from '../../../global';

@inject('LightningStore') @observer
export default class LightningView extends Component {
    
    render() {
        const { LightningStore } = this.props;
        
        return (
            <View style={{ flex: 1 }}>
                <View style={styles.container}>
                    <LineChart
                        style={styles.chart}

                        data={LightningStore.lineData}
                        xAxis={LightningStore.lineXAxis}
                        yAxis={LightningStore.lineYAxis}

                        chartDescription={{ text: '' }}
                        chartBackgroundColor={processColor(Colors.chartBackgroundColor)}
                        // decorator
                        legend={LightningStore.lineSetting.legend}
                        marker={LightningStore.lineSetting.marker}
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

const styles = StyleSheet.create({
    container: {
        flex: 1,
        // backgroundColor: Colors.bg
    },
    chart: {
        flex: 1
    }
});
