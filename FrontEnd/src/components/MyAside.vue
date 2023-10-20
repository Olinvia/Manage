
<template>
  <el-menu style="height: 100%" default-active="/Home"
           :collapse="isCollapse" :collapse-transition="false">
    <el-menu-item index="/Home">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item index="/One">
      <i class="el-icon-s-flag"></i>
      <span slot="title">导航一</span>
    </el-menu-item>

    <el-menu-item index="/Two">
      <i class="el-icon-s-opportunity"></i>
      <span slot="title">导航二</span>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>

</style>

<script>
  export default {
    name: "MyAside",
    data(){
      return{
        //isCollapse:false
      }
    },
    props:{
      isCollapse:Boolean
    }
  }

</script>