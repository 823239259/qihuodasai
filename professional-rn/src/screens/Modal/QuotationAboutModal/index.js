import React, { Component } from 'react';
import { ScrollView, View, StyleSheet, Image, Text, Linking } from 'react-native';
import { SafeAreaView } from '../../../containments';
import { Variables, NavigatorStyle, Layout, Colors } from '../../../global';
import { Logger } from '../../../utils';

export default class QuotationAboutModal extends Component {
    logger = null;
    constructor(props) {
        super(props);
        this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
        this.state = {
            imgWidth: Variables.isJudgeShow ? 375 : 1125,
            imgHeight: Variables.isJudgeShow ? 1148 : 3948,
            source: Variables.isJudgeShow ? require('../../../../img/about/aboutScreenForJudge.png') : require('../../../../img/about/aboutScreen.png')
        };
        this.logger = new Logger(QuotationAboutModal);
    }
    componentWillMount() {
        const scaleFactor = this.state.imgWidth / Layout.screenWidth;
        const imageHeight = this.state.imgHeight / scaleFactor;
        this.setState({ imgWidth: Layout.screenWidth, imgHeight: imageHeight });
    }
    onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
        if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
            if (event.id === Variables.icon.closeAbout.id) { // this is the same id field from the static navigatorButtons definition
            this.props.navigator.dismissModal();
            }
        }
    }
    callNumber = (url) => {
        Linking.canOpenURL(url).then(supported => {
            if (!supported) {
                this.logger.warn(`Can't handle url: ${url}`);
            } else {
                return Linking.openURL(url);
            }
          }).catch(err => this.logger.warn('An error occurred', err));
    }
    _renderTel() {
        return (
            <View style={styles.telContainer}>
                <Text
                    style={{ color: 'rgb(0, 122, 255)', fontSize: 16 }}
                    onPress={this.callNumber.bind(this, 'telprompt:4001209061')}
                >{'开户致电 400-120-9061'}</Text>
            </View>
        );
    }
    render() {
        return (
            <SafeAreaView style={styles.container}>
                <ScrollView style={styles.container}>
                    {this._renderTel()}
                    <Image
                        style={{ width: this.state.imgWidth, height: this.state.imgHeight }}
                        source={this.state.source}
                    />
                    {this._renderTel()}
                </ScrollView>
            </SafeAreaView>
        );
    }
}
QuotationAboutModal.navigatorStyle = NavigatorStyle.modalStyle;
const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: Colors.white,
    },
    telContainer: {
        height: 60,
        ...Layout.center
    }
});
