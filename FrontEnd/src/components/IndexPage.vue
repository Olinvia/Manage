<template>
  <el-container style="height: 100%; border: 1px solid #eee">
  <el-aside :width="aside_width" style="background-color: rgb(238, 241, 246);height: 100vh">
    <MyAside :isCollapse="isCollapse"></MyAside>
  </el-aside>

  <el-container>
    <el-header style="text-align: right; font-size: 12px;border-bottom: rgb(128,128,128) 1px solid;">
      <MyHeader @doCollapse="doCollapse" :icon="icon"></MyHeader>
    </el-header>

    <el-main>
      <MyMain></MyMain>
    </el-main>
  </el-container>
  </el-container>
</template>

<style>
.el-header {
  //background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-main {
  padding: 5px;
}

.el-aside {
  color: #333;
}
</style>

<script>
import MyAside from "@/components/MyAside.vue";
import MyHeader from "@/components/MyHeader.vue";
import MyMain from "@/components/MyMain.vue";

export default {
  name: 'IndexPage',
  components: {MyMain, MyHeader, MyAside},
  data(){
    return{
      isCollapse:false,
      aside_width:'200px',
      icon:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse(){
      this.isCollapse = !this.isCollapse
      if(!this.isCollapse){
        this.aside_width = '200px'
        this.icon = 'el-icon-s-fold'
      }else{
        this.aside_width = '64px'
        this.icon = 'el-icon-s-unfold'
      }
    }
  }
};
</script>