import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { ButtonCommon } from '../../../components';
import { Colors, Layout } from '../../../global';

@inject('AccountStore') @observer
export default class CardHeader extends Component {
    _renderHeaderLogout() {
        const { AccountStore } = this.props;
        if (AccountStore.isLogged) {
            return (
                <ButtonCommon text={'退出账户'} color={Colors.darkBlue} style={{ flex: 0 }} textStyle={Layout.fontNormal} onPress={() => AccountStore.logout()} />
            );
        }
    }
    render() {
        const { AccountStore } = this.props;
        return (
            <View style={styles.container}>
                <View style={[Layout.center, { flexDirection: 'row' }]}>
                    <Icon
                        name="user-circle" 
                        size={42} 
                        color={Colors.lightTextColor}
                    />
                    <View style={[Layout.center, { marginLeft: 20 }]}>
                        <Text style={[{ color: Colors.greyText }, Layout.fontNormal]}>{AccountStore.username}</Text>
                    </View>
                </View>
                { this._renderHeaderLogout() }
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        marginTop: 15,
        height: Layout.buttonLargeHeight,
        marginHorizontal: Layout.accountContainerPadding,
        flexDirection: 'row',
        justifyContent: 'space-between',
        backgroundColor: Colors.accountBalance.backgroundColor
    }
});
