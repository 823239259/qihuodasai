/* 
    只是個Container，因為Type改變ScrollView的style
    type:
    1.Variables.icon.list
    2.Variables.icon.grid
*/
import React, { Component } from 'react';
import { FlatList } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Variables } from '../../global';
import RowSwipeout from './RowSwipeout';

@inject('QuotationStore') @observer
export default class RowSwipeoutContainer extends Component {
    static propTypes = {
        type: PropTypes.string,
        product: PropTypes.object,
        navigator: PropTypes.object
    }

    static defaultProps = {
        type: Variables.icon.list.name
    }
    _renderItem = ({ item }) => (
        <RowSwipeout product={item} navigator={this.props.navigator} />
    );
    render() {
        return (
            <FlatList
                style={{ flex: 1 }}
                keyExtractor={item => item.productName}
                removeClippedSubviews={true}
                scrollEventThrottle={16}
                data={this.props.QuotationStore.sortedProducts}
                renderItem={this._renderItem}
            />
        );
    }  
}
