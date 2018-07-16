import React, { Component } from 'react';
import { TouchableOpacity, Text, StyleSheet, Image, ViewPropTypes } from 'react-native';
import PropTypes from 'prop-types';
import { Colors, Enum } from '../global';

export default class TouchableSort extends Component {
    static propTypes = {
        style: ViewPropTypes.style,
        name: PropTypes.string,
        text: PropTypes.string,
        textStyle: Text.propTypes.style,

        activeName: PropTypes.string,
        
        onPress: PropTypes.func,
    }

    static defaultProps = {
        text: 'text'
    }
    constructor(props) {
        super(props);
        // 0 -> init, 1 -> ascending, 2 -> descending
        this.state = { count: Enum.sort.init };
    }
    componentWillReceiveProps(nextProps) {
        if (nextProps.activeName !== this.props.name) {
            this.setState({ count: Enum.sort.init });
        }
    }
    _onPress = () => {
        let count = this.state.count;
        count = (count + 1) % 3;
        this.setState({ count });
        this.props.onPress && this.props.onPress({ name: this.props.name, count });
    }
    render() {
        return (
            <TouchableOpacity 
                style={[styles.container, this.props.style && this.props.style]}
                onPress={this._onPress}
            >
                <Text style={[styles.textStyle, this.props.textStyle && this.props.textStyle]}>{this.props.text}</Text>
                <Image
                    source={(c => {
                        if (c === 0) {
                            return require('../../img/quotation/sortInit.png');
                        } else if (c === 1) {
                            return require('../../img/quotation/sortUp.png');
                        } else if (c === 2) {
                            return require('../../img/quotation/sortDown.png');
                        }
                    })(this.state.count)}
                />
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
    },
    textStyle: {
        color: Colors.white,
        marginRight: 10
    }
});
