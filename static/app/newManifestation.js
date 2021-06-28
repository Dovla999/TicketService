Vue.component("newmanifestation", {
    data: function () {
        return {
            manif: {
                image: '',
                latitude: 0.0,
                longitude: 0.0

            },
            image: '',
            mapPosition: {latitude: 45.267136, longitude: 19.833549}
        }
    },
    template: ` 
    <div>
    <div class="container">
        <div class="mx-auto" style="width: 500px;">
            <h1>NEW MANIFESTATION</h1>

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
            <div class="col"> <input type="number" id="price" v-model="manif.price"> </div>

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
<input type="text" id="location" placeholder="Location" v-model="manif.location"> </div>

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
        <input type="button" class="btn btn-primary" value="Add Manifestation" v-on:click="addManif()" />
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
        addManif: function () {
            axios.post('manifestations/newManifestation', JSON.stringify(this.manif))
                .then(res => {
                    alert(response.data);
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
                "q": self.manif.location,
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

        axios.get('users/currentUser')
            .then(res => {
                console.log(res);
            })
            .catch(err => {
                console.error(err);
            })
    }
});