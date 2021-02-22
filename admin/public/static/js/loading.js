export default {
    show() {
        $.blockUI({
            message: `<img src="/static/image/loading.gif"/>`,
            css: {
                backgroundColor: '#f00', color: '#fff'
            }
        });
    },
    hide() {
        setTimeout(() => {
            $.unblockUI();
        }, 1000);
    }
}