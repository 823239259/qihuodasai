import React, { Component } from 'react';
import { observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { View, Text, TouchableOpacity, LayoutAnimation, ScrollView } from 'react-native';
import { Colors, Layout } from '../../global';

@observer
export default class AccordionItem extends Component {
    static propTypes = {
        data: PropTypes.object,
        keys: PropTypes.array,
        // 會先使用header設定的styles
        stylesHeader: PropTypes.object,
        // 自動個別column的styles
        style: PropTypes.object,

        index: PropTypes.number,
        // from Accordion，為了控制只展開一個AccordionItem，並合起其他AccordionItem
        isExpand: PropTypes.bool,
        expand: PropTypes.func,
        scrollX: PropTypes.number
    }
    static defaultProps = {
        isExpand: false
    }
    componentWillReceiveProps(nextProps) {
        if (!nextProps) {
            return;
        }
        if (this.props.children && nextProps.scrollX && this.props.isExpand) {
            this._scrollView.scrollTo({ x: -nextProps.scrollX, animated: false });
        }
    }
    // Avoid Warning: Overriding previous layout animation with new one before the first began
    // 用Animation 到現在幾乎沒任何好處... 常常會有效能變差和一堆warning產生
    // componentWillUpdate() {
    //     // LayoutAnimation.easeInEaseOut();
    //     LayoutAnimation.configureNext({
    //         duration: 500,
    //         create: {
    //           type: LayoutAnimation.Types.linear,
    //           property: LayoutAnimation.Properties.opacity,
    //         },
    //         update: {
    //           type: LayoutAnimation.Types.easeInEaseOut,
    //         }
    //     });
    // }
    _getTextColor(key) {
        if (!key.color) {
            return null;
        }
        return { color: this.props.data[key.color] };
    }
    _renderButtons() {
        if (this.props.children && this.props.isExpand) {
            return (
                <ScrollView 
                    contentContainerStyle={{ width: Layout.screenWidth }}
                    horizontal={true}
                    directionalLockEnabled={false}
                    scrollEventThrottle={16}
                    ref={(scrollView) => this._scrollView = scrollView}
                >
                    { this.props.children }
                </ScrollView>
            );
        }
    }
    _renderContent() {
        const { data, keys, stylesHeader } = this.props;
        let count = -1;

        return keys.map((key, index) => {
            count++;
            return (
                <View key={index} style={[stylesHeader.columnStyle, stylesHeader.style[count], this.props.style && this.props.style]}>
                    <Text style={[stylesHeader.textStyle, this._getTextColor(key), key.textStyle && key.textStyle]}>{data[key.name]}</Text>
                </View>
            );
        });
    }
    render() {
        const { data } = this.props;
        // 反手時，會先從holdPositions 清除掉一筆HoldPosition，再新增一筆新的
        // 清除時，這時data(holdPosition/designate...) 變成undefined
        if (!data) {
            return <View />;
        }
        // 沒有children就表示沒有操作可以選，但是依然需要Touchable，才能夠scroll
        return (
            <View>
                <TouchableOpacity 
                    style={{ backgroundColor: this.props.isExpand ? Colors.accordionItemActiveBackgroundColor : Colors.accordionItemBackgroundColor, flexDirection: 'row', borderBottomWidth: 1, borderColor: Colors.black }}
                    onPress={this.props.children ? () => this.props.expand(this.props.index) : null}
                >
                    { this._renderContent() }
                </TouchableOpacity>
                { this._renderButtons() }
            </View>
        );        
    }
}

