import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AdminEbookView from '../views/admin/Admin-EbookView.vue'
import AdminCategoryView from "@/views/admin/Admin-CategoryView.vue";
import AdminDocView from "@/views/admin/Admin-DocView.vue";
import DocView from "@/views/DocView.vue";


const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },
    {
        path: '/admin/ebook',
        name: 'adminEbook',
        component: AdminEbookView
    },
    {
        path: '/admin/category',
        name: 'adminCategory',
        component: AdminCategoryView
    },
    {
        path: '/admin/doc',
        name: 'adminDoc',
        component: AdminDocView
    },
    {
        path: '/doc',
        name: 'doc',
        component: DocView
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
