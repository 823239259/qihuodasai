import React, { Component } from 'react';
import { View, ScrollView } from 'react-native';
import Row from './components/Row';

export default class TestScreen extends Component {
    constructor(props) {
        super(props);
        this.rows = [
            { title: 'TodoDemo', screenName: 'todoScreen.TodoAdd' },
            { title: 'TestFatArrow', screenName: 'test.TestFatArrow' },
            { title: 'ArrayUtil', screenName: 'test.TestArrayUtil' },
            { title: 'Field', screenName: 'test.TestField' },
            { title: 'TestRefreshableFlatList', screenName: 'test.TestRefreshableFlatList' },
            { title: 'TestAsyncStorage', screenName: 'test.TestAsyncStorage' },
            { title: 'TestPromise', screenName: 'test.TestPromise' },
            { title: 'TouchableSort', screenName: 'test.TestTouchableSort' },
            { title: 'TestSwipeout', screenName: 'test.TestSwipeout' },
            { title: 'TestLightning', screenName: 'test.TestLightning' },
            { title: 'TestTimeLine', screenName: 'test.TestTimeLine' },
            { title: 'TestPanResponder', screenName: 'test.TestPanResponder' },
            { title: 'TestKView', screenName: 'test.TestKView' }
        ];
    }
    toScreen = (screenName, title) => {
        this.props.navigator.push({
            screen: screenName,
            title,
            backButtonTitle: '', //返回
            animationType: 'slide-horizontal'
        });   
    }
    render() {
        return (
            <View style={{ flex: 1 }}>
                <ScrollView>
                { 
                    this.rows.map((data, index) => {
                        return <Row key={index} title={data.title} screenName={data.screenName} onPress={this.toScreen} />;
                    })
                }
                </ScrollView>
            </View>
        );
    }
}
