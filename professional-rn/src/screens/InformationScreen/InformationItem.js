import React, { Component } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Dialog } from '../../components';
import { Colors, Layout } from '../../global';

export default class InformationItem extends Component {
    constructor(props) {
        super(props);
        this.state = { isDialogVisible: false };
    }
    _onDetailPress = () => {
        this.setState({ isDialogVisible: true });
    }
    _renderText() {
        let text = this.props.text;
        if (text.length > Layout.informationTextLength) {
            text = text.substring(0, Layout.informationTextLength);
            text = text.replace(/(\r\n|\n|\r)/gm,'');   // remove newline
            text = `${text}.....`;
            return (
                <View>
                    <Text style={{ fontSize: Layout.informationFontSize, color: Colors.greyText, fontFamily: 'PingFangSC-Light' }}>{text}</Text>
                    <View style={{ flexDirection: 'row', justifyContent: 'flex-end', alignItems: 'flex-end' }}>
                        <TouchableOpacity style={{ width: 50, alignItems: 'center' }} onPress={this._onDetailPress}>
                            <Text style={{ textAlign: 'right', color: Colors.informationSeparator }}>{'详情'}</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            );
        }
        return <Text style={{ color: Colors.greyText, fontFamily: 'PingFangSC-Light' }}>{text}</Text>;
    }
    render() {
        return (
            <View style={styles.container}>
                <View>
                    <Text style={{ color: Colors.greyIcon }}>{this.props.dateString}</Text>
                    <View style={{ flexDirection: 'row', justifyContent: 'center' }}>
                        <Text style={{ color: Colors.greyText }}>{this.props.timeString}</Text>
                    </View>
                </View>
                <View style={styles.separator}>
                    <Icon
                        name="circle"
                        size={10}
                        color={Colors.informationSeparator}
                    />
                    <View style={styles.separatorline} />
                </View>
                <View style={{ flex: 1 }}>
                    {this._renderText()}
                </View>
                <Dialog 
                    visible={this.state.isDialogVisible}
                    header={'详情'}
                    content={this.props.text}
                    height={Layout.screenHeight / 2}
                    isCancel={false}
                    isContentScrollable={true}
                    onConfirm={() => this.setState({ isDialogVisible: false })}
                />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        height: Layout.informationHeight,
        flexDirection: 'row',
        paddingHorizontal: Layout.inset,
    },
    separator: {
        width: 20,
        alignItems: 'center'
    },
    separatorline: {
        flex: 1,
        width: 2,
        backgroundColor: Colors.informationSeparator
    }
});
