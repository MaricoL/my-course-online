import Swal from 'sweetalert2'

const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})


export default {
    success(message) {
        Toast.fire({
            icon: 'success',
            title: message,
            width: '30%',
            height: '30%'
        })
    },
    error() {
        Toast.fire({
            icon: 'error',
            title: message
        })
    },
    warning() {
        Toast.fire({
            icon: 'warning',
            title: message
        })
    }

}