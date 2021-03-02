SESSION_KEY_COURSE = "SESSION_KEY_COURSE"; // 课程管理页面点击章管理时，保存课程信息
SESSION_KEY_CHAPTER = "SESSION_KEY_CHAPTER"; // 章管理页面点击节管理时，保存章信息

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