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
    container: {
        flex: 1,
        backgroundColor: Colors.tabBarModalBackgroundColor,  
    },
    header: {
        height: 50,
        flexDirection: 'row',
        ...Layout.center,
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.tabBarModalBorderColor
    },
    headerText: {
        color: Colors.lightTextColor,
        ...Layout.fontBold
    },
    content: {
        flex: 1
    },
    columnFirst: {
        width: 50,
        ...Layout.center,
        marginRight: Layout.columPadding,
        borderRightWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.tabBarModalBorderColor
    },
    column: {
        flex: 1,
        flexDirection: 'row',
        ...Layout.center,
        padding: Layout.columPadding
    },
    column2: {
        flex: 2,
        flexDirection: 'row',
        ...Layout.center,
        paddingVertical: Layout.columPadding,
        paddingHorizontal: Layout.columPadding * 2
    },
    row: {
        flex: 1,
        flexDirection: 'row'
    },
    label: {
        color: Colors.greyText
    },
    value: {
        color: Colors.white
    }
});
export default {
    ...styles
};
