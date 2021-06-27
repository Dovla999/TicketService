const Registration = {template: '<registration></registration>'}
const Login = {template: '<login></login>'}
const NewManifestation = {template: '<newmanifestation></newmanifestation>'}
const HomePage = {template: '<homepage></homepage>'}


axios.defaults.baseURL = 'http://localhost:8080/api/'
const router = new VueRouter({
    mode: 'hash',
    routes: [
        {path: '/', component: HomePage},
        {path: '/registration', component: Registration},
        {path: '/login', component: Login},
        {path: '/newManifestation', component: NewManifestation}

    ]
});


var app = new Vue({
    router,
    el: '#tickets'
});