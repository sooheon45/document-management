<template>
    <div>
        <v-divider class="my-2"></v-divider>
        <div style="text-align: left; font-size: larger; font-weight: bold;">파일 검색</div>
        <v-text-field
            :loading="loading"
            v-model="searchText"
            append-inner-icon="mdi-magnify"
            label="파일 이름"
            dense
            hide-details
            outlined
            single-line
            @click:append-inner="search()"
            @keyup.enter="search()"
        ></v-text-field>
        <div v-if="searchValues.length > 0">
            <div style="display: flex; flex-direction: row;">
                <v-list  v-for="(document, index) in searchValues" :key="index">
                    <v-card @click="goDocument(document)"
                        outlined
                    >
                        <v-card-text>
                            <div style="height: 230px;align-content: center;">
                                <img :src="document.previewImage" style="max-width: 100%; max-height: 100%; display: block; margin: auto;"/>
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
        <div v-else>
            <div v-if="loading">
                검색 중입니다...
            </div>
            <div v-else>
                검색 결과 없습니다.
            </div>
        </div>
    </div>
</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'DocumentSearch',
        data: () => ({
            loading: false,
            searchValues: [],
            searchText: ''
        }),
        methods: {
            async search(){
                this.loading = true
                let documents = []
                const response = await axios.get('/documents/searchtext', {
                    params: {
                        name: this.searchText
                    },
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                });
                if(response.data) {
                    response.data.forEach(async document => {
                        try {
                            const response = await axios.get(`/documents/loadpreview`, { 
                                params: {
                                    id: document.id
                                },
                                responseType: 'blob'
                            });
                            document.previewImage = URL.createObjectURL(response.data);
                            documents.push(document);
                        } catch (error){
                            console.log(error)
                        }
                    })
                } 
                this.searchValues = documents;
                this.loading = false
            },
            goDocument(value){
                this.$router.push({ path: `/file/${value.id}` });
            },
            formattedFileType(fileType) {
                if(fileType.startsWith("application/vnd.openxmlformats-officedocument")) return "docx"
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

