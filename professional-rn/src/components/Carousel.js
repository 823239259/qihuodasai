import React, { Component } from 'react';
import { View, Text, StyleSheet, ScrollView, Image } from 'react-native';
import { observer } from 'mobx-react/native';
import { PropTypes } from 'mobx-react';
import { Loading } from '../components';
import { Layout, Config } from '../global';

@observer
export default class Carousel extends Component {
    static propTypes = {
        imagesUri: PropTypes.observableArray
    }
    _renderContent() {
        if (this.props.imagesUri.length === 0) {
            return (
                <View style={[Layout.center, { width: Layout.screenWidth }]}>
                    <Loading />
                </View>
            );
        }
        return this.props.imagesUri.map((image, index) => {
            return (
                <Image 
                    key={index}
                    style={{ width: Layout.screenWidth }}
                    source={{ uri: `${Config.base_images_url}/${image.imageUri}` }}
                />
            );
        });
    }
    render() {
        return (
            <View style={[styles.container]}>
                <ScrollView
                    horizontal
                    showsHorizontalScrollIndicator={true}
                    scrollEventThrottle={10}
                    pagingEnabled
                >
                    { this._renderContent() }
                </ScrollView>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        height: Layout.screenHeight / 4
    },
    scene: {
        width: Layout.screenWidth,
        alignItems: 'center',
        justifyContent: 'center',
    },
});
