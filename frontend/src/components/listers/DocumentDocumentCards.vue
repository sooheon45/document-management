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
            <div class="text-center">
                <div style="text-align: left; font-size: larger; font-weight: bold;">새 파일</div>
                <Document tag="upload" style="width: 150px; height: 50px;"></Document>
                

                <div style="text-align: left; font-size: larger; font-weight: bold; margin-top: 20px;">파일 리스트</div>
                <Document tag="list"></Document>
                <v-spacer></v-spacer>

                <div style="text-align: left; font-size: larger; font-weight: bold; margin-top: 20px;">파일 검색</div>
                <Document tag="search"></Document>
                <v-spacer></v-spacer>

                <div style="text-align: left; font-size: larger; font-weight: bold; margin-top: 20px;">파일 뷰어</div>
                <Document tag="viewer" :id="id"></Document>
            </div>
        </v-col>
        <!-- <v-row>
            <DocumentDocument :offline="offline" class="video-card" v-for="(value, index) in values" v-model="values[index]" v-bind:key="index" @delete="remove"/>
        </v-row> -->
    </div>
</template>

<script>

    const axios = require('axios').default;
    import Document from './../Document.vue';

    export default {
        name: 'DocumentDocumentManager',
        components: {
            Document,
        },
        props: {
            offline: Boolean
        },
        data: () => ({
            values: [],
        }),
        async created() {
            var me = this;
            var temp = await axios.get(axios.fixUrl('/documents'))
            me.values = temp.data._embedded.documents;
        },
        methods:{
            
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

