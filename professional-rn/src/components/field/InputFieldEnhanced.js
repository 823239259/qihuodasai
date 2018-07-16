import { fieldWrapper } from './Hoc';
import { Enum } from '../../global';
import InputField from './InputField';

const InputFieldEnhanced = fieldWrapper(InputField, Enum.fieldType.input);
export default InputFieldEnhanced;

