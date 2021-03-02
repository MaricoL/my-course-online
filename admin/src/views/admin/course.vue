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
                {{ COURSE_LEVEL | optionObjFilter(course.level) }}
              </span>
              <span class="pull-right label label-pink info-label">
                {{ COURSE_CHARGE | optionObjFilter(course.charge) }}
              </span>
              <span class="pull-right label label-purple info-label">
                {{ COURSE_STATUS | optionObjFilter(course.status) }}
              </span>
            </div>

            <h3 class="search-title">
              <a href="#" class="blue">{{ course.name }}</a>
            </h3>

            <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})" class="profile-activity clearfix">
              <div>
                <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image">
                <a class="user" href="#"> {{teacher.name}} </a>
                <br>
                {{teacher.position}}
              </div>
            </div>

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
              <button class="btn btn-white btn-xs btn-success btn-round" @click="editContent(course)">
                <i class="ace-icon fa fa-book green"></i>
                内容
              </button>&nbsp;
              <button v-on:click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                排序
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

    <!-- Modal -->
    <div id="form-modal" class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                <label class="col-sm-2 control-label">分类</label>
                <div class="col-sm-10">
                  <ul id="tree" class="ztree"></ul>
                  <!--                  <input type="text" class="form-control" placeholder="名称" v-model="course.name">-->
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="名称" v-model="course.name">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">讲师</label>
                <div class="col-sm-10">
                  <select v-model="course.teacherId" class="form-control">
                    <option v-for="o in teachers" v-bind:value="o.id">{{o.name}}</option>
                  </select>
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


    <!-- course-content-modal -->
    <div id="course-content-modal" class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
      <div class="modal-dialog width-50">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" data-target="#mymodal-data" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <h5 class="modal-title" id="course-content-title">课程内容</h5>

          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <div class="col-lg-12">
                  {{ saveContentLabel }}
                </div>
              </div>
              <div class="form-group">
                <div class="col-lg-12">
                  <div id="content"></div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" @click="saveContent()">保存</button>
          </div>
        </div>
      </div>
    </div>

    <!-- course-sort-modal -->
    <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">排序</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="control-label col-lg-3">
                  当前排序
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-lg-3">
                  新排序
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.newSort" name="newSort">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              取消
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateSort()">
              <i class="ace-icon fa fa-plus blue"></i>
              更新排序
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
import Pagination from "../../components/pagination";

