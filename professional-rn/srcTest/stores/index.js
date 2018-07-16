import TestTimeLineStore from './TestTimeLineStore';
import TestKStore from './TestKStore';
import TodoStore from './TodoStore';
import { Logger, PromiseTestUtil } from '../../src/utils';

class StoreManager {
    logger = null;
    promiseTestUtil = null;
    quotationStoreProductResolve = null;
  
    constructor() {
      this.logger = new Logger(StoreManager);
      this.promiseTestUtil = new PromiseTestUtil();
      // this.promiseTestUtil.printSecond();
    }
    configureStore() {
      return new Promise((resolve, reject) => {
        let stores = null;
        Promise.all([
            this.createStores(),
            // this.quotationStoreProductAdded(),
        ]).then(values => {
            stores = values[0];
            resolve(stores);
        }).catch(error => {
            console.log(error);
            reject(error);
        });
      });
    }
    createStores() {
      return new Promise(resolve => {
          const testTimeLineStore = new TestTimeLineStore();
          const testKStore = new TestKStore();
          const todoStore = new TodoStore();
  
          resolve({
            TestTimeLineStore: testTimeLineStore,
            TestKStore: testKStore,
            TodoStore: todoStore
          });
      });
    }
  }
  export default new StoreManager();
