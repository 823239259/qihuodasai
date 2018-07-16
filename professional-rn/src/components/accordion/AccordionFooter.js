import React, { Component } from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../global';

export default class AccordionFooter extends Component {
    static propTypes = {
        count: PropTypes.number
    }
    static defaultProps = {
        count: 0,
    }
    render() {
        return (
            // 多一層是為了background顏色
            <View style={{ backgroundColor: Colors.accordionFooterColor, height: Layout.accordionColumnHeight, justifyContent: 'center' }}>
                <View style={[Layout.center, { flexDirection: 'row', justifyContent: 'space-between', paddingHorizontal: 15, width: Layout.screenWidth }]}>
                    <Text style={{ color: Colors.lightTextColor }}>{'合计'}</Text>
                    <Text style={{ color: Colors.lightTextColor }}>{`${this.props.count} 单`}</Text>
                </View>
            </View>    
        );
    }
}
