import React, { Component } from 'react';
import { View, Text, TouchableWithoutFeedback, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../../../global';

export default class Marker extends Component {
    static propTypes = {
        header: PropTypes.string,
        content: PropTypes.array,
        hide: PropTypes.func,
      };
    
      static defaultProps = {
        header: 'Header'
      };
    // 顯示->決定左or右
    _calcPosition() {
        const { units, xIndexSelected } = this.props;
        const median = units / 2; // 中間值

        const position = { top: Layout.inset };
        if (xIndexSelected > median) {
            position.left = Layout.inset;
        } else {
            position.right = Layout.inset;
        }
        return position;
    }
    _renderHeader() {
        const { textSize } = styles;
        return (
            <View style={styles.header}>
                <Text style={[{ color: Colors.greyText }, textSize]}>{this.props.header}</Text>
            </View>            
        );
    }
    _renderContent() {
        const { textSize, rowText } = styles;
        return this.props.content.map((data, index) => {
            return (
                <View key={`${this.props.header}${index}`} style={styles.row}>
                    <Text style={[rowText, textSize, { color: data.color ? data.color : Colors.white }]}>{data.text}</Text>
                    <Text style={[rowText, textSize, { color: data.color ? data.color : Colors.white }]}>{data.value}</Text>
                </View>
            );
        });
    }
    render() {
        const frameStyle = this._calcPosition();
        return (
            <TouchableWithoutFeedback onPress={() => this.props.hide()}>
                <View style={[styles.container, frameStyle]}>
                    { this._renderHeader() }
                    { this._renderContent() }
                </View>
            </TouchableWithoutFeedback>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        position: 'absolute',
        width: Layout.markerWidth,
        backgroundColor: Colors.markerBackgroundColor,
        borderWidth: 1,
        borderColor: Colors.blackBorder,
        borderRadius: 5,
        paddingHorizontal: 5,
        paddingBottom: 5
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        height: 25
    },
    row: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        height: 15
    },
    rowText: {
        color: Colors.white
    },
    textSize: {
        fontSize: 12
    }
});
