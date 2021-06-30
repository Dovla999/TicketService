const Registration = {template: '<registration></registration>'}
const Login = {template: '<login></login>'}
const NewManifestation = {template: '<newmanifestation></newmanifestation>'}
const HomePage = {template: '<homepage></homepage>'}
const Manifestation = {template: '<manifestation></manifestation>'}
const Cart = {template: '<cart></cart>'}
const MyTicketsClient = {template: '<myticketsclient></myticketsclient>'}
const NewSeller = {template: '<newseller></newseller>'}
const AdminManifs = {template: '<manifestationsadmin></manifestationsadmin>'}
const AdminTickets = {template: '<ticketsadmin></ticketsadmin>'}
const AdminUsers = {template: '<usersadmin></usersadmin>'}
const AdminComments = {template: '<commentsadmin></commentsadmin>'}
const Profile = {template: '<profile></profile>'}
const ManifSeller = {template: '<manifseller></manifseller>'}
const TicketsSeller = {template: '<tickets-seller></tickets-seller>'}
const SellerComments = {template: '<sellercomments></sellercomments>'}
const EditManif = {template: '<editmanif></editmanif>'}

axios.defaults.baseURL = 'http://localhost:8080/api/'
const router = new VueRouter({
    mode: 'hash',
    routes: [
        {path: '/', component: HomePage},
        {path: '/registration', component: Registration},
        {path: '/login', component: Login},
        {path: '/newManifestation', component: NewManifestation},
        {path: '/manifestation/:id', name: 'Manifestation', component: Manifestation},
        {path: '/cart', component: Cart},
        {path: '/ticketsclient', component: MyTicketsClient},
        {path: '/newseller', component: NewSeller},
        {path: '/adminmanifs', component: AdminManifs},
        {path: '/ticketsadmin', component: AdminTickets},
        {path: '/usersadmin', component: AdminUsers},
        {path: '/commentsadmin', component: AdminComments},
        {path: '/profile', component: Profile},
        {path: '/manifseller', component: ManifSeller},
        {path: '/sellertickets', component: TicketsSeller},
        {path: '/sellercomments', component: SellerComments},
        {path: '/editmanifestation/:id', name: 'EditManifestation', component: EditManif}
    ]
});


var app = new Vue({
    router,
    el: '#tickets',
    data: {
        userRole: 'NONE'
    },
    methods: {
        logout: function () {
            let self = this;
            this.$root.$emit('loggingIn', 'NONE');
            axios
                .get("users/logout")
                .then(function (resp) {
                });

            window.location.href = "#/";
        }
    },
    mounted() {
        this.$root.$on('loggingIn', (role) => {
            this.userRole = role;
        })
    }
});