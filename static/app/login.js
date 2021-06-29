Vue.component("login", {
    data: function () {
        return {
            user: {}
        }
    },
    template: ` 
<div>
<div class="container">
  <div class="mx-auto" style="width: 200px;">
<h1>Login </h1> 

  </div>
  <div class="row">	
				<div class="col"> <h3>Username:</h3> </div><div class="col">  <input type="text" v-model="user.username" > </div>
		
				<div class="col"> <h3>Password:</h3> </div><div class="col"> <input v-model="user.password" type="password" > </div>
	</div>

    <div class="row" style="padding: 40px;">
    <div class="col"></div>
    <div class="col"></div>
    <div class="col"></div>
    <div class="col"></div>
    <div class="col"></div>
    <div class="col"></div>
    <div class="col">		<input  type="button" class="btn btn-primary" value="Login" v-on:click="login()"/>
    </div>	 <div class="col"></div>
                </div>
	
</div>
</div>		  
`,


    methods: {
        init: function () {

        },
        login: function () {
            axios.post('users/logIn', JSON.stringify(this.user))
                .then(function (response) {

                })
                .catch(function (error) {
                    alert(error.response.data);
                });
        }
    },
    mounted() {
        axios.get('users/currentUser')
            .then(res => {
                console.log(res);
            })
            .catch(err => {
                console.error(err);
            })
    }
});