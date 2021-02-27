Toast = {
    success(message) {
        Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: message,
            showConfirmButton: false,
            timer: 2000
        })
        // 修改为上述弹框，显示效果更佳
        // Toast.fire({
        //     icon: 'success',
        //     title: message,
        //     width: '30%',
        //     height: '30%'
        // })
    },
    error(message) {
        Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: message,
            showConfirmButton: false,
            timer: 2000
        })
    },
    warning(message) {
        Swal.fire({
            position: 'top-end',
            icon: 'warning',
            title: message,
            showConfirmButton: false,
            timer: 2000
        })
    }
}

// export default Toast;