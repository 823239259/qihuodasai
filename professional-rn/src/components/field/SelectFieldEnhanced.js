import { fieldWrapper } from './Hoc';
import { Enum } from '../../global';
import SelectField from './SelectField';

const SelectFieldEnhanced = fieldWrapper(SelectField, Enum.fieldType.select);
export default SelectFieldEnhanced;

