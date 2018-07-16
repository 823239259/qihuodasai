import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { PickerModal } from '../../../../components';
import { Logger } from '../../../../utils';

@inject('QuotationSocket', 'QuotationStore', 'QuotationDetailStore') @observer
export default class PickerProduct extends Component {
    static propTypes = {
        visible: PropTypes.bool,
        options: PropTypes.array,
        onBlockPress: PropTypes.func
    }

    static defaultProps = {
        visible: false
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(PickerProduct);
    }

    render() {
        const { QuotationSocket, QuotationStore, QuotationDetailStore } = this.props;
        return (
            <PickerModal 
                visible={QuotationDetailStore.isPickerVisible} 
                options={QuotationStore.products.slice()}
                labelKey={'commodityName'}
                valueKey={'productName'}
                initValue={QuotationDetailStore.productName}
                onBlockPress={() => QuotationDetailStore.toggleIsPickerVisible()}
                onDone={(selectedValue) => {
                        QuotationSocket.changeDetail(selectedValue);
                        QuotationDetailStore.toggleIsPickerVisible();
                    }       
                }
            />
        );
    }
}
