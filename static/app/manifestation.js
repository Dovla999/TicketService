Vue.component('manifestation', {
    data: function () {
        return {
            manif: {
                location: {},
                image: '',
                datetime: new Date(),
            },
            tickets: {
                count: 0
            },
            mapPosition: {latitude: 45.267136, longitude: 19.833549}

        }
    },
    template: `
    <div>
    <div class="container">
    
    <div class="row">
        <div class="col map" id="map" >
        </div>
    <div class="col">
        <img :src="manif.image" alt="Image" width="600"  height="300"> 
    </div>
    </div>
        <div class="row">
            <div class="col">
                <h3>Title:</h3>
            </div>
            <div class="col"> <h3>{{manif.name}} </h3> </div>

            <div class="col">
                <h3>Type:</h3>
            </div>
            <div class="col"> <h3>{{manif.type}} </h3>
        </div>
        </div>
        <div class="row">
            <div class="col">
                <h3>Regular ticket price:</h3>
            </div>
            <div class="col"> <h3>{{manif.ticketPrice}} </h3> </div>

            <div class="col">
                <h3>Capacity:</h3>
            </div>
            <div class="col"> <h3>{{manif.capacity}} </h3></div>
        </div>
        <div class="row">
            <div class="col">
                <h3>When and where: {{manif.datetime.toLocaleString()}} {{manif.location.address}} </h3>
                </div>
                <div class="col"> 
                <p style="font-size: x-large;">Tickets:                  
                <select id="tickets">
                    <option value="REGULAR">REGULAR</option>
                    <option value="VIP">VIP</option>
                    <option value="FAN_PIT">FAN PIT</option>
                  </select>

                  <input type="number" width="20px !important" id="quantity"  v-model="tickets.count">
                  <button style="margin-top: 10;" type="button" class="btn btn-primary">Add to cart</button>
                  </p>
                  </div>
            </div>

        </div>
 
    </div>

    `,
    methods: {
        showOnMap: function () {
            let self = this;
            self.mapPosition.latitude = parseFloat(this.manif.location.latitude);
            self.mapPosition.longitude = parseFloat(this.manif.location.longitude);
            self.manif.latitude = self.mapPosition.latitude;
            self.manif.longitude = self.mapPosition.longitude;

            console.log(self.mapPosition.latitude);
            console.log(self.mapPosition.longitude);
            let map = new ol.Map({
                target: 'map',
                interactions: [],
                controls: [],
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM()
                    })
                ],
                view: new ol.View({
                    center: ol.proj.fromLonLat([self.mapPosition.longitude, self.mapPosition.latitude]),
                    zoom: 15
                })
            });
        }

    },
    mounted() {
        console.log("mounted");

        axios.get('manifestations/' + this.$route.params.id)
            .then(res => {
                this.manif = res.data;
                this.manif.datetime = new Date(this.manif.dateTime);
                console.log(this.manif);
                this.manif.image = 'data:image/png;base64,' + this.manif.image;
                this.showOnMap();
            })
            .catch(err => {
                console.error(err);
            });


    }

})