Vue.component("editmanif", {
    data: function () {
        return {
            manif: {
                image: '',
                latitude: 0.0,
                longitude: 0.0,
                date: '',
                time: '',
                location: {
                    address: ''
                }


            },
            image: '',
            mapPosition: {latitude: 45.267136, longitude: 19.833549}
        }
    },
    template: ` 
    <div>
    <div class="container">
        <div class="mx-auto" style="width: 500px;">
            <h1>EDIT MANIFESTATION</h1>

        </div>
        <div class="row">
            <div class="col">
                <h3>Title:</h3>
            </div>
            <div class="col"> <input type="text" id="name" v-model="manif.name"> </div>

            <div class="col">
                <h3>Type:</h3>
            </div>
            <div class="col"> <input v-model="manif.type" id="type" type="text"> </div>
        </div>
        <div class="row">
            <div class="col">
                <h3>Regular ticket price:</h3>
            </div>
            <div class="col"> <input type="number" id="price" v-model="manif.ticketPrice"> </div>

            <div class="col">
                <h3>Capacity:</h3>
            </div>
            <div class="col"> <input type="number" id="capacity" v-model="manif.capacity"> </div>
        </div>
        <div class="row">
            <div class="col">
                <h3>Date:</h3>
            </div>
            <div class="col"> <input type="date" id="date" v-model="manif.date"> <input type="time"  id="time" v-model="manif.time"> </div>
            <div class="col">
<input type="text" id="location" placeholder="Location" v-model="manif.location.address"> </div>

            <div class="col">
            <input type="button" class="btn btn-primary" value="Preview on map" v-on:click="showOnMap()" />

            </div>
        
            </div>

        <div class="row">
        <div class="col">
        <div id="map" class="map"></div>
        </div>
        <div class="col">
        <img :src="image" alt="Image" width="600"  height="300"> 
        </div>
        </div>
        <div class="row" style="padding: 40px;">
        <div class="col"></div>
        <div class="col"></div>
        <div class="col"></div>
        <div class="col"></div>
        <div class="col"></div>
        <div class="col"></div>
        <div class="col">
        
        <input type="file" v-on:change="showImage()"   id="myfile">
      </div>
      <div class="col">
        <input type="button" class="btn btn-primary" value="Update Manifestation" v-on:click="updateManif()" />
        </div>
        <div class="col"></div>
    </div>
    </div>

</div>
	  
`,


    methods: {
        init: function () {

        },
        showImage: function () {

            let self = this;

            let file = document.querySelector('#myfile').files[0];
            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function () {
                self.manif.image = reader.result.split(',')[1];
                self.image = reader.result;
            };
            reader.onerror = function (error) {
                console.log('Error: ', error);
            };
        },
        updateManif: function () {
            let self = this;
            this.manif.location = this.manif.location.address;
            axios.put('manifestations/updateManifestation', JSON.stringify(this.manif))
                .then(res => {
                    axios.get('manifestations/' + self.$route.params.id)
                        .then(res => {
                            self.manif = res.data;
                            self.manif.datetime = new Date(self.manif.dateTime);
                            console.log(this.manif);
                            self.manif.image = 'data:image/png;base64,' + self.manif.image;
                            let tmpDate = new Date(this.manif.dateTime);
                            tmpDate.setTime(tmpDate.getTime() + (2 * 60 * 60 * 1000));
                            self.manif.date = (tmpDate).toISOString().split('T')[0];
                            self.manif.time = ((tmpDate).toISOString().split('T')[1]).substr(0, 5);
                            self.showOnMap();
                        })
                        .catch(err => {
                            console.error(err);
                        });
                })
                .catch(err => {
                    alert(error.response.data);
                })
        },
        showOnMap: function () {
            let self = this;
            let data = {
                "format": "json",
                "addressdetails": 1,
                "q": self.manif.location.address,
                "limit": 1
            }
            $.ajax({
                method: "GET",
                url: "https://nominatim.openstreetmap.org",
                data: data
            })
                .done(res => {
                    $('#map').empty();
                    self.mapPosition.latitude = parseFloat(res[0].lat);
                    self.mapPosition.longitude = parseFloat(res[0].lon);
                    self.manif.latitude = self.mapPosition.latitude;
                    self.manif.longitude = self.mapPosition.longitude;
                    console.log(res);

                    console.log(self.mapPosition.latitude);
                    console.log(self.mapPosition.longitude);
                    var map = new ol.Map({
                        target: 'map',
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
                })

        }

    },
    mounted() {
        var map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                center: ol.proj.fromLonLat([this.mapPosition.longitude, this.mapPosition.latitude]),
                zoom: 10
            })
        });

        let self = this;
        axios.get('manifestations/' + this.$route.params.id)
            .then(res => {
                this.manif = res.data;
                this.manif.datetime = new Date(this.manif.dateTime);
                console.log(this.manif);
                this.manif.image = 'data:image/png;base64,' + this.manif.image;
                this.image = this.manif.image;
                let tmpDate = new Date(this.manif.dateTime);
                tmpDate.setTime(tmpDate.getTime() + (2 * 60 * 60 * 1000));
                this.manif.date = (tmpDate).toISOString().split('T')[0];
                this.manif.time = ((tmpDate).toISOString().split('T')[1]).substr(0, 5);
                this.showOnMap();
            })
            .catch(err => {
                console.error(err);
            });
    }
});