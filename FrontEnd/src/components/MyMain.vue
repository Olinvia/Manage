<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入名字" style="width: 200px"
                suffix-icon="el-icon-search" @keyup.enter.native="loadPost"></el-input>

      <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 15px;">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>
    <el-table :data="tableData" :header-cell-style="{backgroundColor:'#f2f5fc'}" border>
      <el-table-column prop="id" label="id" width="60" align="center">
      </el-table-column>
      <el-table-column prop="num" label="账号" width="180" align="center">
      </el-table-column>
      <el-table-column prop="userName" label="姓名" width="180" align="center">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80" align="center">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="80" align="center">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.sex === 1 ? 'primary':'success'"
            disable-transitions>{{scope.row.sex === 1 ? '男':'女'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色" width="180" align="center">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger': (scope.row.roleId === 1 ? 'primary': 'success')"
              disable-transitions>{{scope.row.roleId === 0 ? '超级管理员': (scope.row.roleId === 1 ? '管理员': '普通用户')}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="180" align="center">
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="180" align="center">
        <el-button size="small" type="success">编辑</el-button>
        <el-button size="small" type="danger">删除</el-button>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<style scoped>

</style>

<script>
export default {
  name: "MyMain",
  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name:'',
      sex:'',
      sexs:[
        {
          value: '1',
          label: '男'
          },{
          value: '0',
          label: '女'
        }
      ]
    }
  },
  methods:{
    loadGet(){
      this.$axios.get(this.$httpUrl+'/user/list').then(res=>res.data).then(res=>{
        console.log(res)
        this.tableData=res
      })
    },
    resetParam(){
      this.name = ''
      this.sex=''
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/user/result',{
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param:{
          name:this.name,
          sex:this.sex
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code == 200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert("获取数据失败！")
        }
      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    }
  },
  beforeMount() {
    //this.loadGet();
    this.loadPost();
  }
};

</script>