import React, { Component } from 'react';
import { View } from 'react-native';
import { ChartMarkerHighlightIndicator } from '../../src//hoc'; 

class TestPanResponder extends Component {
    render() {
        return <View />;
    }
}
export default ChartMarkerHighlightIndicator(TestPanResponder, null, null);

