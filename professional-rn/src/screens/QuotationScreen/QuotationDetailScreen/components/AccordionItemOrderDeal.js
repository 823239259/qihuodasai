import React, { Component } from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { TextCenter } from '../../../../components';

export default class AccordionItemOrderDeal extends Component {
    static propTypes = {
        text: PropTypes.string.isRequired
    }
    render() {
        return (
            <View style={{ flexDirection: 'row' }}>
                <TextCenter text={this.props.text} />
            </View>
        );
    }
}
