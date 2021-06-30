Vue.component('ticketsadmin', {
    data: function () {
        return {
            tickets: [],
            sfs: {}
        }
    },
    template: `
    <div width="80%">
    <h3>
    All tickets
</h3>
    <table class="table table-striped table-dark">
        <thead>
            <tr>
                <th scope="col">Manifestation</th>
                <th scope="col">Type</th>
                <th scope="col">Price</th>
                <th scope="col">Date</th>
                <th scope="col">ID</th>
                <th scope="col">Owner</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="ticket in this.tickets" :key="ticket.uuid">
                <td> {{ticket.manifestation.name}} </td>
                <td> {{ticket.ticketType}} </td>
                <td> {{ticket.ticketPrice}} </td>
                <td> {{ticket.manifestation.datetime}} </td>
                <td> {{ticket.id}} </td>
                <td>
                    {{ticket.owner.username}}
                </td>
                <td>
                <button type="button" class="btn btn-danger" v-on:click="deleteTicket(ticket.uuid)">Delete</button>
            </td>
            </tr>
        </tbody>
    </table>

</div>
    `,
    methods: {
        deleteTicket: function (id) {
            let self = this;
            axios.delete('tickets/delete/' + id)
                .then(res => {
                    self.tickets = self.tickets.filter(m => m.uuid !== id)
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
    mounted() {
        axios.get('tickets/adminTickets')
            .then(res => {

                this.tickets = res.data;
                for (t of this.tickets) {
                    t.manifestation.datetime = (new Date(t.manifestation.dateTime)).toLocaleString();
                }
            })
            .catch(err => {
                console.error(err);
            })
    }
})