import React, { Component } from 'react';
import { View, Text, ActivityIndicator, FlatList } from 'react-native';
import ListItem from './ListItem';

export default class FlatListDemo extends Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: false,
            data: [],
            page: 1,
            seed: 1,
            error: null,
            refreshing: false  
        };
    }
    componentDidMount() {
        this.makeRemoteRequest();
    }
    makeRemoteRequest() {
        const { seed, page } = this.state;
        const url = `https://randomuser.me/api/?seed=${seed}&page=${page}&results=20`;
        this.setState({ loading: true });
        fetch(url)
            .then(res => res.json())
            .then(res => {
                this.setState({
                    data: this.state.data.concat(res.results) || this.state.data,
                    error: res.error || null,
                    loading: false,
                    refreshing: false
                });
            })
            .catch(error => {
                this.setState({ error, loading: false });
            });
    }

    handleRefresh() {
        this.setState({
            page: 1,
            refreshing: true,
            seed: this.state.seed + 1
        }, () => {
            this.makeRemoteRequest();
        });
    }
    renderSeparator() {
        return (
            <View 
                style={{
                    height: 1,
                    width: '86%',
                    backgroundColor: '#CED0CD',
                    marginLeft: '14%'
                }}
            />
        );
    }
    renderHeader() {
        return <Text style={{ color: 'white' }}>{'it is header'}</Text>;
        // return <SearchBar placeholder='Type Here...' />;
    }
    renderFooter() {
        if (!this.state.loading) return null;

        return (
            <View
                style={{
                    paddingVertical: 20,
                    borderTopWidth: 1,
                    borderColor: '#CED0CE'
                }}
            >
                <ActivityIndicator animating size='large' />
            </View>
        );
    }
    render() {
        return (
            <View>
                <FlatList
                    data={this.state.data}
                    renderItem={({ item }) => (
                        <ListItem
                            title={`${item.name.first} ${item.name.last}`}
                            subtitle={item.email}
                        />
                    )}
                    inverted
                    keyExtractor={item => item.email}
                    ItemSeparatorComponent={() => this.renderSeparator()}
                    ListHeaderComponent={() => this.renderHeader()}
                    ListFooterComponent={() => this.renderFooter()}
                    refreshing={this.state.refreshing}
                    onRefresh={() => this.handleRefresh()}
                />
            </View>
        );
    }
}
