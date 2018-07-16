import React, { Component } from 'react';
import { View, TouchableWithoutFeedback, ScrollView, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../global';

const styles = StyleSheet.create({
    headerTextStyle: {
        color: Colors.greyText
    },
    contentTextStyle: {
        color: Colors.white
    },
    columnStyle: {
        // flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        height: Layout.accordionColumnHeight
    }
});
export default class Accordion extends Component {
    static propTypes = {
        headers: PropTypes.array,       // [{ text, style }]
        
        scrollEnabled: PropTypes.bool,
        expandIndex: PropTypes.number,
        
        count: PropTypes.number,
        renderFooter: PropTypes.any,

        totalWidth: PropTypes.number,

        headerTextStyle: PropTypes.oneOfType([PropTypes.number, PropTypes.object]),
        contentTextStyle: PropTypes.oneOfType([PropTypes.number, PropTypes.object]),

        
    }
    static defaultProps = {
        scrollEnabled: true,
        count: 0,
        totalWidth: 0,

        headerTextStyle: styles.headerTextStyle,
        contentTextStyle: styles.contentTextStyle
    }
    constructor(props) {
        super(props);
        this.state = {
            expandIndex: -1,
            scrollX: 0
        };
    }
    _expand(expandIndex) {
        if (this.state.expandIndex === expandIndex) {
            this.setState({ expandIndex: -1 });
        } else {
            this.setState({ expandIndex });
        }
    }
    _renderFooter() {
        if (this.props.renderFooter && this.props.count > 1) {
            return React.cloneElement(this.props.renderFooter(), { count: this.props.count, scrollX: this.state.scrollX });
        }
    }
    _renderHeader() {   
        return this.props.headers.map((header, index) => {
            if (header.style.width) {
                this.totalWidth += header.style.width;
            }
            return (
                <TouchableWithoutFeedback key={index}>
                    <View style={[styles.columnStyle, header.style]}>
                        <Text style={[this.props.headerTextStyle, header.textStyle && header.textStyle]}>{header.text}</Text>
                    </View>
                </TouchableWithoutFeedback>
            );
        });
    }
    render() {
        let count = -1;
        const headerView = this._renderHeader();
        return (
            <ScrollView 
                contentContainerStyle={{ width: this.totalWidth }}
                horizontal={true}
                directionalLockEnabled={false}
                scrollEventThrottle={16}
                onScroll={(e) => this.setState({ scrollX: e.nativeEvent.contentOffset.x })}
                scrollEnabled={this.props.scrollEnabled}
            >
                <View>
                    <View style={{ flexDirection: 'row' }}>
                        { headerView }
                    </View>
                    <ScrollView 
                        contentContainerStyle={{ flexGrow: 1 }}
                        scrollEnabled={this.props.scrollEnabled}
                    >
                        { 
                            React.Children.map(this.props.children, (child) => {
                                let isExpand = false;
                                count++;
                                if (count === this.state.expandIndex) {
                                    isExpand = true;
                                }
                                // 為了讓Accordion, AccordionItem，設定一樣的textStyle, columnStyle, 還有抓到最外層設定給的個別header style
                                return React.cloneElement(child, { 
                                    stylesHeader: { textStyle: this.props.contentTextStyle, columnStyle: styles.columnStyle, style: this.props.headers.map((header) => { return header.style; }) },
                                    index: count,
                                    isExpand,
                                    expand: (expandIndex) => this._expand(expandIndex),
                                    scrollX: this.state.scrollX
                                });
                            })
                        }
                        { this._renderFooter() }
                    </ScrollView>
                </View>
            </ScrollView>
        );
    }
}
