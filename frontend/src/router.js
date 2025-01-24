
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import DocumentDocumentManager from "./components/listers/DocumentDocumentCards"
import ViewerComponent from "./components/ViewerComponent"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/files',
            name: 'DocumentDocumentManager',
            component: DocumentDocumentManager
        },
        {
            path: '/file/:id',
            name: 'ViewerComponent',
            component: ViewerComponent
        },



    ]
})
