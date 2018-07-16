import { Navigation, ScreenVisibilityListener } from 'react-native-navigation';

import TodoScreen from './TodoScreen';
import TodoAddScreen from './TodoScreen/TodoAddScreen';
import TestScreen from './TestScreen';
import TestFatArrow from './TestFatArrow';
import TestArrayUtil from './TestArrayUtil';
import TestField from './TestField';
import TestRefreshableFlatList from './TestRefreshableFlatList';
import TestAsyncStorage from './TestAsyncStorage';
import TestPromise from './TestPromise';
import TestTouchableSort from './TestTouchableSort';
import TestSwipeout from './TestSwipeout';
import TestLightning from './TestLightning';
import TestTimeLine from './TestTimeLine';
import TestPanResponder from './TestPanResponder';
import TestKView from './TestKView';

import { Logger } from '../../src/utils';

// register all screens of the app (including internal ones)
export function registerScreens(store: {}, Provider: {}) {
  return new Promise(resolve => {
    
      Navigation.registerComponent('todoScreen.TodoAdd', () => TodoScreen, store, Provider);
      Navigation.registerComponent('todoScreen.TodoAddScreen', () => TodoAddScreen, store, Provider);
      Navigation.registerComponent('test.TestScreen', () => TestScreen, store, Provider);
      Navigation.registerComponent('test.TestFatArrow', () => TestFatArrow, store, Provider);
      Navigation.registerComponent('test.TestArrayUtil', () => TestArrayUtil, store, Provider);
      Navigation.registerComponent('test.TestField', () => TestField, store, Provider);
      Navigation.registerComponent('test.TestRefreshableFlatList', () => TestRefreshableFlatList, store, Provider);
      Navigation.registerComponent('test.TestAsyncStorage', () => TestAsyncStorage, store, Provider);
      Navigation.registerComponent('test.TestPromise', () => TestPromise, store, Provider);
      Navigation.registerComponent('test.TestTouchableSort', () => TestTouchableSort, store, Provider);
      Navigation.registerComponent('test.TestSwipeout', () => TestSwipeout, store, Provider);
      Navigation.registerComponent('test.TestLightning', () => TestLightning, store, Provider);
      Navigation.registerComponent('test.TestTimeLine', () => TestTimeLine, store, Provider);
      Navigation.registerComponent('test.TestPanResponder', () => TestPanResponder, store, Provider);
      Navigation.registerComponent('test.TestKView', () => TestKView, store, Provider);

    resolve(true);
  });
}
export function registerScreenVisibilityListener() {
  return new Promise(resolve => {
      const logger = new Logger('RegisterScreens');
      new ScreenVisibilityListener({
        willAppear: ({screen}) => logger.trace(`Displaying screen ${screen}`),
        didAppear: ({screen, startTime, endTime, commandType}) => logger.trace('screenVisibility', `Screen ${screen} displayed in ${endTime - startTime} millis [${commandType}]`),
        willDisappear: ({screen}) => logger.trace(`Screen will disappear ${screen}`),
        didDisappear: ({screen}) => logger.trace(`Screen disappeared ${screen}`)
      }).register();
    resolve(true);
  });
}
