import React, { Component } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import PropTypes from 'prop-types';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Colors, Layout } from '../../../src/global';

export default class Row extends Component {
    static propTypes = {
        screenName: PropTypes.string,
        title: PropTypes.string,
        onPress: PropTypes.func
    }
    static defaultProps = {
        title: 'title'
    }
    press = () => {
        const { screenName, title, onPress } = this.props;
        if (onPress) {
            onPress(screenName, title);
            return;
        }
        alert('press');
    }
    render() {
        const { title } = this.props;
        return (
            <TouchableOpacity style={styles.row} onPress={this.press} >
                <View style={styles.left}>
                    <View style={[Layout.center, { marginLeft: 20 }]}>
                        <Text style={[{ color: Colors.greyText }]}>{title}</Text>
                    </View>
                </View>
                <View style={styles.right}>
                    <Icon
                        name="chevron-right"
                        size={15}
                        color={Colors.greyIcon}
                    />
                </View>
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    row: {
        height: 50,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 20,
        
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.greyIcon
    },
    left: {
        flexDirection: 'row',
        ...Layout.center
    },
    right: {
        ...Layout.center
    }
});
