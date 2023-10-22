import VueRouter from "vue-router";

const routes = [
    {
        path:'/',
        name:'login',
        component:()=>import('../components/MyLogin.vue')
    },
    {
        path:'/IndexPage',
        name:'index',
        component:()=>import('../components/IndexPage.vue')
    }
]

const router = new VueRouter({
    mode:'history',
    routes
})

export default router;
