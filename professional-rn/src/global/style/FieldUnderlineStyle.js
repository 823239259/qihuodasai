import { StyleSheet } from 'react-native';
import Config from '../Config';
import Layout from '../Layout';
import c from '../Colors';
import Rhino from '../theme/Rhino';
import DarkBlue from '../theme/DarkBlue';

let Colors = c;
if (Config.theme === 'Rhino') {
    Colors = Rhino;
} else if (Config.theme === 'DarkBlue') {
    Colors = DarkBlue;
}

const styles = StyleSheet.create({
    containerCircle: {
        // height: Layout.buttonLargeHeight,
        alignItems: 'center',

        borderWidth: StyleSheet.hairlineWidth,
        borderRadius: 5,
        borderColor: Colors.formTextInputBorderColor,
    },
    containerUnderline: {
        // height: Layout.buttonLargeHeight,
        alignItems: 'center',

        borderBottomWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.greyIcon,
    },
    inputWrapper: {
        paddingLeft: 20,

        // height: Layout.buttonLargeHeight - 2, // for border
        flexDirection: 'row',
        alignItems: 'center',
    },
    // InputField + FieldWrapper === DisplayUnderline
    labelContainer: {
        width: 120, // 目前最長：累计免手续费
    },
    // InputField + FieldWrapper === DisplayUnderline
    labelDisplayContainer: {
        width: 120 // 目前最長：累计免手续费
    },
    labelStyle: {
        color: Colors.greyText, 
        fontSize: 18, 
        fontWeight: '600'
    },
    clear: {
        flexDirection: 'row',
        ...Layout.center,
        width: 50,
        height: Layout.buttonLargeHeight - 2, // for border
    },
    error: {
        color: 'red',
    },
});
export default {
    ...styles
};
