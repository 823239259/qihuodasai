import React, { Component } from 'react';
import { StyleSheet, View, Text, ViewPropTypes, Animated, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Colors, Layout } from '../../global';

export default class ButtonCaret extends Component {
  render() {
    const { textStyle, color, fontWeight } = this.props;
    return (
        <View style={{ flexDirection: 'row' }}>
            <Text style={[textStyle, { color, fontWeight }]}>{this.props.buttonText}</Text>
            <View style={{ position: 'relative', marginLeft: 5 }}>
                <Icon
                    name="caret-right" 
                    size={textStyle.fontSize} 
                    color={Colors.kCaretColor}
                    style={{ transform: [{ rotate: '45deg' }], bottom: -7 }} 
                />
            </View>
        </View>      
      );
  }
}
