const Registration = {template: '<registration></registration>'}
const Login = {template: '<login></login>'}


axios.defaults.baseURL = 'http://localhost:8080/api/'
const router = new VueRouter({
    mode: 'hash',
    routes: [
        {path: '/registration', component: Registration},
        {path: '/login', component: Login}
    ]
});


var app = new Vue({
    router,
    el: '#tickets'
});