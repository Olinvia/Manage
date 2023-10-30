<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入仓库名" style="width: 200px"
                suffix-icon="el-icon-search" @keyup.enter.native="loadPost"></el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>

      <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>


    </div>
    <el-table :data="tableData" :header-cell-style="{backgroundColor:'#f2f5fc'}" border>
      <el-table-column prop="id" label="id" width="60" align="center">
      </el-table-column>
      <el-table-column prop="name" label="仓库名" width="180" align="center">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="300" align="center">
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="180" align="center">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="mod(scope.row)" style="margin-right: 5px">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="del(scope.row.id)">
            <el-button slot="reference" size="small" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
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
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="名字" prop="name">
          <el-input style="width: 200px" v-model="form.name"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" style="width: 200px" v-model="form.remark"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>

<script>
export default {
  name: "StorageManage",
  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name:'',
      centerDialogVisible:false,
      form:{
        id:'',
        name:'',
        remark:''
      },
      rules:{
        name:[
          {required: true,message: '请输入仓库', trigger: 'blur'},
          {min: 1,max: 8,message: '长度在1到8个字符之间',trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    resetForm(){
      this.$refs.form.resetFields();
    },
    add(){
      this.centerDialogVisible = true
      this.$nextTick(()=>{
        this.resetForm()
      })
    },
    mod(row){
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.form.id = row.id;
        this.form.name = row.name;
        this.form.remark = row.remark;
      })
    },
    del(id){
      this.$axios.post(this.$httpUrl+'/storage/delete?id='+id).then(res=>res.data).then(res=>{
        if(res.code === 200){
          this.$message({
            message: '删除成功！',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            message: '删除失败！',
            type: 'error'
          });
        }
      })
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/storage/save',this.form).then(res=>res.data).then(res=>{
        if(res.code === 200){
          this.$message({
            message: '插入成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
        }else{
          this.$message({
            message: '插入失败！',
            type: 'error'
          });
        }
      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/storage/mod',this.form).then(res=>res.data).then(res=>{
        if(res.code === 200){
          this.$message({
            message: '修改成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
        }else{
          this.$message({
            message: '修改失败！',
            type: 'error'
          });
        }
      })
    },
    save(){
      this.$refs.form.validate((valid)=>{
        if(valid){
          if(this.form.id){
            this.doMod();
          }else{
            this.doSave();
          }
        }else{
          console.log('wrong submit');
          return false;
        }
      });
    },
    resetParam(){
      this.name = ''
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/storage/result',{
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param:{
          name:this.name
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
    this.loadPost();
  }
};

</script>