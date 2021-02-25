const optionKV = (list,key) => {
    if(!list || !key) return;
    const [obj] =  list.filter(item => item.key === key);
    return obj.value;
}

export default {
    optionKV
}