Filter = {
    optionKV(list, key) {
        if (!list || !key) return;
        const [obj] = list.filter(item => item.key === key);
        return obj.value;
    },


    optionObj(obj, key) {
        if (!obj || !key) return;
        const [o] = Object.values(obj).filter(item => item.key === key);
        return o?.value;
    }
}

// export default {
//     optionKV,
//     optionObj
// }