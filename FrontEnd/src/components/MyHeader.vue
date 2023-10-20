<template>
  <div style="display: flex;line-height: 60px;">
    <div>
        <i :class="icon" style="cursor: pointer;font-size: 20px;margin-top: 20px" @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center; font-size: 34px;">
      <span>欢迎来到仓库管理系统</span>
    </div>
    <span>王小虎</span>
    <el-dropdown style="cursor: pointer;">
      <i class="el-icon-arrow-down" style="margin-left: 15px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<style scoped>

</style>

<script>
  export default {
    name: "MyHeader",
    props:{
      icon:String
    },
    methods:{
      toUser(){
        console.log('to_user')
      },
      logout(){
        console.log('logout')
      },
      collapse(){
        this.$emit('doCollapse')
      }
    }
  };
</script>