export default {
  name: "business-course",
  components: {
    Pagination
  },
  data() {
    return {
      course: {},
      courses: [],
      COURSE_LEVEL,
      COURSE_CHARGE,
      COURSE_STATUS,
      tree: {},
      saveContentLabel: '',
      sort: {
        id: '',
        oldSort: 0,
        newSort: 0
      },
      teachers: []
    }
  },
  mounted() {
    let _this = this;
    _this.allCategory();
    _this.allTeachers();
    // 每页 5 条数据
    _this.$refs.pagination.size = 5;
    _this.list(1);
    //
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
      // 取消ztree中所有的勾选
      _this.tree.checkAllNodes(false);
      // 合并所有父节点
      _this.tree.expandAll(false);
      $('#form-modal').modal({
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
      // 查询当前课程所有的分类信息
      _this.listCourseCategory(course.id);
      $('#form-modal').modal('show');
    },
    // 修改课程内容，弹出模态框
    editContent(course) {
      let _this = this;
      _this.course = course;
      $("#content").summernote({
        focus: true,
        height: 300,
      });
      // 先清空历史文本
      $("#content").summernote('code', '');
      // 获取该课程的课程内容
      _this.$ajax.get(`${process.env.VUE_APP_SERVER}/business/admin/course/find-content/${course.id}`)
          .then(response => {
            let responseDto = response.data;
            if (responseDto.success) {
              $('#course-content-modal').modal({
                show: true,
                backdrop: 'static'  //  禁止点击模态框外部时关闭
              });
              if (responseDto.content) {
                $("#content").summernote('code', responseDto.content.content);
              }

              //自动保存功能
              let saveContentInterval = setInterval(() => {
                _this.saveContent();
              }, 5000);
              // 关闭模态框时，清空自动保存功能
              $('#course-content-modal').on('hidden.bs.modal', (e) => {
                clearInterval(saveContentInterval);
              });
            } else {
              Toast.warning(responseDto.message);
            }
          });
    },
    // 保存课程内容
    saveContent() {
      let _this = this;
      let content = $("#content").summernote('code');
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/course/save-content`, {
        id: _this.course.id,
        content
      }).then(response => {
        let responseDto = response.data;
        if (responseDto.success) {
          // Toast.success('内容保存成功！');
          let now = Tool.dateFormat("mm:ss", new Date());
          _this.saveContentLabel = `最后保存时间：${now}`;
        } else {
          Toast.warning(responseDto.message);
        }
      })
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

      // 获取在ztree中被勾选的分类信息列表
      let checkedNodes = _this.tree.getCheckedNodes();
      if (Tool.isEmpty(checkedNodes)) {
        Toast.warning('请选择该课程相应的分类！');
        return;
      }
      _this.course.categorys = checkedNodes;
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/course/save`,
          _this.course
      ).then((response) => {
        const responseDto = response.data;
        if (responseDto.success) {
          Toast.success("保存成功！");
          // console.log("新增课程列表结果：", responseDto.content);
          // 关闭模态框
          $('#form-modal').modal('hide');
          // 刷新表格数据
          _this.list(1);
        } else {
          Toast.warning(responseDto.message);
        }
      });
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
      SessionStorage.set(SESSION_KEY_COURSE, course);
      this.$router.push("/business/chapter");
    },

    // 查询所有分类数据
    allCategory() {
      console.log("当前应用服务请求地址：" + process.env.VUE_APP_SERVER);

      let _this = this;
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/category/all`).then((response) => {
        // console.log("查询分类列表结果：", response);
        const responseDto = response.data;
        _this.categorys = responseDto.content;

        // 初始化分类树
        _this.initTree();

        // 将结果转换为树状结构
        _this.level1 = _this.categorys
            .filter(category => category.parent === '00000000')
            .map(category => {
              if (Tool.isEmpty(category.children)) {
                category.children = [];
              }
              _this.categorys.forEach(child => {
                if (child.parent === category.id) {
                  category.children.push(child);
                }
              })
              return category;
            });

        // 刷新 右侧子分类表格数据
        _this.level2 = [];
        setTimeout(() => {
          $('.active').trigger('click');
        }, 100);


      });
    },
    listCourseCategory(courseId) {
      const _this = this;
      // 展开所有的节点
      _this.tree.expandAll(false);
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/course/list-category/${courseId}`)
          .then(response => {
            const responseDto = response.data;
            const courseCategoryList = responseDto.content;
            // 先取消ztree中所有的勾选
            _this.tree.checkAllNodes(false);
            // 循环遍历 courseCategoryList，勾选ztree相应的checkbox
            courseCategoryList.forEach(courseCategory => {
              const node = _this.tree.getNodeByParam("id", courseCategory.categoryId);
              // 如果是父节点，则说明该父节点下一定还有子节点被勾选，则展开该父节点
              if (node.parent === '00000000') {
                _this.tree.expandNode(node, true);
              }
              _this.tree.checkNode(node, true);
            });
          })
    },

    initTree() {
      let _this = this;
      let setting = {
        check: {
          enable: true
        },
        data: {
          simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parent",
            rootPId: "00000000"
          }
        }
      };

      let zNodes = _this.categorys;

      _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
    },

    // 打开排序模态框
    openSortModal(course) {
      let _this = this;
      _this.sort = {
        id: course.id,
        oldSort: course.sort,
        newSort: course.sort
      }
      $('#course-sort-modal').modal('show');
    },

    // 更新排序
    updateSort() {
      let _this = this;
      if (_this.sort.oldSort === _this.sort.newSort) {
        Toast.warning('排序没有变化！');
        return;
      }
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/course/sort`, _this.sort)
          .then(response => {
            const responseDto = response.data;
            if (responseDto.success) {
              Toast.success('更新排序成功！');
              $('#course-sort-modal').modal('hide');
              _this.list(1);
            } else {
              Toast.error('更新排序失败！');
            }
          })
    },

    // 查询所有讲师
    allTeachers() {
      console.log("当前应用服务请求地址：" + process.env.VUE_APP_SERVER);

      let _this = this;
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/teacher/all`).then((response) => {
        const responseDto = response.data;
        _this.teachers = responseDto.content;
      });
    },

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
  font-size: 25px;
}

@media (max-width: 1199px) {
  .caption h3 {
    font-size: 20px;
  }
}
</style>