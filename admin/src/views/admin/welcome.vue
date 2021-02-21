<template>
  <div>
    <h1>欢迎进入控台</h1>
  </div>
</template>

<script>
export default {
  name: "welcome",
  mounted() {
    // 调用父组件的 activeSideBar 方法
    this.$parent.activeSideBar('welcome-sidebar');
  }
}
</script>

<style scoped>

</style>