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
    dateFormat(format,date){
        let result;
        if (!date) {
            date = new Date();
        }
        const options = {
            'y+': date.getFullYear().toString(),
            'M+': (date.getMonth() + 1).toString(),
            'd+': date.getDate().toString(),
            'h+': date.getHours().toString(),
            'm+': date.getMinutes().toString(),
            's+':date.getSeconds().toString()
        }

        for (let i in options) {
            result = new RegExp(`(${i})`).exec(format);
            if(result){
                format = format.replace(result[1], (result[1].length === 1) ? options[i] : (options[i].padStart(result[1].length, '0')));
            }
        }

        return format;
    }
}

// export default Tool;