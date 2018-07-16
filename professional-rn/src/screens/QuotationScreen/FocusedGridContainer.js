/* 
    只是個Container，因為Type改變ScrollView的style
    type:
    1.Variables.icon.list
    2.Variables.icon.grid
*/
import React, { Component } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout, Colors, Variables } from '../../global';
import FocusedGrid from './FocusedGrid';

@inject('QuotationStore') @observer
export default class FocusedGridContainer extends Component {
    static propTypes = {
        product: PropTypes.object,
        navigator: PropTypes.object
    }

    static defaultProps = {
    }
    constructor(props) {
        super(props);
        this.state = { isOpen: false };
    }
    _renderContent() {
        const { QuotationStore, navigator } = this.props;
        return (
            <View style={{ flexDirection: 'row' }}>
                {
                    QuotationStore.focusedProducts.map((product, index) => {
                        return (
                            <FocusedGrid 
                                key={`FocusedGrid${index}`} 
                                index={index}
                                product={product} 
                                navigator={navigator}
                                height={Layout.quotaitonGridHeight}
                            />
                        );
                    })
                }
            </View>
        );
    }
    collapse = () => {
        this.setState({ isOpen: !this.state.isOpen });
    }
    _renderCollapse() {
        const { QuotationStore } = this.props;
        return (
            <TouchableOpacity 
                style={styles.collapseContainer}
                onPress={this.collapse}
            >
                <Text style={{ color: Colors.white }}>{'已关注'}</Text>
                { 
                    QuotationStore.focusedProducts.map((product, index) => {
                        return <Text key={`focused${index}`} style={{ color: Colors.greyText }}>{product.productName}</Text>;
                    })
                }
                <Icon
                    name={this.state.isOpen ? 'angle-up' : 'angle-down'} 
                    size={Variables.icon.size} 
                    color={Colors.kCaretColor}
                />
            </TouchableOpacity>
        );
    }
    render() {
        return (
            <View>
                { this._renderCollapse() }
                { this.state.isOpen && this._renderContent() }
            </View>
        );
    }
}
const styles = StyleSheet.create({
    collapseContainer: {
        height: 30,
        flexDirection: 'row', 
        justifyContent: 'space-around', 
        alignItems: 'center',  
        backgroundColor: Colors.quotationContentBackgroundColor
    },
});
