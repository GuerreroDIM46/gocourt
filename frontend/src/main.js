import { createApp } from 'vue'
import '@/scss/styles.scss'
import * as bootstrap from 'bootstrap'
import { createRouter, createWebHashHistory } from 'vue-router'
import { createPinia } from 'pinia'
import PrimeVue from 'primevue/config'
import 'primevue/resources/themes/aura-light-green/theme.css'
import BadgeDirective from 'primevue/badgedirective'
import Home from '@/components/Home.vue'
import App from './App.vue'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import {  faTrash, faCircleXmark, faMagnifyingGlass, faPenToSquare, faUserPlus, faXmark } from '@fortawesome/free-solid-svg-icons' //iconos de muestra
import { faInstagram, faXTwitter } from '@fortawesome/free-brands-svg-icons' //iconos de muestra


library.add( faInstagram, faXTwitter, faTrash, faCircleXmark, faMagnifyingGlass, faPenToSquare, faUserPlus, faXmark)



const NotFound = () => import('@/components/NotFound.vue')
const Partidos = () => import('@/components/Partidos.vue')
const Federados = () => import('@/components/Federados.vue')
const Principiantes = () => import('@/components/Principiantes.vue')


const routes = [
    { path: '/', redirect: '/home'},
    { path: '/home', component: Home, name: 'home' },
    { path: '/partidos', component: Partidos, name: 'partidos' },
    { path: '/federados', component: Federados, name: 'federados' },
    { path: '/principiantes', component: Principiantes, name: 'principiantes' },
    { path: '/:pathMatch(.*)', component: NotFound, name: 'notfound'}
    
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
    })

const app = createApp(App)
const pinia = createPinia()

app.component('font-awesome-icon', FontAwesomeIcon)
app.use(router)
app.use(pinia)
app.use(PrimeVue)
app.directive('badge', BadgeDirective)
app.mount('#app')
