import { Colors, Layout, Enum } from '../../global';

const styles = {
  containerHeaderStyle: {
    flexDirection: 'row', 
    backgroundColor: Colors.quotationHeaderBackgroundColor, 
    paddingHorizontal: Layout.quotationHeaderPaddingHorizontal, 
    height: Layout.quotationHeaderHeight
  },
  containerStyle: {
    flexDirection: 'row', 
    backgroundColor: Colors.quotationContentBackgroundColor, 
    paddingHorizontal: Layout.quotationContentPaddingHorizontal, 
    height: Layout.quotationContentHeight
  },
  headerFirstTextStyle: {
    color: Colors.quotationHeaderFirstColumnTextColor,
    fontSize: (dz => {
        let size = null;
        if (dz === Enum.deviceSize.small) {
            size = 14;
        } else if (dz === Enum.deviceSize.medium) {
            size = 16;
        } else if (dz === Enum.deviceSize.large) {
            size = 16;
        } else if (dz === Enum.deviceSize.pad) {
            size = 18;
        }
        return size;
    })(Layout.deviceSize),
  },
  headerTextStyle: {
    color: Colors.quotationHeaderColumnTextColor,
    fontSize: (dz => {
        let size = null;
        if (dz === Enum.deviceSize.small) {
            size = 14;
        } else if (dz === Enum.deviceSize.medium) {
            size = 16;
        } else if (dz === Enum.deviceSize.large) {
            size = 16;
        } else if (dz === Enum.deviceSize.pad) {
            size = 20;
        }
        return size;
    })(Layout.deviceSize),
  },
  textStyle: {
    color: Colors.white,
    fontSize: (dz => {
        let size = null;
        if (dz === Enum.deviceSize.small) {
            size = 12;
        } else if (dz === Enum.deviceSize.medium) {
            size = 14;
        } else if (dz === Enum.deviceSize.large) {
            size = 14;
        } else if (dz === Enum.deviceSize.pad) {
            size = 18;
        }
        return size;
    })(Layout.deviceSize),
  },
  gridSmallFontSize: 10,
  gridPriceFontSize: Layout.deviceSize === Enum.deviceSize.small ? 16 : 20,
  gridPercentageFontSize: Layout.deviceSize === Enum.deviceSize.small ? 10 : 14,
  gridVolumnFontSize: Layout.deviceSize === Enum.deviceSize.small ? 10 : 12,
  // gridTe
  centerStyle: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center'
  },
  centerVerticalStyle: {
    flex: 1,
    justifyContent: 'center'
  },
  borderStyle: {
    borderBottomWidth: Layout.quotationBorderWidth,
    borderBottomColor: Colors.blackBorder
  },
  adStyle: {
    height: Layout.quotationAdHeight, 
    justifyContent: 'center', 
    alignItems: 'center', 
    backgroundColor: Colors.markerBackgroundColor
  },
  adTextStyle: {
    color: Colors.greyText
  }
};
export default styles;
