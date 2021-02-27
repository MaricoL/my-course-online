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
      <div class="col-md-4 col-lg-3" v-for="course in courses" :key="course.id">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg"/>
          <img v-show="course.image" class="media-object" :src="course.image"/>
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">
                {{ COURSE_LEVEL | optionObj(course.level) }}
              </span>
              <span class="pull-right label label-pink info-label">
                {{ COURSE_CHARGE | optionObj(course.charge) }}
              </span>
              <span class="pull-right label label-purple info-label">
                {{ COURSE_STATUS | optionObj(course.status) }}
              </span>
            </div>

            <h3 class="search-title">
              <a href="#" class="blue">{{ course.name }}</a>
            </h3>
            <p>
              <span class="blue bolder bigger-150">{{ course.price }} <i class="fa fa-rmb"></i></span>
            </p>
            <p>{{ course.summary }}</p>
            <p>
              <span class="badge badge-info">{{ course.id }}</span>&nbsp;
              <span class="badge badge-info">排序：{{ course.sort }}</span>&nbsp;
              <span class="badge badge-info">时长：{{ course.time | formatTimeFilter }}</span>&nbsp;
            </p>
            <p>
              <button class="btn btn-white btn-xs btn-success btn-round" @click="toChapter(course)">
                <i class="ace-icon fa fa-book green"></i>
                大章
              </button>&nbsp;
              <button class="btn btn-white btn-xs btn-default btn-round" @click="edit(course)">
                <i class="ace-icon fa fa-pencil blue"></i>
                编辑
              </button>&nbsp;
              <button class="btn btn-white btn-xs btn-warning btn-round" @click="del(course.id)">
                <i class="ace-icon fa fa-trash-o orange"></i>
                删除
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>

    <!--    <table id="simple-table" class="table  table-bordered table-hover">-->
    <!--      <thead>-->
    <!--      <tr>-->
    <!--        <th>id</th>-->
    <!--        <th>名称</th>-->
    <!--        <th>概述</th>-->
    <!--        <th>时长</th>-->
    <!--        <th>价格（元）</th>-->
    <!--        <th>封面</th>-->
    <!--        <th>级别</th>-->
    <!--        <th>收费</th>-->
    <!--        <th>状态</th>-->
    <!--        <th>报名数</th>-->
    <!--        <th>顺序</th>-->
    <!--        <th>创建时间</th>-->
    <!--        <th>修改时间</th>-->
    <!--        <th>操作</th>-->
    <!--      </tr>-->
    <!--      </thead>-->

    <!--      <tbody>-->
    <!--      <tr v-for="course in courses" :key="course.id">-->
    <!--        <td>{{ course.id }}</td>-->
    <!--        <td>{{ course.name }}</td>-->
    <!--        <td>{{ course.summary }}</td>-->
    <!--        <td>{{ course.time }}</td>-->
    <!--        <td>{{ course.price }}</td>-->
    <!--        <td>{{ course.image }}</td>-->
    <!--        <td>{{ COURSE_LEVEL | optionObj(course.level) }}</td>-->
    <!--        <td>{{ COURSE_CHARGE | optionObj(course.charge) }}</td>-->
    <!--        <td>{{ COURSE_STATUS | optionObj(course.status) }}</td>-->
    <!--        <td>{{ course.enroll }}</td>-->
    <!--        <td>{{ course.sort }}</td>-->
    <!--        <td>{{ course.createdAt }}</td>-->
    <!--        <td>{{ course.updatedAt }}</td>-->
    <!--        <td>-->
    <!--          &lt;!&ndash; 在小屏幕和超小屏幕上隐藏 &ndash;&gt;-->
    <!--          <div class="hidden-sm hidden-xs btn-group">-->
    <!--            <button class="btn btn-xs btn-info" @click="edit(course)">-->
    <!--              <i class="ace-icon fa fa-pencil bigger-120"></i>-->
    <!--            </button>-->

    <!--            <button class="btn btn-xs btn-danger" @click="del(course.id)">-->
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
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="名称" v-model="course.name">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">概述</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="概述" v-model="course.summary">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="时长" v-model="course.time">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">价格（元）</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="价格（元）" v-model="course.price">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">封面</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="封面" v-model="course.image">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">级别</label>
                <div class="col-sm-10">
                  <select class="form-control" v-model="course.level">
                    <option v-for="COURSE_LEVELObj in COURSE_LEVEL" :value="COURSE_LEVELObj.key">
                      {{ COURSE_LEVELObj.value }}
                    </option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                  <select class="form-control" v-model="course.charge">
                    <option v-for="COURSE_CHARGEObj in COURSE_CHARGE" :value="COURSE_CHARGEObj.key">
                      {{ COURSE_CHARGEObj.value }}
                    </option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">状态</label>
                <div class="col-sm-10">
                  <select class="form-control" v-model="course.status">
                    <option v-for="COURSE_STATUSObj in COURSE_STATUS" :value="COURSE_STATUSObj.key">
                      {{ COURSE_STATUSObj.value }}
                    </option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">报名数</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="报名数" v-model="course.enroll">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="顺序" v-model="course.sort">
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
  name: "business-course",
  components: {
    Pagination
  },
  filters: {
    optionObj: Filter.optionObj,
    formatTimeFilter: Filter.formatTime
  },
  data() {
    return {
      course: {},
      courses: [],
      COURSE_LEVEL,
      COURSE_CHARGE,
      COURSE_STATUS,
    }
  },
  mounted() {
    let _this = this;
    // 每页 5 条数据
    _this.$refs.pagination.size = 5;
    _this.list(1);
    // 激活样式方法一：
    // 调用父组件的 activeSideBar 方法
    // this.$parent.activeSideBar('business-course-sidebar');
    // this.list();
  },
  methods: {
    add() {
      // $('.modal').modal('show');
      let _this = this;
      // 清空模态框中的文字
      _this.course = {};
      $('.modal').modal({
        show: true,
        backdrop: 'static'  //  禁止点击模态框外部时关闭
      });
    },
    edit(course) {
      let _this = this;
      // _this.course = course 会在修改模态框中的字段时，自动修改背后数据表格的值
      // _this.course = course;
      // _this.course = $.extend({}, course);
      _this.course = Object.assign({}, course);
      $('.modal').modal('show');
    },
    save() {
      let _this = this;
      // 保存校验
      if (1 != 1
          || !Validator.require(_this.course.name, "名称")
          || !Validator.length(_this.course.name, "名称", 1, 50)
          || !Validator.length(_this.course.summary, "概述", 1, 2000)
          || !Validator.length(_this.course.image, "封面", 1, 100)
      ) {
        return;
      }
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/course/save`,
          _this.course
      ).then((response) => {
        const responseDto = response.data;
        if (responseDto.success) {
          Toast.success("保存成功！");
          // console.log("新增课程列表结果：", responseDto.content);
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
      Confirm.show("删除课程后不可恢复，确认删除?", () => {
        _this.$ajax.delete(`${process.env.VUE_APP_SERVER}/business/admin/course/delete/${id}`)
            .then(response => {
              // console.log("删除课程列表结果：", response);
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

      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/course/list`, {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        // console.log("查询课程列表结果：", response);
        const responseDto = response.data;
        _this.courses = responseDto.content.list;
        _this.$refs.pagination.render(page, responseDto.content.total);
      })
    },
    toChapter(course) {
      SessionStorage.set("course", course);
      this.$router.push("/business/chapter");
    }

    // list() {
    //   const _this = this;
    //   // _this.$ajax.get('http://localhost:9000/business/admin/course/list')
    //   // jquery ajax 默认是表单的方式 ，axios ajax 默认是以JSON的方式
    //   // 在这里需要在 CourseController 所对应的 list() 方法行参上加上 @RequestBody 注解
    //   _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/course/list`, {
    //     page: 2,
    //     size: 5
    //   })
    //       .then(response => {
    //         // console.log(response);
    //         _this.courses = response.data.list;
    //       })
    // }
  }
}
</script>

<style scoped>
.caption h3 {
  font-size: 30px;
}
</style>