Vue.component('usersadmin', {
    data: function () {
        return {
            users: [],
            sfs: {
                username: '',
                firstname: '',
                lastname: '',
                sortCrit: 'USERNAME',
                sortDirection: 'ASC',
                filterRole: 'ALL',
                filterType: 'ALL'
            },
            types: []
        }
    },
    template: `
    <div width="80%">
    <h3 style= "text-align: center;">
    All tickets
</h3>
<div class="d-flex justify-content-center">
<div class="input-group d-flex justify-content-center">
<span class="input-group-text">Username</span>
<input type="text" v-model="sfs.username" placeholder="username" style="width: 5%;">
<span class="input-group-text">First name</span>
<input type="text" v-model="sfs.firstname" placeholder="first name" style="width: 5%;">
<span class="input-group-text">Last name</span>
<input type="text" v-model="sfs.lastname" placeholder="last name" style="width: 5%;">
<span class="input-group-text">Sort by</span>
<select v-model="sfs.sortCrit">
    <option value="USERNAME">Username</option>
    <option value="FIRST_NAME">First name</option>
    <option value="LAST_NAME">Last name</option>
    <option value="POINTS">Points</option>
</select>
<span class="input-group-text">Sort order</span>
<select v-model="sfs.sortDirection">
<option value="ASC">Ascending</option>
<option value="DESC">Descending</option>
</select>
<span class="input-group-text">Filter by type</span>
<select v-model="sfs.filterType">
<option value="ALL">NONE</option>
<option v-for="type of types" :value="type">{{type}}</option>
</select>
<span class="input-group-text">Role</span>
<select v-model="sfs.filterRole">
<option value="ALL">All</option>
<option value="CLIENT">CLIENT</option>
<option value="SELLER">SELLER</option>
<option value="ADMIN">ADMIN</option>
</select>
<button type="button" class="btn btn-primary" v-on:click="search">Search</button>
</div>
</div>

    <table class="table table-striped table-dark">
        <thead>
            <tr>
                <th scope="col">Username</th>
                <th scope="col">Type</th>
                <th scope="col">First name</th>
                <th scope="col">Last name</th>
                <th scope="col">Points</th>
                <th scope="col">Type</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="user in this.users" :key="user.uuid">
                <td> {{user.username}} </td>
                <td> {{user.userRole}} </td>
                <td> {{user.firstName}} </td>
                <td> {{user.lastName}} </td>
                <td> {{user.points}} </td>
                <td v-if="user.loyaltyCategory"> {{user.loyaltyCategory.name}} </td>
                <td v-else="user.loyaltyCategory"> No type </td>
                <td>
                <button v-bind:hidden="user.userRole == 'ADMIN'" type="button" class="btn btn-danger" v-on:click="deleteUser(user.uuid)">Delete</button>
            </td>
            </tr>
        </tbody>
    </table>

</div>
    `,
    methods: {
        deleteUser: function (id) {
            let self = this;
            axios.delete('users/delete/' + id)
                .then(res => {
                    self.users = self.users.filter(m => m.uuid !== id)
                })
                .catch(err => {
                    console.error(err);
                })
        },
        search: function () {
            let self = this;
            let tmp = '?';
            Object.entries(this.sfs).forEach(([key, val]) => tmp = tmp + key + '=' + val + '&');
            axios.get('users/allForAdmin' + tmp)
                .then(res => {


                    self.users = res.data;
                    self.users.forEach(
                        user => {
                            if (user.userRole != 'CLIENT') {
                                user.points = 'X';
                            }
                        }
                    );
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
    mounted() {
        let self = this;
        axios.get('users/categories')
            .then(res => {
                self.types = JSON.stringify(res.data)
                    .replace('[', '')
                    .replace('"', '')
                    .replace(']', '')
                    .replace('"', '')
                    .split(',');
            })
            .catch(err => {
                console.error(err);
            })
        axios.get('users/allForAdmin')
            .then(res => {
                this.users = res.data;
                this.users.forEach(user => {
                    if (user.userRole != 'CLIENT') {
                        user.points = 'X';
                    }
                });
            })
            .catch(err => {
                console.error(err);
            });
        console.log(this.users);

    }
})