<template>
    <div>
        <div style="display: flex; flex-direction: row; height: 500px;">
            <v-list v-for="(document, index) in documents" :key="index" style="width: 250px; height: 300px;">
                <v-card @click="goDocument(document)" style="width: 220px; height: 320px; margin: 5px;">
                    <v-card-text style="background-color: white; padding: 0px;">
                        <div style="height: 230px;align-content: center;">
                            <img :src="document.previewImage" style="max-width: 100%; max-height: 100%"/>
                        </div>
                        <div style="margin-left: 10px;">
                            <String label="이름" v-model="document.name" style="justify-items: left;"/>
                            <String label="유형" :value="formattedFileType(document.fileType)" style="justify-items: left;"/>
                            <String label="크기" :value="formattedFileSize(document.fileSize)" style="justify-items: left;" />
                            <Date label="마지막 수정" :value="formattedTimeStamp(document.timeStamp)" style="justify-items: left;"/>
                        </div>
                    </v-card-text>
                </v-card>
            </v-list>
        </div>
    </div>
</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'DocumentLists',
        data: () => ({
            loading: false,
            documents: [] // 문서 리스트
        }),
        created(){
            this.init()
        },
        methods: {
            async init(){
                this.loading = true
                let lists = []
                var temp = await axios.get(axios.fixUrl('/documents'));

                if(temp.data._embedded.documents){
                    temp.data._embedded.documents.forEach(async document => {
                        const href = document._links.self.href;
                        const id = href.split('/').pop();
                        document.id = id;

                        try {
                            const response = await axios.get(`/documents/loadpreview`, { 
                                params: {
                                    id: document.id
                                },
                                responseType: 'blob'
                            });
                            document.previewImage = URL.createObjectURL(response.data);
                        } catch (error){
                            console.log(error)
                        }
                        lists.push(document);
                    });
                }

                this.documents = lists; 
                this.loading = false
            },
            goDocument(value){
                this.$router.push({ path: `/file/${value.id}` });
            },
            formattedFileType(fileType) {
                return fileType;
            },
            formattedTimeStamp(timeStamp) {
                const date = new Date(timeStamp);
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0');
                const day = String(date.getDate()).padStart(2, '0');
                const hours = String(date.getHours()).padStart(2, '0');
                const minutes = String(date.getMinutes()).padStart(2, '0');
                return `${year}.${month}.${day} ${hours}:${minutes}`;
            },
            formattedFileSize(size) {
                if (size < 1024) return `${size} B`;
                let i = Math.floor(Math.log(size) / Math.log(1024));
                let sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
                return (size / Math.pow(1024, i)).toFixed(2) + ' ' + sizes[i];
            }
        },
    }
</script>

