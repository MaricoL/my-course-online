<template>
  <div>
    <h2 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" data-toggle="modal" class="pink"> {{ course.name }} </router-link>
    </h2>
    <hr>
    <!--    <h1>{{ course.name }}</h1>-->
    <p>
      <router-link to="/business/course" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        返回
      </router-link>&nbsp;
      <button @click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>&nbsp;
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
            <button class="btn btn-white btn-xs btn-success btn-round" @click="toSection(chapter)">
              <i class="ace-icon fa fa-book green"></i>
              小节
            </button>&nbsp;
            <button class="btn btn-white btn-xs btn-default btn-round" @click="edit(course)">
              <i class="ace-icon fa fa-pencil blue"></i>
              编辑
            </button>&nbsp;
            <button class="btn btn-white btn-xs btn-warning btn-round" @click="del(course.id)">
              <i class="ace-icon fa fa-trash-o orange"></i>
              删除
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
                <label class="col-sm-2 control-label">所属课程</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ course.name }}</p>
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

export default {
  name: "chapter",
  components: {
    Pagination
  },
  data() {
    return {
      chapter: {},
      chapters: [],
      course: {}
    }
  },
  mounted() {
    let _this = this;

    // ||{} ：小技巧，如果course为空，则 course.id 报错，而空对象不会报错
    let course = SessionStorage.get("course") || {};
    if (Tool.isEmpty(course)) {
      _this.$router.push("/welcome");
    } else {
      _this.course = course;
      // 每页 5 条数据
      _this.$refs.pagination.size = 5;
      _this.list(1);
      // 激活样式方法一：
      // 调用父组件的 activeSideBar 方法
      this.$parent.activeSideBar('business-course-sidebar');
    }


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
      // 保存校验
      // if (!Validator.require(_this.chapter.name, "名称") ||
      //     !Validator.require(_this.chapter.courseId, "课程ID") ||
      //     !Validator.length(_this.chapter.courseId, "课程ID", 1, 8)) {
      //   return;
      // }
      _this.chapter.courseId = _this.course.id;
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/chapter/save`,
          _this.chapter
      ).then((response) => {
        const responseDto = response.data;
        if (responseDto.success) {
          Toast.success("保存成功！");
          // console.log("新增大章列表结果：", responseDto.content);
          // 关闭模态框
          $('.modal').modal('hide');
          // 刷新表格数据
          _this.list(1);
        } else {
          Toast.warning(responseDto.message);
        }

      })
    },
    del(id) {
      let _this = this;
      Confirm.show("删除大章后不可恢复，确认删除?", () => {
        _this.$ajax.delete(`${process.env.VUE_APP_SERVER}/business/admin/chapter/delete/${id}`)
            .then(response => {
              // console.log("删除大章列表结果：", response);
              let responseDto = response.data;
              if (responseDto.success) {
                // 刷新表格数据
                _this.list(1);
                Toast.success("删除成功！");
              }
            })
      })

    },
    list(page) {
      console.log("当前应用服务请求地址：" + process.env.VUE_APP_SERVER);

      let _this = this;
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/chapter/list`, {
        page: page,
        size: _this.$refs.pagination.size,
        courseId: _this.course.id
      }).then((response) => {
        // console.log("查询大章列表结果：", response);
        const responseDto = response.data;
        _this.chapters = responseDto.content.list;
        _this.$refs.pagination.render(page, responseDto.content.total);

      })
    },

    toSection(chapter) {
      SessionStorage.set("chapter", chapter);
      this.$router.push("/business/section");
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