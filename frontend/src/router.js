
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import DocumentDocumentManager from "./components/listers/DocumentDocumentCards"
import DocumentDocumentDetail from "./components/listers/DocumentDocumentDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/documents/documents',
                name: 'DocumentDocumentManager',
                component: DocumentDocumentManager
            },
            {
                path: '/documents/documents/:id',
                name: 'DocumentDocumentDetail',
                component: DocumentDocumentDetail
            },



    ]
})
