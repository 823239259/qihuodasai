import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { View, Text, ActivityIndicator } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Colors } from '../global';

const size = 14;
@observer
export default class ConnectionStatus extends Component {
    static propTypes = {
        title: PropTypes.string,
        isConnected: PropTypes.bool
    }

    static defaultProps = {
        isConnected: false
    }
    _renderIsConnection() {
        if (this.props.isConnected) {
            return (
                <Icon
                    name="check-circle-o"
                    size={size}
                    color={Colors.connectionStatus.textColor}
                />
            );
        }
        return (
            <ActivityIndicator color={Colors.connectionStatus.textColor} />
            // <Icon
            //     name="spinner"
            //     size={size}
            //     color={Colors.connectionStatus.textColor}
            // />
        );
    }
    render() {
        return (
            <View style={{ flexDirection: 'row' }}>
                <View style={{ justifyContent: 'center', marginRight: 5 }}>
                    { this._renderIsConnection() }
                </View>
                <View style={{ flexDirection: 'row', alignItems: 'center' }}>
                    <Text style={{ color: Colors.connectionStatus.textColor, fontSize: size }}>{this.props.title}</Text>
                </View>
            </View>
        );
    }
}
