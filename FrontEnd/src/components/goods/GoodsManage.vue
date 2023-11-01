<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入物品名" style="width: 200px"
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

      <el-button type="primary" style="margin-left: 5px;" @click="add" v-if="user.roleId!==2">新增</el-button>
      <el-button type="primary" style="margin-left: 5px;" @click="inGoods" v-if="user.roleId!==2">入库</el-button>
      <el-button type="primary" style="margin-left: 5px;" @click="outGoods" v-if="user.roleId!==2">出库</el-button>


    </div>
    <el-table :data="tableData" :header-cell-style="{backgroundColor:'#f2f5fc'}" border
              highlight-current-row @current-change="selectCurrentChange">
      <el-table-column prop="id" label="id" width="60" align="center">
      </el-table-column>
      <el-table-column prop="name" label="物品名" width="180" align="center">
      </el-table-column>
      <el-table-column prop="storage" label="仓库" width="180" align="center"
                      :formatter="formatStorage">
      </el-table-column>
      <el-table-column prop="goodstype" label="物品类名" width="180" align="center"
                       :formatter="formatGoodstype">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="180" align="center">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="300" align="center">
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="180" align="center" v-if="user.roleId!==2">
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
        title="新增"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="物品名" prop="name">
          <el-input style="width: 200px" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="仓库" prop="storage">
          <el-select v-model="form.storage" placeholder="请选择仓库">
            <el-option
                v-for="item in storageData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="物品类名" prop="goodstype">
          <el-select v-model="form.goodstype" placeholder="请选择仓库">
            <el-option
                v-for="item in storageData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-input style="width: 200px" v-model="form.count"></el-input>
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

    <el-dialog
        title="入库"
        :visible.sync="inDialogVisible"
        width="30%"
        center>
      <el-form ref="form1" :rules="rules1" :model="form1" label-width="80px">
        <el-form-item label="物品名">
          <el-input style="width: 200px" v-model="form1.goodsname" readonly></el-input>
        </el-form-item>

        <el-form-item label="数量" prop="count">
          <el-input style="width: 200px" v-model="form1.count"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" style="width: 200px" v-model="form1.remark"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="inDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doInGoods">确 定</el-button>
  </span>
    </el-dialog>

    <el-dialog
        title="出库"
        :visible.sync="outDialogVisible"
        width="30%"
        center>
      <el-form ref="form2" :rules="rules2" :model="form1" label-width="80px">
        <el-form-item label="物品名">
          <el-input style="width: 200px" v-model="form1.goodsname" readonly></el-input>
        </el-form-item>

        <el-form-item label="数量" prop="count">
          <el-input style="width: 200px" v-model="form1.count"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" style="width: 200px" v-model="form1.remark"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="outDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doOutGoods">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<style scoped>

</style>

<script>
export default {
  name: "GoodsManage",
  data() {
    let checkCount = (rule,value,callback)=>{
      if(value > 9999){
        callback(new Error('数量过大！'));
      }else{
        callback();
      }
    };
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      goodstypeData: [],
      storageData: [],
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name:'',
      storage: '',
      goodstype: '',
      centerDialogVisible:false,
      inDialogVisible:false,
      outDialogVisible:false,
      currentRow: {},
      form:{
        id:'',
        name:'',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },
      form1:{
        id:'',
        goods:'',
        goodsname:'',
        count:'',
        userid:'9',
        adminid:'',
        remark:''
      },
      rules:{
        name:[
          {required: true,message: '请输入物品名', trigger: 'blur'},
          {min: 1,max: 8,message: '长度在1到8个字符之间',trigger: 'blur'}
        ],
        storage:[
          {required: true,message: '请选择仓库名', trigger: 'blur'}
        ],
        goodstype:[
          {required: true,message: '请选择物品类型', trigger: 'blur'}
        ],
        count:[
          {required: true,message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数', trigger: 'blur'},
          {validator: checkCount, trigger: 'blur'}
        ]
      },
      rules1:{
        count:[
          {required: true,message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数', trigger: 'blur'},
          {validator: checkCount, trigger: 'blur'}
        ]
      },
      rules2:{
        count:[
          {required: true,message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数', trigger: 'blur'},
          {validator: checkCount, trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    formatStorage(row){
      let temp = this.storageData.find(item=>{
        return item.id == row.storage
      })
      return temp && temp.name
    },

    formatGoodstype(row){
      let temp = this.goodstypeData.find(item=>{
        return item.id == row.goodstype
      })
      return temp && temp.name
    },

    resetForm(){
      this.$refs.form.resetFields();
    },
    resetInForm(){
      this.$refs.form1.resetFields();
    },
    resetOutForm(){
      this.$refs.form2.resetFields();
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
        this.form.storage = row.storage;
        this.form.goodstype = row.goodstype;
        this.form.count = row.count;
        this.form.remark = row.remark;
      })
    },
    del(id){
      this.$axios.post(this.$httpUrl+'/goods/delete?id='+id).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/goods/save',this.form).then(res=>res.data).then(res=>{

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
      this.$axios.post(this.$httpUrl+'/goods/mod',this.form).then(res=>res.data).then(res=>{
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
            this.form.id='';
          }else{
            this.doSave();
            this.form.id='';
          }
        }else{
          console.log('wrong submit');
          return false;
        }
      });
    },
    resetParam(){
      this.name = ''
      this.storage=''
      this.goodstype=''
    },
    inGoods(){
      if(!this.currentRow.id){
        alert("请选择物品！")
        return;
      }
      this.inDialogVisible = true;
      this.$nextTick(()=>{
        this.resetInForm()
      })

      this.form1.goods = this.currentRow.id;
      this.form1.goodsname = this.currentRow.name;
      this.form1.adminid = this.user.id;

    },

    doInGoods(){
      this.$refs.form1.validate((valid)=>{
        if(valid){
          this.$axios.post(this.$httpUrl+'/record/save',this.form1).then(res=>res.data).then(res=>{
            if(res.code === 200){
              this.$message({
                message: '入库成功！',
                type: 'success'
              });
              this.inDialogVisible = false
              this.loadPost()
              this.resetInForm()
            }else{
              this.$message({
                message: '入库失败！',
                type: 'error'
              });
            }
          })
        }else{
          console.log('wrong submit');
          return false;
        }
      });
    },

    outGoods(){
      if(!this.currentRow.id){
        alert("请选择物品！")
        return;
      }
      this.outDialogVisible = true;
      this.$nextTick(()=>{
        this.resetOutForm()
      })

      this.form1.goods = this.currentRow.id;
      this.form1.goodsname = this.currentRow.name;
      this.form1.adminid = this.user.id;

    },
    doOutGoods(){
      this.$refs.form2.validate((valid)=>{
        if(valid){
          if(this.form1.count > this.currentRow.count){
            alert("库存不足！")
            return;
          }
          this.$axios.post(this.$httpUrl+'/record/save1',this.form1).then(res=>res.data).then(res=>{
            if(res.code === 200){
              this.$message({
                message: '出库成功！',
                type: 'success'
              });
              this.outDialogVisible = false
              this.loadPost()
              this.resetOutForm()
            }else{
              this.$message({
                message: '出库失败！',
                type: 'error'
              });
            }
          })
        }else{
          console.log('wrong submit');
          return false;
        }
      });
    },


    loadPost(){
      this.$axios.post(this.$httpUrl+'/goods/result',{
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param:{
          name:this.name,
          storage: this.storage+'',
          goodstype: this.goodstype+''
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

    selectCurrentChange(val){
      this.currentRow = val;
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