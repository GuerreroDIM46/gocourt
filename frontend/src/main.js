import { createApp } from 'vue'
import '@/scss/styles.scss'
import * as bootstrap from 'bootstrap'
import { createRouter, createWebHashHistory } from 'vue-router'
import { createPinia } from 'pinia'
import PrimeVue from 'primevue/config'
import { format } from 'date-fns'
import { es } from 'date-fns/locale'
import 'primevue/resources/themes/aura-light-green/theme.css'
import BadgeDirective from 'primevue/badgedirective'
import App from './App.vue'
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import {  faTrash, faCircleXmark, faMagnifyingGlass, faPenToSquare, faUserPlus, faXmark, faAngleLeft, faAngleRight, faAngleDoubleLeft, faAngleDoubleRight} from '@fortawesome/free-solid-svg-icons' //iconos de muestra
import { faInstagram, faXTwitter } from '@fortawesome/free-brands-svg-icons' 


library.add( faInstagram, faXTwitter, faTrash, faCircleXmark, faMagnifyingGlass, faPenToSquare, faUserPlus, faXmark, faAngleLeft, faAngleRight, faAngleDoubleLeft, faAngleDoubleRight)



const NotFound = () => import('@/components/NotFound.vue')
const Partidos = () => import('@/views/VistaPartidos.vue')
const Jugadores = () => import('@/views/VistaJugadores.vue')
const LandingPage = () => import('@/views/LandingPage.vue')



const routes = [
    { path: '/', redirect: '/jugadores'},
    { path: '/partidos', component: Partidos, name: 'partidos' },
    { path: '/jugadores', component: Jugadores, name: 'jugadores' },
    { path: '/landingPage/:id', component: LandingPage, name: 'landingPage' },
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
app.config.globalProperties.$formatDate = (date, formatStr = 'PPpp') => {
    return format(new Date(date), formatStr, { locale: es })
}
app.mount('#app')