<template>
  <div>
    <p>
      <button @click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button @click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <!--  itemCount：最多显示8个页码  -->
    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

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
          <!-- 在小屏幕和超小屏幕上隐藏 -->
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-info" @click="edit(chapter)">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button class="btn btn-xs btn-danger" @click="del(chapter.id)">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>

          </div>

          <!-- 在中屏幕和大屏幕上隐藏 -->
          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
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

    <!-- Modal -->
    <div id="form_modal" class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" data-target="#mymodal-data" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <h5 class="modal-title" id="exampleModalLabel">表单</h5>

          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="名称" v-model="chapter.name">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程ID</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="课程ID" v-model="chapter.courseId">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="save()">保存</button>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script>
import Pagination from "../../components/pagination";
import Swal from 'sweetalert2'
import toast from '../../static/js/toast'

export default {
  name: "chapter",
  components: {
    Pagination
  },
  data() {
    return {
      chapter: {},
      chapters: []
    }
  },
  mounted() {
    let _this = this;
    // 每页 5 条数据
    _this.$refs.pagination.size = 5;
    _this.list(1);
    // 激活样式方法一：
    // 调用父组件的 activeSideBar 方法
    // this.$parent.activeSideBar('business-chapter-sidebar');
    // this.list();
  },
  methods: {
    add() {
      // $('.modal').modal('show');
      let _this = this;
      // 清空模态框中的文字
      _this.chapter = {};
      $('.modal').modal({
        show: true,
        backdrop: 'static'  //  禁止点击模态框外部时关闭
      });
    },
    edit(chapter) {
      let _this = this;
      // _this.chapter = chapter 会在修改模态框中的字段时，自动修改背后数据表格的值
      // _this.chapter = chapter;
      // _this.chapter = $.extend({}, chapter);
      _this.chapter = Object.assign({}, chapter);
      $('.modal').modal('show');
    },
    save() {
      let _this = this;
      _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/save',
          _this.chapter
      ).then((response) => {
        const responseDto = response.data;
        if (responseDto.success) {
          console.log("新增大章列表结果：", responseDto.content);
          // 关闭模态框
          $('.modal').modal('hide');
          // 刷新表格数据
          _this.list(1);
        }

      })
    },
    del(id) {
      let _this = this;
      Swal.fire({
        title: '确认删除?',
        text: "删除后将不能恢复!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '删除!'
      }).then((result) => {
        if (result.isConfirmed) {
          _this.$ajax.delete(`http://127.0.0.1:9000/business/admin/chapter/delete/${id}`)
              .then(response => {
                console.log("删除大章列表结果：", response);
                let responseDto = response.data;
                if (responseDto.success) {
                  // 刷新表格数据
                  _this.list(1);
                  toast.success("删除成功！");
                }
              })
        }
      })


    },
    list(page) {
      let _this = this;
      _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/list', {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        console.log("查询大章列表结果：", response);
        const responseDto = response.data;
        _this.chapters = responseDto.content.list;
        _this.$refs.pagination.render(page, responseDto.content.total);

      })
    }

    // list() {
    //   const _this = this;
    //   // _this.$ajax.get('http://localhost:9000/business/admin/chapter/list')
    //   // jquery ajax 默认是表单的方式 ，axios ajax 默认是以JSON的方式
    //   // 在这里需要在 ChapterController 所对应的 list() 方法行参上加上 @RequestBody 注解
    //   _this.$ajax.post('http://localhost:9000/business/admin/chapter/list', {
    //     page: 2,
    //     size: 5
    //   })
    //       .then(response => {
    //         // console.log(response);
    //         _this.chapters = response.data.list;
    //       })
    // }
  }
}
</script>

<style scoped>

</style>