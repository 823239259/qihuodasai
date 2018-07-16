/**
 * https://github.com/wix/react-native-navigation/issues/1118
 * HOC for Screen
 * setActiveScreenName 設定當前active screen
 * 搭配使用 ConnectionMonitor
 */
import React, { Component } from 'react';
import { computed } from 'mobx';
import { inject, observer } from 'mobx-react/native';
import { View, StyleSheet } from 'react-native';
import { LoadingModal, ConnectionStatus } from '../components';
import { Layout, Enum, Colors, Variables } from '../global';

class ConnectionMonitorStore {
    connectionScreenStore = null;
    screenName = null;
    isMonitorAll= null;
  
    constructor(connectionScreenStore, screenName, isMonitorAll) {
        this.connectionScreenStore = connectionScreenStore;
        this.screenName = screenName;
        this.isMonitorAll = isMonitorAll;
    }

  @computed get isReconnecting() {
    //   console.log(`isReconnecting: ${this.connectionScreenStore.activeScreenName}`);
    //   console.log(`isReconnecting: ${this.screenName}`);
    //   console.log(`isInternetConnection: ${this.connectionScreenStore.isInternetConnection}`);
    //   console.log(`isQuotationSocketConnection: ${this.connectionScreenStore.isQuotationSocketConnection}`);
    //   console.log(`isTradeSocketConnection: ${this.connectionScreenStore.isTradeSocketConnection}`);
    if (this.screenName === this.connectionScreenStore.activeScreenName) {
        if (this.isMonitorAll) {
            if (!this.connectionScreenStore.isInternetConnection || 
                !this.connectionScreenStore.isQuotationSocketConnection) {
                    if (Variables.trade.isLogged) {
                        if (!this.connectionScreenStore.isTradeSocketConnection) {
                            return true;
                        }
                    }
            }
        }
        if (!this.connectionScreenStore.isInternetConnection || 
            !this.connectionScreenStore.isQuotationSocketConnection) {
            return true;
        }
    }
    return false;
  }
}

@inject('ConnectionScreenStore') @observer
export const ScreenNavigatorMonitor = (ComposedComponent) => {
    // const component = class extends Component + 之後要return component
    return class extends Component {
        composedComponentInstance = null;
        wrappedInstance = null;
        store = null;
        /**
         * composedComponent.props: 
         * 1.screenName: string
         * 2.isMonitorAll: bool
         */
         
        constructor(props) {
          super(props);

          props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
          this.newProps = Object.assign({}, this.props, { ref: this.proc.bind(this) });
          this.store = new ConnectionMonitorStore(this.props.ConnectionScreenStore, ComposedComponent.screenName, ComposedComponent.isMonitorAll);
        }
        onNavigatorEvent(event) {
            if (this.wrappedInstance) {
                this.wrappedInstance.onNavigatorEvent && this.wrappedInstance.onNavigatorEvent(event);
            }
            if (event.id === 'willAppear') {
                // console.log(`willAppear: ${ComposedComponent.screenName}`);
                this.props.ConnectionScreenStore.setActiveScreenName(ComposedComponent.screenName);
            }
        }
        proc(composedComponentInstance) {
            if (composedComponentInstance) {
                this.composedComponentInstance = composedComponentInstance;
                this.wrappedInstance = composedComponentInstance.wrappedInstance;
            }
        }
        _renderConnectionStatus = () => {
            const { ConnectionScreenStore } = this.props;
            return (
                <View style={[styles.connectionStatusContainer, Colors.boxShadowBottom]}>
                    <ConnectionStatus title={Enum.connectionStatus.type.internet} isConnected={ConnectionScreenStore.isInternetConnection} />
                    <ConnectionStatus title={Enum.connectionStatus.type.quotation} isConnected={ConnectionScreenStore.isQuotationSocketConnection} />
                    {ComposedComponent.isMonitorAll && Variables.trade.isLogged && <ConnectionStatus title={Enum.connectionStatus.type.trade} isConnected={ConnectionScreenStore.isTradeSocketConnection} />}
                </View>
            );
        }
        render() {
            return (
                <View style={styles.container}>
                    <ComposedComponent {...this.newProps} />
                    <LoadingModal isVisible={this.store.isReconnecting} renderContent={this._renderConnectionStatus} />
                </View>
            );
        }
    };
};
const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    connectionStatusContainer: {
        flexDirection: 'row', 
        justifyContent: 'space-around',
        height: 25,
        width: Layout.screenWidth
    }
});

