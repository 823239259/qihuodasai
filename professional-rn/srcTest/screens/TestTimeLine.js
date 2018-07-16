import React, { Component } from 'react';
import { View, processColor, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { LineChart, BarChart } from 'react-native-charts-wrapper';
import { Colors, Enum } from '../../src/global';
import { ChartMarkerHighlightIndicator } from '../../src/hoc';

@observer
class TestTimeLine extends Component {
    constructor(props) {
        super(props);
        this.store = this.props.store;
    }
    handleSelect(event) {
        this.store.handleSelect(event.nativeEvent);
    }
    render() {
        return (
            <View style={styles.container}>
                <View style={styles.lineChartContainer}>
                    <LineChart
                        style={styles.chart}
                        
                        data={this.store.lineData}
                        xAxis={this.store.lineXAxis}
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
                        borderColor={processColor('teal')}
                        borderWidth={1}

                        animation={{ durationX: 1000 }}
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

                        animation={{ durationX: 1000 }}
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
}
export default inject('TestTimeLineStore')(ChartMarkerHighlightIndicator(TestTimeLine, Enum.chart.display.time));

const styles = StyleSheet.create({
    container: {
        flex: 1,
        // position: 'relative' // for Marker
    },
    lineChartContainer: {
        flex: 2
    },
    barChartContainer: {
        flex: 1
    },
    chart: {
        flex: 1
    }
});
