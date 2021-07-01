Vue.component('profile', {
    data: function () {
        return {
            user: {
                birthdate: new Date().toISOString().split('T')[0],
                loyaltyCategory: {}
            }
        }
    },
    template: `<div>
    <div class="container">
      <div class="mx-auto" style="width: 200px;">
    <h1>Profile </h1> 
    
      </div>
      <div class="row">	
                    <div class="col"> <h3>Username:</h3> </div><div class="col">  <input type="text" disabled="true" v-model="user.username" > </div>
            
                    <div class="col"> <h3>Password:</h3> </div><div class="col"> <input v-model="user.password" type="password" > </div>
        </div>
        <div class="row">
                    <div class="col"> <h3>First name:</h3> </div><div class="col"> <input type="text" v-model="user.firstName" > </div>
            
                    <div class="col"> <h3>Last name:</h3> </div><div class="col">  <input type="text" v-model="user.lastName" > </div>
        </div>	
        <div class="row">	
                    <div class="col"> <h3>Gender:</h3> </div>
                    <div class="col">
                        <select name="gender" id="gender" v-model="user.userGender" >
                          <option value="FEMALE">FEMALE</option>
                          <option value="MALE">MALE</option>
                        </select>
                    </div> 
                
                    <div class="col"><h3>Birthdate:</h3></div>
                    <div class="col"> <input type="date" id="birthday" v-model="user.birthdate"> </div>
        </div>
        <div v-if="user.userRole==='CLIENT'" class="row">	
        <br>
        <div class="col"> </div>
    
        <div class="col">
        <h3>Loyalty category: {{user.loyaltyCategory.name}}</h3> 
        <h3>Discount: {{(1-user.loyaltyCategory.discount).toFixed(2)}}%</h3>
        <h3>Points: {{user.points.toFixed(2)}}</h3></div>

        <div class="col"></div>

</div>
          <div class="row" style="padding: 40px;">
            <div class="col"></div>
            <div class="col"></div>
            <div class="col"></div>
            <div class="col"></div>
            <div class="col"></div>
            <div class="col"></div>
            <div class="col">		<input  type="button" class="btn btn-primary" value="Update" v-on:click="update()"/>
            </div>	 <div class="col"></div>
                        </div>
    </div>
    
        
    </div>		  
    
            `,
    methods: {
        update: function () {
            axios.put('users/update', JSON.stringify(this.user))
                .then(res => {
                    alert("Profile updated!");
                })
                .catch(err => {
                    alert(err.response.data);
                })
        }

    },
    mounted() {
        axios.get('users/currentUser')
            .then(res => {
                this.user = res.data;
                this.user.birthdate = new Date(this.user.birthDate.year,
                    this.user.birthDate.month - 1,
                    this.user.birthDate.day + 1
                ).toISOString().split('T')[0];
                console.log(this.user);
            })
            .catch(err => {
                console.error(err);
            })
    }
})