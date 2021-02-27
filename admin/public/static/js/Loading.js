Loading = {
    show() {
        $.blockUI({
            message: `<img src="/static/image/loading.gif"/>`,
            css: {
                zIndex: "10011",
                padding: "10px",
                left: "50%",
                width: "10%",
                // marginLeft: "-10%",
                transform: "translateX(-50%)"

            }
        });
    },
    hide() {
        // 为了掩饰效果，增加延迟
        // setTimeout(() => {
        //     $.unblockUI();
        // }, 1000);
        $.unblockUI();
    }
}

// export default Loading