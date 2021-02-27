LocalStorage = {
    set(key,value) {
        localStorage.setItem(key, JSON.stringify(value));
    },

    get(key) {
        let value = localStorage.getItem(key);
        if (value && typeof (value) !== 'undefined' && value !== 'undefined') {
            return JSON.parse(value);
        }
    },

    remove(key) {
        localStorage.removeItem(key);
    },

    clearAll(){
        localStorage.clear();
    }
}

// export default LocalStorage