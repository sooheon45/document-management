<template>
    <div style="width: -webkit-fill-available; height: 100%;">
        <div v-if="tag == 'upload'" style="width: 100%; height: 100%;">
            <DocumentUpload :itemId="itemId"></DocumentUpload>
        </div>
        <div v-if="tag == 'search'" style="width: 100%; height: 100%;">
            <DocumentSearch/>
        </div>
        <div v-if="tag == 'list'" style="width: 100%; height: 100%;">
            <DocumentLists/>
        </div>
        <div v-if="tag == 'viewer' && isDone" style="width: 100%; height: 100%;">
            <DocumentViewer :itemId="itemId" :type="type" :document="document"></DocumentViewer>
        </div>
    </div>
</template>

<script>
    const axios = require('axios').default;
    import DocumentUpload from './DocumentUpload.vue';
    import DocumentSearch from './DocumentSearch.vue';
    import DocumentLists from './DocumentLists.vue';
    import DocumentViewer from './DocumentViewer.vue';


    export default {
        name: 'Document',
        props: {
            tag: String, // 사용할 태그 create, search, list, edit
            id: String, // 문서 아이디
            itemId: String, // 다른 서비스의 고유 id
        },
        components: {
            DocumentUpload,
            DocumentSearch,
            DocumentLists,
            DocumentViewer
        },
        data: () => ({
            isDone: false,
            type: null, // pdf, text, image, video
            document: null, // id에 따른 문서 정보.
        }),
        created(){
            this.init()
        },
        methods: {
            async init(){
                var me = this
                if(!me.id) return; // only edit mode

                me.isDone = false
                const response = await axios.get(`/documents/${me.id}`)
                const file = await axios.get(`/documents/loadfile`,{
                    params: {
                        id: me.id,
                        itemId: me.itemId
                    },
                    responseType: 'blob'
                });
                
                me.document = response ? response.data : {}
                me.type = response.data.fileType
                me.document.documentId = me.id;
                me.document.file = URL.createObjectURL(file.data);

                me.isDone = true
            },
            
            
        },
    }
</script>

