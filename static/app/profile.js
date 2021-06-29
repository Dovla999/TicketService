Vue.component('profile', {
    data: function () {
        return {
            user: {}
        }
    },
    template: `
    <div width="80%">
    <h3>
    {{user}}
</h3>


</div>
    `,
    methods: {},
    mounted() {
        axios.get('users/currentUser')
            .then(res => {
                this.user = res.data;
            })
            .catch(err => {
                console.error(err);
            })
    }
})