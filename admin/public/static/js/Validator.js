Validator = {
    // 判断value值是否为空
    require(value, text) {
        if (Tool.isEmpty(value)) {
            Toast.error(`${text}不能为空！`);
            return false;
        }
        return true;
    },

    length: function (value, text, min, max) {
        // 如果 value 为空，则跳过不校验该字段
        if (Tool.isEmpty(value)) {
            return true;
        }
        if (!Tool.isLength(value, min, max)) {
            Toast.warning(text + "长度" + min + "~" + max + "位");
            return false;
        } else {
            return true;
        }
    }

};

// export default Validator;