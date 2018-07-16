import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Colors, Layout } from '../../../global';

@inject('AccountStore') @observer
export default class CardBalance extends Component {
    render() {
        return (
            <View style={styles.container}>
                <View style={[Layout.center, { flex: 1 }]}>
                    <Text style={[{ color: Colors.lightTextColor }, Layout.fontBolder]}>{this.props.AccountStore.balance}</Text>
                    <Text style={[{ color: Colors.greyText, marginTop: 5 }, Layout.fontNormal]}>{'余额'}</Text>
                </View>
        </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
      height: 100,
      alignItems: 'center',
      ...Colors.accountBalance
    },
  });
