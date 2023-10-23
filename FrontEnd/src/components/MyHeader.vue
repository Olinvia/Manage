<template>
  <div style="display: flex;line-height: 60px;">
    <div>
        <i :class="icon" style="cursor: pointer;font-size: 20px;margin-top: 20px" @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center; font-size: 34px;">
      <span>欢迎来到仓库管理系统</span>
    </div>
    <span>{{user.userName}}</span>
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
    data(){
      return{
        user: JSON.parse(sessionStorage.getItem('CurUser'))
      }
    },
    props:{
      icon:String
    },
    methods:{
      toUser(){
        this.$router.push("/MyHome")
      },
      logout(){
        this.$router.push("/")
        sessionStorage.clear()
      },
      collapse(){
        this.$emit('doCollapse')
      }
    },
    created(){
      this.$router.push("/MyHome")
    }
  };
</script>