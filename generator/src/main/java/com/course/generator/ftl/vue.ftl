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
      <tr><#list fieldList as field>
          <#if field.nameHump != "createAt" && field.nameHump != "updateAt"><th>${field.nameCn}</th></#if>
        </#list>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="${domain} in ${domain}s" :key="${domain}.id">
          <#list fieldList as field>
            <#if field.nameHump != "createAt" && field.nameHump != "updateAt">
              <th>{{ ${domain}.${field.nameHump} }}</th>
            </#if>
          </#list>
        <td>
          <!-- 在小屏幕和超小屏幕上隐藏 -->
          <div class="hidden-sm hidden-xs btn-group">
            <button class="btn btn-xs btn-info" @click="edit(${domain})">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button class="btn btn-xs btn-danger" @click="del(${domain}.id)">
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
                <#list fieldList as field>
                  <#if field.nameHump != "id" && field.nameHump != "createdAt" && field.nameHump != "updatedAt">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">${field.nameCn}</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" placeholder="${field.nameCn}" v-model="${domain}.${field.nameHump}">
                        </div>
                    </div>
                  </#if>
                </#list>
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
import Toast from '../../../public/static/js/Toast'
import Loading from '../../../public/static/js/Loading'
import Confirm from '../../../public/static/js/Confirm'
import Validator from '../../../public/static/js/Validator'

export default {
  name: "${domain}",
  components: {
    Pagination
  },
  data() {
    return {
      ${domain}: {},
      ${domain}s: []
    }
  },
  mounted() {
    let _this = this;
    // 每页 5 条数据
    _this.$refs.pagination.size = 5;
    _this.list(1);
    // 激活样式方法一：
    // 调用父组件的 activeSideBar 方法
    // this.$parent.activeSideBar('${MODULE}-${domain}-sidebar');
    // this.list();
  },
  methods: {
    add() {
      // $('.modal').modal('show');
      let _this = this;
      // 清空模态框中的文字
      _this.${domain} = {};
      $('.modal').modal({
        show: true,
        backdrop: 'static'  //  禁止点击模态框外部时关闭
      });
    },
    edit(${domain}) {
      let _this = this;
      // _this.${domain} = ${domain} 会在修改模态框中的字段时，自动修改背后数据表格的值
      // _this.${domain} = ${domain};
      // _this.${domain} = $.extend({}, ${domain});
      _this.${domain} = Object.assign({}, ${domain});
      $('.modal').modal('show');
    },
    save() {
      let _this = this;
      // 保存校验
        if(1 != 1
        <#list fieldList as field>
          <#if field.nameHump != "id" && field.nameHump != "createdAt" && field.nameHump != "updatedAt" && field.nameHump != "sort">
            <#if !field.nullable>
              || !Validator.require(_this.${domain}.${field.nameHump}, "${field.nameCn}")
            </#if>
            <#if (field.length > 0)>
              || !Validator.length(_this.${domain}.${field.nameHump}, "${field.nameCn}", 1, ${field.length})
            </#if>
          </#if>
        </#list>
        ){
        return;
      }
      Loading.show();
      _this.$ajax.post(`${"$"}{process.env.VUE_APP_SERVER}/${MODULE}/admin/${domain}/save`,
          _this.${domain}
      ).then((response) => {
        Loading.hide();
        const responseDto = response.data;
        if (responseDto.success) {
          Toast.success("保存成功！");
          // console.log("新增${tableNameCn}列表结果：", responseDto.content);
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
      Confirm.show("删除${tableNameCn}后不可恢复，确认删除?", () => {
        // loading框
        Loading.show();
        _this.$ajax.delete(`${"$"}{process.env.VUE_APP_SERVER}/${MODULE}/admin/${domain}/delete/${"$"}{id}`)
            .then(response => {
              // console.log("删除${tableNameCn}列表结果：", response);
              let responseDto = response.data;
              if (responseDto.success) {
                Loading.hide();
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
      _this.$ajax.post(`${"$"}{process.env.VUE_APP_SERVER}/${MODULE}/admin/${domain}/list`, {
        page: page,
        size: _this.$refs.pagination.size,
      }).then((response) => {
        // console.log("查询${tableNameCn}列表结果：", response);
        const responseDto = response.data;
        _this.${domain}s = responseDto.content.list;
        _this.$refs.pagination.render(page, responseDto.content.total);
      })
    }

    // list() {
    //   const _this = this;
    //   // _this.$ajax.get('http://localhost:9000/${MODULE}/admin/${domain}/list')
    //   // jquery ajax 默认是表单的方式 ，axios ajax 默认是以JSON的方式
    //   // 在这里需要在 ${Domain}Controller 所对应的 list() 方法行参上加上 @RequestBody 注解
    //   _this.$ajax.post(`${"$"}{process.env.VUE_APP_SERVER}/${MODULE}/admin/${domain}/list`, {
    //     page: 2,
    //     size: 5
    //   })
    //       .then(response => {
    //         // console.log(response);
    //         _this.${domain}s = response.data.list;
    //       })
    // }
  }
}
</script>

<style scoped>

</style>