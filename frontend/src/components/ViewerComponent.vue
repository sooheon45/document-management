<template>
    <div v-if="id" style="height: 100%;">
        <Document tag="viewer" :id="id"></Document>
    </div>
</template>

<script>
    import Document from './Document.vue';

    export default {
        name: 'ViewerComponent',
        components: {
            Document
        },
        data: () => ({
            id: null,
        }),
        created(){
            var me = this
            if(me.id) me.id = null;
            me.id = me.$route.params.id
        },
    }
</script>

