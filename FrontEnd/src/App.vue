<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>



export default {
  name: 'App',
  components: {

  },
  data(){
    return{
      user: JSON.parse(sessionStorage.getItem('CurUser')),
    }
  },
  watch:{
    '$store.state.menu':{
      handler(val,old){
        if(!old && this.user && this.user.num){
          this.$store.commit("setMenu",val)
        }
      },
      immediate: true
    }
  }
}
</script>

<style>
#app {
  height: 100%;
}
</style>
