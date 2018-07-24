import { observable, computed, action } from 'mobx';

class FutureTypeStore {
    @observable isFutIn = false // 内外盘的标识
    @observable business_Type = false //内外盘平台标识false 默认外盘
    @computed get changebusinessType () {
        return this.business_Type? 99 : 8
    }
    @action changebusinessType1 () {
        this.business_Type = !this.business_Type
    }
    @computed get Futstring () {
        return this.isFutIn?"FUT_IN":"FUT_OUT"
    }
    @action changeFutIn (k) {
        this.isFutIn = !this.isFutIn
    }
}
export default futureTypeStore = new FutureTypeStore()