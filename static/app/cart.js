Vue.component('cart', {
    data: function () {
        return {
            tickets: [],
            totalPrice: 0
        }
    },
    template: `
    <div width="80%">
    <h3>
    Total price of items in your cart is : {{totalPrice}}
    <button type="button" class="btn btn-primary" v-on:click="buyCart()">Buy</button>

</h3>
    <table class="table table-striped table-dark">
        <thead>
            <tr>
                <th scope="col">Manifestation</th>
                <th scope="col">Type</th>
                <th scope="col">Price</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="ticket in this.tickets" :key="ticket.uuid">
                <td> {{ticket.manifestation.name}} </td>
                <td> {{ticket.ticketType}} </td>
                <td> {{ticket.ticketPrice}} </td>
                <td>
                    <button type="button" class="btn btn-danger" v-on:click="removeFromCart(ticket.uuid)">Remove</button>
                </td>
            </tr>
        </tbody>
    </table>

</div>
    `,
    methods: {
        removeFromCart: function (id) {
            let self = this;
            axios.get('tickets/removeFromCart/' + id)
                .then(res => {
                    self.tickets = res.data;
                    axios.get('tickets/getCart')
                        .then(res => {
                            self.tickets = res.data;
                            axios.get('tickets/cartPrice')
                                .then(res => {
                                    self.totalPrice = res.data;
                                })
                        })
                        .catch(err => {
                            console.error(err);
                        })

                })
                .catch(err => {
                    console.error(err);
                });


        },
        buyCart: function () {
            let self = this;
            axios.get('tickets/buyCart')
                .then(res => {
                    alert("Tickets bought")
                    axios.get('tickets/getCart')
                        .then(res => {
                            self.tickets = res.data;
                            axios.get('tickets/cartPrice')
                                .then(res => {
                                    self.totalPrice = res.data;
                                })
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
        axios.get('tickets/getCart')
            .then(res => {
                this.tickets = res.data;
            })
            .catch(err => {
                console.error(err);
            })
        axios.get('tickets/cartPrice')
            .then(res => {
                this.totalPrice = res.data;
            })
            .catch(err => {
                console.error(err);
            })


    },
    watch: {}
})