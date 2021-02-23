import Tool from './Tool'
import Toast from './Toast'

export default {
    // 判断value值是否为空
    require(value, text) {
        if (Tool.isEmpty(value)) {
            Toast.error(`${text}不能为空！`);
            return false;
        }
        return true;
    },

    length: function (value, text, min, max) {
        if (!Tool.isLength(value, min, max)) {
            Toast.warning(text + "长度" + min + "~" + max + "位");
            return false;
        } else {
            return true
        }
    }

};