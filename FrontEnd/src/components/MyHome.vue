<template>
  <div>
    <h1 align="center">欢迎来到个人中心！</h1>
    <el-descriptions :title="'欢迎你！'+user.userName" :column="2" size="40" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
            账号
        </template>
        {{user.num}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          电话
        </template>
        {{user.phone}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          性别
        </template>
        <el-tag :type="user.sex ==='1'?'primary':'danger'" disable-transitions>
          <i :class="user.sex ==1?'el-icon-male':'el-icon-female'"></i>
          {{user.sex ==1 ?"男":"女"}}
        </el-tag>
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          角色
        </template>
        <el-tag
            :type="user.roleId === 0 ? 'danger': (user.roleId === 1 ? 'primary': 'success')"
            disable-transitions>
          {{user.roleId === 0 ? '超级管理员': (user.roleId === 1 ? '管理员': '普通用户')}}
        </el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<style scoped>

</style>

<script>
  export default {
    name: "MyHome",
    data(){
      return{
        user:JSON.parse(sessionStorage.getItem('CurUser'))
      }
    },
    computed:{

    },
    methods:{
      init(){
        this.user=JSON.parse(sessionStorage.getItem('CurUser'))
      }
    }
  }

</script>