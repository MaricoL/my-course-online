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
        <th>id</th>
        <th>父id</th>
        <th>名称</th>
        <th>顺序</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="category in categorys" :key="category.id">
              <td>{{ category.id }}</td>
              <td>{{ category.parent }}</td>
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
                        <label class="col-sm-2 control-label">父id</label>
                        <div class="col-sm-10">
                          <input type="text" class="form-control" placeholder="父id" v-model="category.parent">
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
import Pagination from "../../components/pagination";

export default {
  name: "business-category",
  components: {
    Pagination
  },
  data() {
    return {
      category: {},
      categorys: [],
    }
  },
  mounted() {
    let _this = this;
    // 每页 5 条数据
    _this.$refs.pagination.size = 5;
    _this.list(1);
    // 激活样式方法一：
    // 调用父组件的 activeSideBar 方法
    // this.$parent.activeSideBar('business-category-sidebar');
    // this.list();
  },
  methods: {
    add() {
      // $('.modal').modal('show');
      let _this = this;
      // 清空模态框中的文字
      _this.category = {};
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
        if(1 != 1
              || !Validator.require(_this.category.parent, "父id")
              || !Validator.require(_this.category.name, "名称")
              || !Validator.length(_this.category.name, "名称", 1, 50)
        ){
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
          _this.list(1);
        }else{
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
                _this.list(1);
                Toast.success("删除成功！");
              }
            })
      })

    },
    list(page) {
      console.log("当前应用服务请求地址：" + process.env.VUE_APP_SERVER);

      let _this = this;
      _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/category/list`, {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        // console.log("查询分类列表结果：", response);
        const responseDto = response.data;
        _this.categorys = responseDto.content.list;
        _this.$refs.pagination.render(page, responseDto.content.total);
      })
    }

    // list() {
    //   const _this = this;
    //   // _this.$ajax.get('http://localhost:9000/business/admin/category/list')
    //   // jquery ajax 默认是表单的方式 ，axios ajax 默认是以JSON的方式
    //   // 在这里需要在 CategoryController 所对应的 list() 方法行参上加上 @RequestBody 注解
    //   _this.$ajax.post(`${process.env.VUE_APP_SERVER}/business/admin/category/list`, {
    //     page: 2,
    //     size: 5
    //   })
    //       .then(response => {
    //         // console.log(response);
    //         _this.categorys = response.data.list;
    //       })
    // }
  }
}
</script>

<style scoped>

</style>