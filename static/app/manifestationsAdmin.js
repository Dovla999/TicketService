Vue.component('manifestationsadmin', {
    data: function () {
        return {
            manifs: [],
            sfs: {}
        }
    },
    template: `
    <div width="80%">
    <h3>
    My tickets
</h3>
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