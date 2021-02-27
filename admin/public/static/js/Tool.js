Tool =  {
    isEmpty(obj) {
        if (typeof obj === 'string') {
            return !obj || obj.replace(/\s+/g, '') === '';
        } else {
            return !obj || JSON.stringify(obj) === '{}' || obj.length === 0;
        }
    },
    isNotEmpty(obj) {
        return !this.isEmpty(obj);
    },

    isLength: function (str, min, max) {
        return $.trim(str).length >= min && $.trim(str).length <= max;
    },
}

// export default Tool;