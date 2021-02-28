<template>
  <div>
    <div class="row">
      <div class="col-md-6">
        <p>
          <button @click="add1()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增一级
          </button>
          &nbsp;
          <button @click="all()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
          </button>
        </p>
        <table id="level1-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="category in level1" :key="category.id" @click="showLevel2(category)"
              :class="{'active': active.id === category.id}">
            <td>{{ category.id }}</td>
            <td>{{ category.name }}</td>
            <td>{{ category.sort }}</td>
            <td>
              <!-- 在小屏幕和超小屏幕上隐藏 -->
              <div class="hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" @click="edit(category)">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button class="btn btn-xs btn-danger" @click="del(category.id)">
                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>

              </div>

              <!-- 在中屏幕和大屏幕上隐藏 -->
              <div class="hidden-md hidden-lg">
                <div class="inline pos-rel">
                  <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                          data-position="auto">
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
      </div>
      <div class="col-md-6">
        <p>
          <button @click="add2()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增二级
          </button>
          &nbsp;
          <button id="btn2" @click="all()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
          </button>
        </p>
        <table id="level2-table" class="table  table-bordered table-hover">
          <thead>
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="category in level2" :key="category.id">
            <td>{{ category.id }}</td>
            <td>{{ category.name }}</td>
            <td>{{ category.sort }}</td>
            <td>
              <!-- 在小屏幕和超小屏幕上隐藏 -->
              <div class="hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" @click="edit(category)">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>

                <button class="btn btn-xs btn-danger" @click="del(category.id)">
                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
                </button>

              </div>

              <!-- 在中屏幕和大屏幕上隐藏 -->
              <div class="hidden-md hidden-lg">
                <div class="inline pos-rel">
                  <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                          data-position="auto">
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
      </div>
    </div>


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
                <label class="col-sm-2 control-label">父分类</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ category.parent === '00000000' ? '无' : active.name }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="名称" v-model="category.name">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" placeholder="顺序" v-model="category.sort">
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

export default {
  name: "business-category",
  data() {
    return {
      category: {},
      categorys: [],
      level1: [],
      level2: [],
      active: {},
    }
  },
  mounted() {
    let _this = this;

    _this.all(1);
    // 激活样式方法一：
    // 调用父组件的 activeSideBar 方法
    // this.$parent.activeSideBar('business-category-sidebar');
    // this.list();
  },
  methods: {
    add1() {
      // $('.modal').modal('show');
      let _this = this;
      _this.category = {
        parent: '00000000'
      };
      $('.modal').modal({
        show: true,
        backdrop: 'static'  //  禁止点击模态框外部时关闭
      });
    },
    add2() {
      // $('.modal').modal('show');
      let _this = this;
      if (Tool.isEmpty(_this.active)) {
        Toast.warning('请先选择一级分类！');
        return;
      }
      _this.category = {
        parent: _this.active.id
      };
      $('.modal').modal({
        show: true,
        backdrop: 'static'  //  禁止点击模态框外部时关闭
      });
    },
    edit(category) {
      let _this = this;
      // _this.category = category 会在修改模态框中的字段时，自动修改背后数据表格的值
      // _this.category = category;
      // _this.category = $.extend({}, category);
      _this.category = Object.assign({}, category);
      $('.modal').modal('show');
    },
    save() {
      let _this = this;
      // 保存校验
      if (1 != 1
          || !Validator.require(_this.category.parent, "父id")
          || !Validator.require(_this.category.name, "名称")
          || !Validator.length(_this.category.name, "名称", 1, 50)
      ) {
        return;
      }
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/category/save`,
          _this.category
      ).then((response) => {
        const responseDto = response.data;
        if (responseDto.success) {
          Toast.success("保存成功！");
          // console.log("新增分类列表结果：", responseDto.content);
          // 关闭模态框
          $('.modal').modal('hide');
          // 刷新表格数据
          _this.all(1);
        } else {
          Toast.warning(responseDto.message);
        }

      })
    },
    del(id) {
      let _this = this;
      Confirm.show("删除分类后不可恢复，确认删除?", () => {
        _this.$ajax.delete(`${process.env.VUE_APP_SERVER}/business/admin/category/delete/${id}`)
            .then(response => {
              // console.log("删除分类列表结果：", response);
              let responseDto = response.data;
              if (responseDto.success) {
                // 刷新表格数据
                _this.all();
                Toast.success("删除成功！");
              }
            })
      })

    },

    all() {
      console.log("当前应用服务请求地址：" + process.env.VUE_APP_SERVER);

      let _this = this;
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/category/all`).then((response) => {
        // console.log("查询分类列表结果：", response);
        const responseDto = response.data;
        _this.categorys = responseDto.content;

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
    showLevel2(category) {
      this.level2 = category.children;
      this.active = category;
    }


  }
}
</script>

<style scoped>
.active td {
  background-color: #bfc000 !important;
}
</style>