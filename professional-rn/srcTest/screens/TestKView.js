/*
    分時
    CombinedChart -> 1.CandlestickChart 2.BarChart 3. LineChart(MA)
*/
import React, { Component } from 'react';
import { View, processColor, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { CombinedChart, BarChart } from 'react-native-charts-wrapper';
import { Loading } from '../../src/components';
import { Colors, Enum } from '../../src/global';
import { ChartMarkerHighlightIndicator } from '../../src/hoc';

@observer
class TestKView extends Component {
    constructor(props) {
        super(props);
        this.store = this.props.TestKStore;
    }
    _renderLoading() {
        return (
            <Loading />
        );
    }
    handleSelect(event) {
        this.store.handleSelect(event.nativeEvent);
    }
    _renderContent() {
        return (
            <View style={styles.container}>
                <View style={styles.combinedChartContainer}>
                    <CombinedChart
                    style={styles.chart}
                    data={this.store.candleData}
                    xAxis={this.store.candleXAxis}
                    yAxis={this.store.yAxis}

                    chartDescription={{ text: '' }}
                    chartBackgroundColor={processColor(Colors.chartBackgroundColor)}
                    // decorator
                    legend={this.store.setting.legend}
                    marker={this.store.setting.marker}
                    // grid - false後 之後的設定也沒用 留著做紀錄
                    drawGridBackground={false}
                    // borders - false後 之後的設定也沒用 留著做紀錄
                    drawBorders={false}
                    // borderColor={processColor(Colors.chartBorderColor)}
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

                    onSelect={this.handleSelect.bind(this)}
                    />
                </View>
                <View style={styles.barChartContainer}>
                    <BarChart
                        style={styles.chart}
                        data={this.store.barData}
                        xAxis={this.store.barXAxis}
                        yAxis={this.store.yAxis}
                        chartDescription={{ text: '' }}
                        chartBackgroundColor={processColor(Colors.chartBackgroundColor)}
                        // decorator
                        legend={this.store.setting.legend}
                        marker={this.store.setting.marker}
                        // grid - false後 之後的設定也沒用 留著做紀錄
                        drawGridBackground={false}
                        // borders - false後 之後的設定也沒用 留著做紀錄
                        drawBorders={false}
                        borderColor={processColor('red')}
                        borderWidth={1}

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

                        onSelect={this.handleSelect.bind(this)}
                    />
                </View>
            </View>
        );
    }
    render() {
        return this.store.isLoading ? this._renderLoading() : this._renderContent();
    }
}
export default inject('TestKStore')(ChartMarkerHighlightIndicator(TestKView, Enum.chart.display.k));

const styles = StyleSheet.create({
    container: {
        flex: 1,
        position: 'relative' // for Marker
    },
    combinedChartContainer: {
        flex: 2
    },
    barChartContainer: {
        flex: 1
    },
    chart: {
        flex: 1
    }
});
