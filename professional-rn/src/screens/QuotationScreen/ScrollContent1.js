/* 
    只是個Container，因為Type改變ScrollView的style
    type:
    1.Variables.icon.list
    2.Variables.icon.grid
*/
import React, { Component } from 'react';
import { ScrollView } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Variables, Layout } from '../../global';
import ScrollContentItem from './ScrollContentItem';

@inject('QuotationStore') @observer
export default class ScrollContent extends Component {
    static propTypes = {
        type: PropTypes.string,
        product: PropTypes.object,
        navigator: PropTypes.object
    }

    static defaultProps = {
        type: Variables.icon.list.name
    }
    _renderContent() {
        const { QuotationStore, navigator, type } = this.props;
        return QuotationStore.sortedProducts.map((product, index) => {
            return (
                <ScrollContentItem key={`type${index}`} index={index} type={type} product={product} navigator={navigator} />
            );
        });
    }
    render() {
        const scrollStyle = { flex: 1, flexDirection: 'column' };
        const scrollContentStyle = {};
        if (this.props.type === Variables.icon.grid.name) {
            scrollStyle.margin = Layout.inset;
            scrollContentStyle.flexWrap = 'wrap';
            scrollContentStyle.flexDirection = 'row';
        }
        return (
            <ScrollView 
                style={scrollStyle}
                contentContainerStyle={scrollContentStyle}
            >
                { this._renderContent() }
            </ScrollView>
        );
    }
}
