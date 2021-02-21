<template>
  <div>
    <p>
      <button @click="list()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>名称</th>
        <th>课程ID</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="chapter in chapters" :key="chapter.id">
        <td>{{ chapter.id }}</td>
        <td>{{ chapter.name }}</td>
        <td>{{ chapter.courseId }}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-success">
              <i class="ace-icon fa fa-check bigger-120"></i>
            </button>

            <button class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>

            <button class="btn btn-xs btn-warning">
              <i class="ace-icon fa fa-flag bigger-120"></i>
            </button>
          </div>

          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                <li>
                  <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																			<span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </td>
      </tr>

      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "chapter",
  data() {
    return {
      chapters: []
    }
  },
  mounted() {
    // 激活样式方法一：
    // 调用父组件的 activeSideBar 方法
    // this.$parent.activeSideBar('business-chapter-sidebar');
    this.list();
  },
  methods: {
    list() {
      const _this = this;
      // _this.$ajax.get('http://localhost:9000/business/admin/chapter/list')
      // jquery ajax 默认是表单的方式 ，axios ajax 默认是以JSON的方式
      // 在这里需要在 ChapterController 所对应的 list() 方法行参上加上 @RequestBody 注解
      _this.$ajax.post('http://localhost:9000/business/admin/chapter/list', {
        page: 2,
        size: 5
      })
          .then(response => {
            // console.log(response);
            _this.chapters = response.data.list;
          })
    }
  }
}
</script>

<style scoped>

</style>