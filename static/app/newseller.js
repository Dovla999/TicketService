Vue.component("newseller", {
    data: function () {
        return {
            user: {
                gender: 'MALE'
            }
        }
    },
    template: ` 
<div>
<div class="container">
  <div class="mx-auto" style="width: 200px;">
<h1>Seller </h1> 

  </div>
  <div class="row">	
				<div class="col"> <h3>Username:</h3> </div><div class="col">  <input type="text" v-model="user.username" > </div>
		
				<div class="col"> <h3>Password:</h3> </div><div class="col"> <input v-model="user.password" type="password" > </div>
	</div>
    <div class="row">
				<div class="col"> <h3>First name:</h3> </div><div class="col"> <input type="text" v-model="user.firstname" > </div>
		
				<div class="col"> <h3>Last name:</h3> </div><div class="col">  <input type="text" v-model="user.lastname" > </div>
	</div>	
    <div class="row">	
				<div class="col"> <h3>Gender:</h3> </div>
				<div class="col">
					<select  v-model="user.gender" >
					  <option value="FEMALE">FEMALE</option>
					  <option value="MALE">MALE</option>
					</select>
				</div> 
			
				<div class="col"><h3>Birthdate:</h3></div>
				<div class="col"> <input type="date" id="birthday" v-model="user.birthdate"> </div>
    </div>
	  <div class="row" style="padding: 40px;">
        <div class="col"></div>
        <div class="col"></div>
        <div class="col"></div>
        <div class="col"></div>
        <div class="col"></div>
        <div class="col"></div>
        <div class="col">		<input  type="button" class="btn btn-primary" value="Register" v-on:click="register()"/>
        </div>	 <div class="col"></div>
                    </div>
</div>

	
</div>		  
`,


    methods: {
        init: function () {

        },
        register: function () {
            axios.post('users/newSeller', JSON.stringify(this.user))
                .then(function (response) {
                    alert(response.data);
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