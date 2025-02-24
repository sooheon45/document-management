<template>
    <div style="height: 100%;">
        <v-card outlined>
            <v-btn text color="deep-purple lighten-2" large @click="back()">Back</v-btn>
            <v-card-title>
                {{ document.name }}
            </v-card-title>
            <v-card-actions>
                <v-btn
                        color="primary"
                        text
                        @click="download()"
                >
                    Download
                </v-btn>
                <v-btn
                        color="primary"
                        text
                        @click="remove"
                >
                    Delete
                </v-btn>
            </v-card-actions>
        </v-card>
        <div v-if="type && type.includes('image')" style="height: 100%;">
            <img :src="document.file" :alt="document.name" style="max-width: 100%; height: auto;" />
        </div>
        <div v-if="type && type.includes('pdf')" style="height: 100%; margin-left: 5px; margin-bottom: 5px;">
            <embed :src="document.file" type="application/pdf" width="100%" height="100%" />
        </div>
        <div v-if="type && type.includes('video')" style="height: 100%;">
            <video :src="document.file" controls width="100%" height="50%"></video>
        </div>
    </div> 
</template>


<script>
    const axios = require('axios').default;

    export default {
        name: 'DocumentViewer',
        components:{},
        props: {
            type: String, // pdf, text, image, video
            document: Object,
            itemId: String
        },
        data: () => ({
            updateKey: 0,
            document: null,
        }),
        methods: {
            download(){
                if (this.document && this.document.file) {
                    axios.get(this.document.file, { responseType: 'blob' })
                        .then(async response => {
                            const url = URL.createObjectURL(new Blob([response.data]));
                            const link = document.createElement('a');
                            link.href = url;
                            link.setAttribute('download', this.document.name || 'download');
                            document.body.appendChild(link);
                            link.click();
                            document.body.removeChild(link);
                            URL.revokeObjectURL(url);
                            await axios.post('/documents/downloadfile', {
                                id: this.document.documentId,
                                itemId: this.itemId
                            });
                        })
                        .catch(error => {
                            console.error('파일 다운로드 중 오류 발생:', error);
                        });
                }
            },
            async remove(){
                var me = this
                const response = await axios.delete(`/documents/${me.document.documentId}/deletefile`);
                if(response.data) {
                    me.back()
                } else {
                    alert("파일 삭제 실패");
                }
            },
            back(){
                this.$router.push({ path: '/files' });
            },
        },
    };
</script>
