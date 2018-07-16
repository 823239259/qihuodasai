import Config from './Config';

import c from './Colors';
import Rhino from './theme/Rhino';
import DarkBlue from './theme/DarkBlue';

import Enum from './Enum';
import Layout from './Layout';
import Variables from './Variables';
import TextStyle from './style/TextStyle';
import NavigatorStyle from './style/NavigatorStyle';
import StopLossConditionStyle from './style/StopLossConditionStyle';
import FieldUnderlineStyle from './style/FieldUnderlineStyle';

let Colors = c;
if (Config.theme === 'Rhino') {
    Colors = Rhino;
} else if (Config.theme === 'DarkBlue') {
    Colors = DarkBlue;
}

export {
    Config,
    Colors,
    Enum,
    Layout,
    Variables,

    TextStyle,
    NavigatorStyle,
    StopLossConditionStyle,
    FieldUnderlineStyle
};

