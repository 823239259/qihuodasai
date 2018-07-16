import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import { View } from 'react-native';
import RefreshableFlatList from 'react-native-refreshable-flatlist';
import { IndicatorPullToRefresh } from '../../components';
import InformationItem from './InformationItem';
import { Layout } from '../../global';
import { Logger } from '../../utils';

@inject('InformationStore') @observer
export default class InformationScreen extends Component {

    constructor(props) {
        super(props);
        this.isFirst = true;
        this.logger = new Logger(InformationScreen);
    }
    componentDidUpdate() {
        const { InformationStore } = this.props;
        if (InformationStore.liveInformations && this.isFirst) {
            this.isFirst = false;
            try {
                this.refFlatList.scrollToIndex({ index: InformationStore.liveInformations.length - 1, animated: true });
            } catch (e) {
                this.logger.error(e);
            }
        }
    }
    getItemLayout = (data, index) => (
        { length: Layout.informationHeight, offset: Layout.informationHeight * index, index }
    )
    _onRefresh() {
        const { InformationStore } = this.props;
        InformationStore.getLiveInformation();
    }
    render() {
        const { InformationStore } = this.props;
        return (
            <View style={{ flex: 1 }}>
                <RefreshableFlatList
                    bottomPullingPrompt={'向上拉，获取更多资讯'}
                    bottomHoldingPrompt={'下载中...'}
                    bottomRefreshingPrompt={'已获取，正在处理资讯...'}
                    showTopIndicator={false}
                    bottomIndicatorComponent={IndicatorPullToRefresh}
                    data={InformationStore.reverseInformations}
                    renderItem={({ item }) => (
                        <InformationItem
                            dateString={item.dateString}
                            timeString={item.timeString}
                            text={item.liveTitle}
                        />
                    )}
                    ref={(ref) => { this.flatList = ref; }}
                    refreshing={InformationStore.refreshing}
                    // onRefreshing={() => new Promise((r) => {
                    //     setTimeout(() => {
                    //         r();
                    //     }, 3000);
                    // })}
                    onLoadMore={() => this._onRefresh()}
                    onLoadMore={() => new Promise((r) => {
                        setTimeout(() => {
                            this._onRefresh();
                            r();
                        }, 2500);
                    })}
                    keyExtractor={item => item.index}
                    styles={{ prompt: { color: 'gray' } }}
                />
            </View>
        );
    }
}
