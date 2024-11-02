// import {createRouter, createWebHistory} from 'vue-router';

// const router = createRouter({

//     history: createWebHistory(import.meta.env.BASE_URL),
//     routes: [
//         {
//             path: '/',
//             name: 'home',
//             component:HomeVieWw

//         }
//     ]
// })

// export default router;
// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import Summary from '../views/Summary.vue'
import Login from '../views/Login.vue'

const routes = [
  {
    path: '/',
    name: 'Summary',
    component: Summary
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router