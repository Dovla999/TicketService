Vue.component('sellercomments', {
    data: function () {
        return {
            comments: []
        }
    },
    template: `
    <div width="80%">
    <h3 style= "text-align: center;">
    Comments 
</h3>
    <table class="table table-striped table-dark">
        <thead>
            <tr>
                <th scope="col">Manifestation</th>
                <th scope="col">Client</th>
                <th scope="col">Text</th>
                <th scope="col">Rating</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="comment in this.comments" :key="comment.uuid">
                <td> {{comment.manifestation.name}} </td>
                <td> {{comment.commenter.username}} </td>
                <td> {{comment.text}} </td>
                <td> {{comment.rating}} </td>
                <td v-if="comment.active===false">
                    <button type="button" class="btn btn-success" v-on:click="activate(comment.uuid)">Activate</button>
                </td>
                <td v-if="comment.active===true">
                <button type="button" class="btn btn-danger" v-on:click="activate(comment.uuid)">Deactivate</button>
                </td>
            </tr>
        </tbody>
    </table>

</div>
    `,
    methods: {
        activate: function (id) {
            let self = this;
            axios.put('comments/activate/' + id)
                .then(res => {
                    self.comments = res.data;
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
    mounted() {
        axios.get('comments/allForSeller')
            .then(res => {
                this.comments = res.data;
            })
            .catch(err => {
                console.error(err);
            })
    }
})