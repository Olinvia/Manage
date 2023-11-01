<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="goodsname" placeholder="请输入物品名" style="width: 200px"
                suffix-icon="el-icon-search" @keyup.enter.native="loadPost"></el-input>

      <el-select v-model="storage" placeholder="请选择仓库">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <el-select v-model="goodstype" placeholder="请选择物品分类">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>


    </div>
    <el-table :data="tableData" :header-cell-style="{backgroundColor:'#f2f5fc'}" border>
      <el-table-column prop="id" label="id" width="60" align="center">
      </el-table-column>
      <el-table-column prop="goodsname" label="物品名" width="100" align="center">
      </el-table-column>
      <el-table-column prop="storagename" label="仓库" width="100" align="center">
      </el-table-column>
      <el-table-column prop="goodstypename" label="物品类名" width="100" align="center">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="100" align="center">
      </el-table-column>
      <el-table-column prop="createtime" label="操作时间" width="280" align="center">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人" width="100" align="center">
      </el-table-column>
      <el-table-column prop="username" label="申请人" width="100" align="center">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="300" align="center">
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
  name: "RecordManage",
  data() {

    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      goodstypeData: [],
      storageData: [],
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      goodsname:'',
      storage: '',
      goodstype: '',
      centerDialogVisible:false
    }
  },
  methods:{
    resetForm(){
      this.$refs.form.resetFields();
    },

    resetParam(){
      this.goodsname = ''
      this.storage=''
      this.goodstype=''
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/record/result',{
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param:{
          goodsname:this.goodsname+'',
          storage: this.storage+'',
          goodstype: this.goodstype+'',
          roleId: this.user.roleId+'',
          userId: this.user.id+''
        }
      }).then(res=>res.data).then(res=>{
        if(res.code == 200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert("获取数据失败！")
        }
      })
    },
    loadStorage(){
      this.$axios.get(this.$httpUrl+'/storage/list').then(res=>res.data).then(res=>{
        if(res.code == 200){
          this.storageData=res.data
        }else{
          alert("获取仓库数据失败！")
        }
      })
    },

    loadGoodstype(){
      this.$axios.get(this.$httpUrl+'/goodstype/list').then(res=>res.data).then(res=>{
        if(res.code == 200){
          this.goodstypeData=res.data
        }else{
          alert("获取仓库数据失败！")
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
    this.loadStorage();
    this.loadGoodstype();
    this.loadPost();
  }
};

</script>