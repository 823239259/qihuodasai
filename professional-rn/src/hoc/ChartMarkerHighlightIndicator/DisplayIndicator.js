import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../global';

export default class DisplayIndicator extends Component {
    static propTypes = {
        text: PropTypes.string,
    }
    static defaultProps = {
        text: '',
    }
    setNativeProps(styles) {
        this.ref && this.ref.setNativeProps(styles);
        this.textRef && this.textRef.setNativeProps({ style: { color: (styles.style.backgroundColor === Colors.unHighlightColor) ? Colors.unHighlightColor : Colors.greyText } });
    }
    render() {
        return (
            <View 
                style={[styles.container, this.props.style]}
                ref={(ref) => {
                    this.ref = ref;
                }}
            >
                <Text 
                    style={styles.textStyle}
                    ref={(textRef) => {
                        this.textRef = textRef;
                    }}
                >
                    {this.props.text}
                </Text>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        position: 'absolute',
        width: Layout.displayWidth,
        height: Layout.displayHeight,
        ...Layout.center,
        
        left: 0,
        top: 0,
        
        backgroundColor: Colors.unHighlightColor // 初始顏色
    },
    textStyle: {
        color: Colors.unHighlightColor           // 初始顏色
    }
});
