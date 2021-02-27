SessionStorage = {
    set(key,value) {
        sessionStorage.setItem(key, JSON.stringify(value));
    },

    get(key) {
        let value = sessionStorage.getItem(key);
        if (value && typeof (value) !== 'undefined' && value !== 'undefined') {
            return JSON.parse(value);
        }
    },

    remove(key) {
        sessionStorage.removeItem(key);
    },

    clearAll(){
        sessionStorage.clear();
    }
}

// export default SessionStorage;