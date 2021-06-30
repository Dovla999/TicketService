Vue.component('manifseller', {
    data: function () {
        return {
            manifs: [],
            sfs: {}
        }
    },
    template: `
    <div width="80%">
    <h3>
    My manifestations
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
                <th scope="col"></th>
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
                <td >
                    <button type="button" class="btn btn-primary" v-on:click="details(manif.uuid)">Edit</button>
                </td>

            </tr>
        </tbody>
    </table>

</div>
    `,
    methods: {
        details: function (id) {
            this.$router.push({name: 'EditManifestation', params: {'id': id}});
        },
    },
    mounted() {
        console.log('hello')
        let self = this;
        axios.get('manifestations/sellerManifestations')
            .then(res => {
                console.log(res.data);
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

})