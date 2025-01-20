<template>
    <v-card outlined>
        <v-card-title>
            SearchText
        </v-card-title>

        <v-card-text>
            <String label="Text" v-model="value.text" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="searchText"
            >
                SearchText
            </v-btn>
            
            <v-btn
                    color="primary"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'SearchTextCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.text = '';
        },
        watch: {
        },
        methods: {
            searchText() {
                this.$emit('searchText', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

