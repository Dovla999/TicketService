Vue.component('manifestationsadmin', {
    data: function () {
        return {
            manifs: [],
            sfs: {
                name: '',
                location: '',
                dateStart: '',
                dateEnd: '',
                priceStart: 0,
                priceEnd: 9999999,
                sortCrit: 'NAME',
                sortDirection: 'ASC',
                filterType: 'ALL',
                filterSoldOut: 'ALL'
            },
            types: {}
        }
    },
    template: `
    <div width="80%">
    <h3 style= "text-align: center;">
    All manifestations
    </h3>
   
    <div class="d-flex justify-content-center">
    <div class="input-group d-flex justify-content-center">
    <span class="input-group-text">Title</span>
    <input type="text" v-model="sfs.name" placeholder="name" style="width: 5%;">
    <span class="input-group-text">Location</span>
    <input type="text" v-model="sfs.location" placeholder="location" style="width: 5%;">
    <span class="input-group-text">After</span>
    <input type="date" v-model="sfs.dateStart" >
    <span class="input-group-text">Before</span>
    <input type="date" v-model="sfs.dateEnd" >
    <span class="input-group-text">From</span>
    <input type="number" v-model="sfs.priceStart" style="width: 4%;" >
    <span class="input-group-text">To</span>
    <input type="number" v-model="sfs.priceEnd" style="width: 4%;" >
    <span class="input-group-text">Sort by</span>
    <select v-model="sfs.sortCrit">
        <option value="NAME">Name</option>
        <option value="DATE">Date</option>
        <option value="TICKET_PRICE">Ticket price</option>
        <option value="LOCATION">Location</option>
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
<span class="input-group-text">Sold out</span>
<select v-model="sfs.filterSoldOut">
<option value="ALL">All</option>
<option value="NO">NO</option>
<option value="YES">YES</option>
</select>
<button type="button" class="btn btn-primary" v-on:click="search">Search</button>
</div>
</div>

<br><br>
    <table class="table table-striped table-dark">
        <thead>
            <tr>
                <th scope="col">Manifestation</th>
                <th scope="col">Capacity</th>
                <th scope="col">Location</th>
                <th scope="col">Price</th>
                <th scope="col">Date</th>
                <th scope="col">Type</th>
                <th scope="col">Rating</th>
                <th scope="col">Active</th>
                <th scope="col">Delete</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="manif in this.manifs" :key="manif.uuid">
                <td> {{manif.name}} </td>
                <td> {{manif.capacity}} </td>
                <td> {{manif.location.address}} </td>
                <td> {{manif.ticketPrice}} </td>
                <td> {{manif.datetime}} </td>
                <td> {{manif.type}} </td>
                <td> {{manif.rating}} </td>
                <td v-if="manif.active">
                    <button type="button" class="btn btn-danger" v-on:click="activate(manif.uuid)">Deactivate</button>
                </td>
                <td v-else>
                <button type="button" class="btn btn-success" v-on:click="activate(manif.uuid)">Activate</button>
                </td>
                <td>
                <button type="button" class="btn btn-danger" v-on:click="deleteManif(manif.uuid)">Delete</button>
                </td>
            </tr>
        </tbody>
    </table>

</div>
    `,
    methods: {
        activate: function (id) {
            let self = this;
            axios.get('manifestations/activate/' + id)
                .then(res => {
                    axios.get('manifestations/allForAdmin')
                        .then(res => {


                            self.manifs = res.data;
                            self.manifs.forEach(
                                t => {
                                    console.log(t);
                                    if (t.rating === 0) t.rating = 'No rating yet';
                                    t.datetime = (new Date(t.dateTime)).toLocaleString();

                                }
                            );
                        })
                        .catch(err => {
                            console.error(err);
                        })
                })
                .catch(err => {
                    console.error(err);
                })

        },
        deleteManif: function (id) {
            let self = this;
            axios.delete('manifestations/delete/' + id)
                .then(res => {
                    self.manifs = self.manifs.filter(m => m.uuid !== id)
                })
                .catch(err => {
                    console.error(err);
                })
        },
        search: function () {
            let self = this;
            let tmp = '?';
            Object.entries(this.sfs).forEach(([key, val]) => tmp = tmp + key + '=' + val + '&');
            axios.get('manifestations/allForAdmin' + tmp)
                .then(res => {


                    self.manifs = res.data;
                    self.manifs.forEach(
                        t => {
                            if (t.rating === 0) t.rating = 'No rating yet';
                            t.datetime = (new Date(t.dateTime)).toLocaleString();

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
        axios.get('manifestations/getTypes')
            .then(res => {
                self.types = res.data;
            })
            .catch(err => {
                console.error(err);
            })
        axios.get('manifestations/allForAdmin')
            .then(res => {

                self.manifs = res.data;
                self.manifs.forEach(
                    t => {
                        console.log(t);
                        if (t.rating === 0) t.rating = 'No rating yet';
                        t.datetime = (new Date(t.dateTime)).toLocaleString();

                    }
                );

            })
            .catch(err => {
                console.error(err);
            })
    }
})