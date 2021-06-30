Vue.component('homepage', {
    data: function () {
        return {
            ms: {},
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
    <div>
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
    <input type="number" v-model="sfs.priceStart" style="width: 5%;" >
    <span class="input-group-text">To</span>
    <input type="number" v-model="sfs.priceEnd" style="width: 5%;" >
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
    <div class="container">
    <div class="row row-cols-1 row-cols-md-2 g-4">
    <div class="col" v-for="m in ms" :key="m.uuid">
        <div class="card mb-3" style="max-width: 700px;">
            <div class="row g-0">
                <div class="col-md-4">
                    <img :src="m.image" class="card-img-top imageitem"  >
                </div>
                <div class="col-md-8">
                    <div class="card-body text-end" style="padding: 10px">
                        <h5 class="card-title">{{m.name}}</h5>
                        <p class="card-text"> {{m.type}} </p>
                        <p class="card-text" v-if="m.location" > {{m.location.address}} , {{m.datetime.toLocaleString()}} </p>
                        <p class="card-text">Regular ticket price :  {{m.ticketPrice}} </p>
                        <p class="card-text" v-if="m.rating!=0"><small class="text-muted">{{m.rating}}</small></p>
                        <button type="button" class="btn btn-primary" v-on:click="showDetails(m.uuid)">Details</button>
                        </div>
                </div>
            </div>
        </div>
        </div>
        </div>
        </div>
    </div>
    `,
    methods: {
        showDetails: function (id) {
            let self = this;
            this.$router.push({name: 'Manifestation', params: {'id': id}});
        },
        search: function () {
            let self = this;
            let tmp = '?';
            Object.entries(this.sfs).forEach(([key, val]) => tmp = tmp + key + '=' + val + '&');
            axios.get('manifestations/all' + tmp)
                .then(res => {


                    self.ms = res.data;
                    self.ms.forEach(
                        t => {
                            if (t.rating === 0) t.rating = 'No rating yet';
                            t.datetime = new Date(t.dateTime);

                            t.image = 'data:image/png;base64,' + t.image;

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
        axios.get('manifestations/all')
            .then(res => {
                self.ms = res.data;
                for (item of self.ms) {
                    item.datetime = new Date(item.dateTime);

                    item.image = 'data:image/png;base64,' + item.image;

                }
                console.log(self.ms);
            })
            .catch(err => {
                console.error(err);
            })
    }

})