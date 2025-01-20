<template>
    <v-card outlined>
        <v-card-title>
            SaveFile
        </v-card-title>

        <v-card-text>
            <Number label="Id" v-model="value.id" :editMode="editMode"/>
            <String label="Name" v-model="value.name" :editMode="editMode"/>
            <String label="UserId" v-model="value.userId" :editMode="editMode"/>
            <String label="UserName" v-model="value.userName" :editMode="editMode"/>
            <String label="Text" v-model="value.text" :editMode="editMode"/>
            <Date label="TimeStamp" v-model="value.timeStamp" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="saveFile"
            >
                SaveFile
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
        name: 'SaveFileCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.id = 0;
            this.value.name = '';
            this.value.userId = '';
            this.value.userName = '';
            this.value.text = '';
            this.value.timeStamp = '2025-01-20';
        },
        watch: {
        },
        methods: {
            saveFile() {
                this.$emit('saveFile', this.value);
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

