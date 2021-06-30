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
                filterType: 'NONE',
                filterSoldOut: 'NO'
            }
        }
    },
    template: `
    <div width="80%">
    <h3>
    All manifestations
    </h3>
    <input type="text" v-model="sfs.name" placeholder="name">
    <input type="text" v-model="sfs.location" placeholder="location">
    <input type="date" v-model="sfs.dateStart" >
    <input type="date" v-model="sfs.dateEnd" >
    <input type="number" v-model="sfs.priceStart" >
    <input type="number" v-model="sfs.priceEnd" >
    <select v-model="sfs.sortCrit">
        <option value="NAME">Name</option>
        <option value="DATE">Date</option>
        <option value="TICKET_PRICE">Ticket price</option>
        <option value="LOCATION">Location</option>
   </select>
   <select v-model="sfs.sortDirection">
   <option value="ASC">Ascending</option>
   <option value="DESC">Descending</option>
</select>
<br><br>
    <table class="table table-striped table-dark">
        <thead>
            <tr>
                <th scope="col">Manifestation</th>
                <th scope="col">Capacity</th>
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
                    axios.get('manifestations/all')
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
        }
    },
    mounted() {
        let self = this;
        axios.get('manifestations/all')
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