import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "@/views/Home";
import Challenges from "@/views/Challenges";
import Scoreboard from "@/views/Scoreboard";
import Users from "@/views/Users";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/challenges',
    name: 'challenge',
    component: Challenges
  },
  {
    path: '/users',
    name: 'users',
    component: Users
  },
  {
    path: '/scoreboard',
    name: 'scoreboard',
    component: Scoreboard
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
