import Colors from '../Colors';

const label = { fontSize: 16, fontWeight: '400', color: Colors.greyText };
export default {
    default: { fontSize: 16 },
    label,
    handicap: { ...label, lineHeight: 28 }
};
