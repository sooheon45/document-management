<template>
    <div>
        <v-card
            class="mx-auto"
            outlined
            color="primary"
            style="padding:10px 0px 10px 0px; margin-bottom:40px;"
        >
            <v-row>
                <v-list-item class="d-flex" style="background-color: white;">
                    <h1 class="align-self-center ml-3">Document</h1>
                    <div class="secondary-text-color" style="margin-left:30px;"></div>
                </v-list-item>
            </v-row>
        </v-card>
        <v-col style="margin-bottom:40px;">
            <v-card class="pa-4" v-if="itemId">
                <Document tag="upload" :itemId="itemId"></Document>
                
                <Document tag="list" :itemId="itemId"></Document>
                <v-spacer></v-spacer>

                <Document tag="search" :itemId="itemId"></Document>
                <v-spacer></v-spacer>

                <div style="text-align: left; font-size: larger; font-weight: bold; margin-top: 20px;">파일 뷰어</div>
                <Document tag="viewer" :itemId="itemId" :id="id"></Document>
            </v-card>
        </v-col>
        <!-- <v-row>
            <DocumentDocument :offline="offline" class="video-card" v-for="(value, index) in values" v-model="values[index]" v-bind:key="index" @delete="remove"/>
        </v-row> -->
    </div>
</template>

<script>
    import Document from './../Document.vue';

    export default {
        name: 'DocumentDocumentCards',
        components: {
            Document,
        },
        props: {
            offline: Boolean
        },
        data: () => ({
            values: [],
            itemId: null,
        }),
        created() {
            // this.itemId = '1'
        }
    };
</script>


<style>
    .video-card {
        width:300px; 
        margin-left:4.5%; 
        margin-top:50px; 
        margin-bottom:50px;
    }
</style>

