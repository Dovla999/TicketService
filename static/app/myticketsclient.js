Vue.component('myticketsclient', {
    data: function () {
        return {
            tickets: [],
            sfs: {
                name: '',
                dateStart: '',
                dateEnd: '',
                priceStart: 0,
                priceEnd: 9999999,
                sortCrit: 'NAME',
                sortDirection: 'ASC',
                filterType: 'ALL',
            }
        }
    },
    template: `
    <div width="80%">
    <h3 style= "text-align: center;">
    My tickets
</h3>
<div class="d-flex justify-content-center">
<div class="input-group d-flex justify-content-center">
<span class="input-group-text">Title</span>
<input type="text" v-model="sfs.name" placeholder="name" style="width: 5%;">
<span class="input-group-text">After</span>
<input type="date" v-model="sfs.dateStart" >
<span class="input-group-text">Before</span>
<input type="date" v-model="sfs.dateEnd" >
<span class="input-group-text">From</span>
<input type="number" v-model="sfs.priceStart" style="width: 5%;" >
<span class="input-group-text">To</span>
<input type="number" v-model="sfs.priceEnd" style="width: 5%;" >
<span class="input-group-text">Sort by</span>
<select v-model="sfs.sortCrit">
    <option value="NAME">Name</option>
    <option value="DATE">Date</option>
    <option value="TICKET_PRICE">Ticket price</option>
</select>
<span class="input-group-text">Sort order</span>
<select v-model="sfs.sortDirection">
<option value="ASC">Ascending</option>
<option value="DESC">Descending</option>
</select>
<span class="input-group-text">Filter by type</span>
<select v-model="sfs.filterType">
<option value="ALL">All</option>
<option value="REGULAR">Regular</option>
<option value="FAN_PIT">Fan pit</option>
<option value="VIP">Vip</option>
</select>
<button type="button" class="btn btn-primary" v-on:click="search">Search</button>
</div>
</div>
    <table class="table table-striped table-dark">
        <thead>
            <tr>
                <th scope="col">Manifestation</th>
                <th scope="col">Type</th>
                <th scope="col">Price</th>
                <th scope="col">Date</th>
                <th scope="col">ID</th>
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
                    <button type="button" class="btn btn-danger" v-on:click="cancel(ticket.uuid)">Cancel</button>
                </td>
            </tr>
        </tbody>
    </table>

</div>
    `,
    methods: {
        cancel: function (id) {
            let self = this;

            axios.put('tickets/cancel/' + id)
                .then(res => {

                    self.tickets = res.data;
                    for (t of this.tickets) {
                        t.manifestation.datetime = (new Date(t.manifestation.dateTime)).toLocaleString();
                    }
                })
                .catch(err => {
                    alert("Manifestation is over, can't cancel this ticket");
                })
        },
        search: function () {
            let self = this;
            let tmp = '?';
            Object.entries(this.sfs).forEach(([key, val]) => tmp = tmp + key + '=' + val + '&');
            axios.get('tickets/allClientTickets' + tmp)
                .then(res => {
                    self.tickets = res.data;
                    for (t of self.tickets) {
                        t.manifestation.datetime = (new Date(t.manifestation.dateTime)).toLocaleString();
                    }
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
    mounted() {
        let tmp = '?';
        Object.entries(this.sfs).forEach(([key, val]) => tmp = tmp + key + '=' + val + '&');
        axios.get('tickets/allClientTickets' + tmp)
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