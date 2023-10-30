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

      <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>


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
        <el-form-item label="账号" prop="num">
          <el-input style="width: 200px" v-model="form.num"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="ps">
          <el-input style="width: 200px" v-model="form.ps"></el-input>
        </el-form-item>

        <el-form-item label="名字" prop="userName">
          <el-input style="width: 200px" v-model="form.userName"></el-input>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input style="width: 200px" v-model="form.age"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="电话" prop="phone">
          <el-input style="width: 200px" v-model="form.phone"></el-input>
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
  name: "AdminManage",
  data() {
    let checkAge = (rule, value, callback)=>{
      if(value>150){
        callback(new Error("你是王八吗这么能活？"));
      }else{
        callback();
      }
    };
    let checkDuplicate = (rule,value,callback)=>{
      if(this.form.id){
        return callback();
      }
      this.$axios.post(this.$httpUrl+"/user/findByNum?num="+this.form.num).then(res=>res.data).then(res=>{
        if(res.code === 400){
          callback();
        }else{
          callback(new Error('该账号已存在！'));
        }
      })
    };
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
      ],
      centerDialogVisible:false,
      form:{
        id:'',
        num:'',
        ps:'',
        userName:'',
        age:'',
        phone:'',
        sex:'1',
        roleId:'1'
      },
      rules:{
        userName:[
          {required: true,message: '请输入名字', trigger: 'blur'},
          {min: 3,max: 8,message: '长度在3到8个字符之间',trigger: 'blur'}
        ],
        num:[
          {required: true,message: '请输入账号', trigger: 'blur'},
          {min: 1,max: 20,message: '长度在1到20个字符之间',trigger: 'blur'},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
        ps:[
          {required: true,message: '请输入密码', trigger: 'blur'},
          {min: 1,max: 20,message: '长度在1到20个字符之间',trigger: 'blur'}
        ],
        age:[
          {required: true,message: '请输入年龄', trigger: 'blur'},
          {min: 1,max: 3,message: '年龄在1到999之间',trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/, message: "年龄必须为正整数", trigger: 'blur' },
          {validator: checkAge, trigger: 'blur'}
        ],
        phone:[
          {required: true,message: '请输入手机号', trigger: 'blur'},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
        ]


      }
    }
  },
  methods:{
    loadGet(){
      this.$axios.post(this.$httpUrl+'/user/list').then(res=>res.data).then(res=>{
        console.log(res)
        this.tableData=res
      })
    },
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
        this.form.num = row.num;
        this.form.sex = row.sex+'';
        this.form.userName = row.userName;
        this.form.ps = '';
        this.form.phone = row.phone;
        this.form.age = row.age+'';
      })


    },
    del(id){
      this.$axios.post(this.$httpUrl+'/user/delete?id='+id).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/user/save',this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/user/mod',this.form).then(res=>res.data).then(res=>{
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
      this.sex=''
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/user/result',{
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param:{
          name:this.name,
          sex:this.sex,
          roleId:'1'
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