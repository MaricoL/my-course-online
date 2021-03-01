<template>
  <div>
    <h2 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" data-toggle="modal" class="pink"> {{ course.name }} </router-link>
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" data-toggle="modal" class="pink"> {{ chapter.name }} </router-link>
    </h2>

    <hr>

    <p>
      <router-link to="/business/chapter" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        返回
      </router-link>
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
        <th>标题</th>
        <th>课程</th>
        <th>大章</th>
        <th>视频</th>
        <th>时长</th>
        <th>收费</th>
        <th>顺序</th>
        <th>创建时间</th>
        <th>修改时间</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="section in sections" :key="section.id">
        <td>{{ section.id }}</td>
        <td>{{ section.title }}</td>
        <td>{{ section.courseId }}</td>
        <td>{{ section.chapterId }}</td>
        <td>{{ section.video }}</td>
        <td>{{ section.time | formatTimeFilter}}</td>
        <td>{{ CHARGE | optionObjFilter(section.charge) }}</td>
        <td>{{ section.sort }}</td>
        <td>{{ section.createdAt }}</td>
        <td>{{ section.updatedAt }}</td>
        <td>
          <!-- 在小屏幕和超小屏幕上隐藏 -->
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-info" @click="edit(section)">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button class="btn btn-xs btn-danger" @click="del(section.id)">
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
                <label class="col-sm-2 control-label">标题</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="标题" v-model="section.title">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ course.name }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">大章</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ chapter.name }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">视频</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="视频" v-model="section.video">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="时长" v-model="section.time">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                  <select class="form-control" v-model="section.charge">
                    <option v-for="chargeObj in CHARGE" :value="chargeObj.key">{{ chargeObj.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="顺序" v-model="section.sort">
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
  name: "business-section",
  components: {
    Pagination
  },
  // filters: {
  //   // chargeFilter: Filter.optionKV
  //   chargeFilter: Filter.optionObj,
  //   formatTimeFilter: Filter.formatTime
  // },
  data() {
    return {
      section: {},
      sections: [],
      CHARGE: SECTION_CHARGE,
      course: {},
      chapter: {}
    }
  },
  mounted() {
    let _this = this;
    let course = SessionStorage.get("course") || {};
    let chapter = SessionStorage.get("chapter") || {};
    if (Tool.isEmpty(chapter) || Tool.isEmpty(chapter)) {
      _this.$router.push("/welcome");
    } else {
      _this.course = course;
      _this.chapter = chapter;
      // 每页 5 条数据
      _this.$refs.pagination.size = 5;
      _this.list(1);
      // 激活样式方法一：
      // 调用父组件的 activeSideBar 方法
      // this.$parent.activeSideBar('business-section-sidebar');
      // this.list();
    }
  },
  methods: {
    add() {
      // $('.modal').modal('show');
      let _this = this;
      // 清空模态框中的文字
      _this.section = {};
      $('.modal').modal({
        show: true,
        backdrop: 'static'  //  禁止点击模态框外部时关闭
      });
    },
    edit(section) {
      let _this = this;
      // _this.section = section 会在修改模态框中的字段时，自动修改背后数据表格的值
      // _this.section = section;
      // _this.section = $.extend({}, section);
      _this.section = Object.assign({}, section);
      $('.modal').modal('show');
    },
    save() {
      let _this = this;
      // 保存校验
      if (1 != 1 || !Validator.require(_this.section.title, "标题") || !Validator.length(_this.section.title, "标题", 1, 50) || !Validator.length(_this.section.video, "视频", 1, 200)
      ) {
        return;
      }
      _this.section.courseId = this.course.id;
      _this.section.chapterId = this.chapter.id;
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/section/save`,
          _this.section
      ).then((response) => {
        const responseDto = response.data;
        if (responseDto.success) {
          Toast.success("保存成功！");
          // console.log("新增小节列表结果：", responseDto.content);
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
      Confirm.show("删除小节后不可恢复，确认删除?", () => {
        _this.$ajax.delete(`${process.env.VUE_APP_SERVER}/business/admin/section/delete/${id}`)
            .then(response => {
              // console.log("删除小节列表结果：", response);
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
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/section/list`, {
        page: page,
        size: _this.$refs.pagination.size,
        courseId: _this.course.id,
        chapterId: _this.chapter.id
      }).then((response) => {
        // console.log("查询小节列表结果：", response);
        const responseDto = response.data;
        _this.sections = responseDto.content.list;
        _this.$refs.pagination.render(page, responseDto.content.total);
      })
    }

    // list() {
    //   const _this = this;
    //   // _this.$ajax.get('http://localhost:9000/business/admin/section/list')
    //   // jquery ajax 默认是表单的方式 ，axios ajax 默认是以JSON的方式
    //   // 在这里需要在 SectionController 所对应的 list() 方法行参上加上 @RequestBody 注解
    //   _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/section/list`, {
    //     page: 2,
    //     size: 5
    //   })
    //       .then(response => {
    //         // console.log(response);
    //         _this.sections = response.data.list;
    //       })
    // }
  }
}
</script>

<style scoped>

</style>