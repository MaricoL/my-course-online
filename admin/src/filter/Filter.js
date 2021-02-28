const Filter = {
    optionKV(list, key) {
        if (!list || !key) return;
        const [obj] = list.filter(item => item.key === key);
        return obj.value;
    },


    optionObjFilter(obj, key) {
        if (!obj || !key) return;
        const [o] = Object.values(obj).filter(item => item.key === key);
        return o?.value;
    },

    // 格式化秒数
    formatTimeFilter(seconds) {
        let second = (seconds % 60).toString().padStart(2,'0');
        let min = (Math.floor(seconds / 60) % 60).toString().padStart(2,'0');
        let hour = Math.floor(seconds / 3600).toString().padStart(2,'0') ;
        return hour + ":" + min + ":" + second;
    }
}

export default Filter;

// export default {
//     optionKV,
//     optionObj
// }