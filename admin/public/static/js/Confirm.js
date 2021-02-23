import Swal from "sweetalert2";

export default {
    show(message,callback) {
        Swal.fire({
            title: message,
            text: "删除后将不能恢复!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '删除!'
        }).then((result) => {
            result.isConfirmed && callback?.();
            // if (result.isConfirmed) {
            //     callback?.();
            // }
        })
    }
}
