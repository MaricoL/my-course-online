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

    <div class="row">
      <div class="col-xs-12 col-sm-3 col-md-2 center" v-for="teacher in teachers">
        <div>
          <span class="profile-picture">
            <img id="avatar" class="editable img-responsive editable-click editable-empty"
                 alt="Alex's Avatar" src="/ace/assets/images/avatars/profile-pic.jpg">
          </span>

          <div class="space-4"></div>

          <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
            <div class="inline position-relative">
              <a href="#" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                <i class="ace-icon fa fa-circle light-green"></i>
                &nbsp;
                <span class="white">Alex M. Doe</span>
              </a>
            </div>
          </div>

          <div class="space-6"></div>

          <a href="javascript:;" class="text-info bigger-110" v-bind:title="teacher.motto">
            <i class="ace-icon fa fa-user"></i>
            {{ teacher.name }}【{{ teacher.nickname }}】
          </a>

          <div class="space-6"></div>

          <div class="profile-social-links align-center">
            <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            &nbsp;
            <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>

          <div class="hr hr16 dotted"></div>
        </div>


      </div>
    </div>
    <!--    <table id="simple-table" class="table  table-bordered table-hover">-->
    <!--      <thead>-->
    <!--      <tr>-->
    <!--        <th>id</th>-->
    <!--        <th>姓名</th>-->
    <!--        <th>昵称</th>-->
    <!--        <th>头像</th>-->
    <!--        <th>职位</th>-->
    <!--        <th>座右铭</th>-->
    <!--        <th>简介</th>-->
    <!--        <th>操作</th>-->
    <!--      </tr>-->
    <!--      </thead>-->

    <!--      <tbody>-->
    <!--      <tr v-for="teacher in teachers" :key="teacher.id">-->
    <!--              <td>{{ teacher.id }}</td>-->
    <!--              <td>{{ teacher.name }}</td>-->
    <!--              <td>{{ teacher.nickname }}</td>-->
    <!--              <td>{{ teacher.image }}</td>-->
    <!--              <td>{{ teacher.position }}</td>-->
    <!--              <td>{{ teacher.motto }}</td>-->
    <!--              <td>{{ teacher.intro }}</td>-->
    <!--        <td>-->
    <!--          &lt;!&ndash; 在小屏幕和超小屏幕上隐藏 &ndash;&gt;-->
    <!--          <div class="hidden-sm hidden-xs btn-group">-->
    <!--            <button class="btn btn-xs btn-info" @click="edit(teacher)">-->
    <!--              <i class="ace-icon fa fa-pencil bigger-120"></i>-->
    <!--            </button>-->

    <!--            <button class="btn btn-xs btn-danger" @click="del(teacher.id)">-->
    <!--              <i class="ace-icon fa fa-trash-o bigger-120"></i>-->
    <!--            </button>-->

    <!--          </div>-->

    <!--          &lt;!&ndash; 在中屏幕和大屏幕上隐藏 &ndash;&gt;-->
    <!--          <div class="hidden-md hidden-lg">-->
    <!--            <div class="inline pos-rel">-->
    <!--              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">-->
    <!--                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>-->
    <!--              </button>-->

    <!--              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">-->
    <!--                <li>-->
    <!--                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">-->
    <!--																			<span class="green">-->
    <!--																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>-->
    <!--																			</span>-->
    <!--                  </a>-->
    <!--                </li>-->

    <!--                <li>-->
    <!--                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">-->
    <!--																			<span class="red">-->
    <!--																				<i class="ace-icon fa fa-trash-o bigger-120"></i>-->
    <!--																			</span>-->
    <!--                  </a>-->
    <!--                </li>-->
    <!--              </ul>-->
    <!--            </div>-->
    <!--          </div>-->
    <!--        </td>-->
    <!--      </tr>-->

    <!--      </tbody>-->
    <!--    </table>-->

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
                <label class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="姓名" v-model="teacher.name">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="昵称" v-model="teacher.nickname">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">头像</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="头像" v-model="teacher.image">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">职位</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="职位" v-model="teacher.position">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">座右铭</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="座右铭" v-model="teacher.motto">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">简介</label>
                <div class="col-sm-10">
<!--                  <input type="text" class="form-control" placeholder="简介" v-model="teacher.intro">-->
                  <textarea v-model="teacher.intro" class="form-control" rows="5"></textarea>

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
  name: "business-teacher",
  components: {
    Pagination
  },
  data() {
    return {
      teacher: {},
      teachers: [],
    }
  },
  mounted() {
    let _this = this;
    // 每页 5 条数据
    _this.$refs.pagination.size = 5;
    _this.list(1);
    // 激活样式方法一：
    // 调用父组件的 activeSideBar 方法
    // this.$parent.activeSideBar('business-teacher-sidebar');
    // this.list();
  },
  methods: {
    add() {
      // $('.modal').modal('show');
      let _this = this;
      // 清空模态框中的文字
      _this.teacher = {};
      $('.modal').modal({
        show: true,
        backdrop: 'static'  //  禁止点击模态框外部时关闭
      });
    },
    edit(teacher) {
      let _this = this;
      // _this.teacher = teacher 会在修改模态框中的字段时，自动修改背后数据表格的值
      // _this.teacher = teacher;
      // _this.teacher = $.extend({}, teacher);
      _this.teacher = Object.assign({}, teacher);
      $('.modal').modal('show');
    },
    save() {
      let _this = this;
      // 保存校验
      if (1 != 1
          || !Validator.require(_this.teacher.name, "姓名")
          || !Validator.length(_this.teacher.name, "姓名", 1, 50)
          || !Validator.length(_this.teacher.nickname, "昵称", 1, 50)
          || !Validator.length(_this.teacher.image, "头像", 1, 100)
          || !Validator.length(_this.teacher.position, "职位", 1, 50)
          || !Validator.length(_this.teacher.motto, "座右铭", 1, 50)
          || !Validator.length(_this.teacher.intro, "简介", 1, 500)
      ) {
        return;
      }
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/teacher/save`,
          _this.teacher
      ).then((response) => {
        const responseDto = response.data;
        if (responseDto.success) {
          Toast.success("保存成功！");
          // console.log("新增讲师列表结果：", responseDto.content);
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
      Confirm.show("删除讲师后不可恢复，确认删除?", () => {
        _this.$ajax.delete(`${process.env.VUE_APP_SERVER}/business/admin/teacher/delete/${id}`)
            .then(response => {
              // console.log("删除讲师列表结果：", response);
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
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/teacher/list`, {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        // console.log("查询讲师列表结果：", response);
        const responseDto = response.data;
        _this.teachers = responseDto.content.list;
        _this.$refs.pagination.render(page, responseDto.content.total);
      })
    }

    // list() {
    //   const _this = this;
    //   // _this.$ajax.get('http://localhost:9000/business/admin/teacher/list')
    //   // jquery ajax 默认是表单的方式 ，axios ajax 默认是以JSON的方式
    //   // 在这里需要在 TeacherController 所对应的 list() 方法行参上加上 @RequestBody 注解
    //   _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/teacher/list`, {
    //     page: 2,
    //     size: 5
    //   })
    //       .then(response => {
    //         // console.log(response);
    //         _this.teachers = response.data.list;
    //       })
    // }
  }
}
</script>

<style scoped>

</style>