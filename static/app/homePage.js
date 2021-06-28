Vue.component('homepage', {
    data: function () {
        return {
            ms: {}
        }
    },
    template: `
    <div>
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