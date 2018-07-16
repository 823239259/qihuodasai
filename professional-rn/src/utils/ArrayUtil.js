/**
 * https://www.evernote.com/l/ATBQKeny0hpL9K4v8rkCLRsDeU_l12sYr-w
 * 
 */
class ArrayUtil {
    removeOne(arr, matchFunc) {
        const index = arr.findIndex(matchFunc);
        if (index === -1) {
            return false;
        }
        arr.splice(index, 1);
        return true;
    }
    // 多次移除 https://stackoverflow.com/questions/29997710/remove-object-from-array-of-objects
    remove(arr, matchFunc) {
        if (this.removeOne(arr, matchFunc)) {
            this.remove(arr, matchFunc);
        }
    }
}
export default new ArrayUtil();
