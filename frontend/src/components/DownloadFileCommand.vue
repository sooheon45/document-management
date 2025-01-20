<template>
    <v-card outlined>
        <v-card-title>
            DownloadFile
        </v-card-title>

        <v-card-text>
            <String label="FileId" v-model="value.fileId" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="downloadFile"
            >
                DownloadFile
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
        name: 'DownloadFileCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.fileId = '';
        },
        watch: {
        },
        methods: {
            downloadFile() {
                this.$emit('downloadFile', this.value);
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

