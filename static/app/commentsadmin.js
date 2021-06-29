Vue.component('commentsadmin', {
    data: function () {
        return {
            comments: []
        }
    },
    template: `
    <div width="80%">
    <h3>
    My tickets
</h3>
    <table class="table table-striped table-dark">
        <thead>
            <tr>
                <th scope="col">Manifestation</th>
                <th scope="col">Client</th>
                <th scope="col">Text</th>
                <th scope="col">Rating</th>
                <th scope="col">Active</th>
                <th scope="col"></th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="comment in this.comments" :key="comment.uuid">
                <td> {{comment.manifestation.name}} </td>
                <td> {{comment.commenter.username}} </td>
                <td> {{comment.text}} </td>
                <td> {{comment.rating}} </td>
                <td> {{comment.active}} </td>
                <td>
                    <button type="button" class="btn btn-danger" v-on:click="Delete(comment.uuid)">Delete</button>
                </td>
            </tr>
        </tbody>
    </table>

</div>
    `,
    methods: {
        delete: function () {

        }
    },
    mounted() {
        axios.get('comments/allForAdmin')
            .then(res => {
                this.comments = res.data;
                this.comments.forEach(c => {
                    c.active = c.active ? 'YES' : 'NO';
                });
            })
            .catch(err => {
                console.error(err);
            })
    }
})