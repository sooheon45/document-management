<template>
    <div style="width: -webkit-fill-available; height: -webkit-fill-available;">
        <v-btn @click="$refs.fileInput.click()" style="width: -webkit-fill-available; height: -webkit-fill-available;">파일 업로드</v-btn>
        <input type="file" ref="fileInput" @change="uploadFile" multiple style="display: none;" /> 
    </div>
</template>

<script>
    const axios = require('axios').default;
    import { PDFDocument, StandardFonts, rgb } from 'pdf-lib'

    export default {
        name: 'DocumentUpload',
        props: {
            itemId: String,
        },
        data: () => ({
            loading: false,
        }),
        methods: {
            async uploadFile(event) {
                const files = event.target.files;
                if (files.length === 0) {
                    return;
                }

                const formData = new FormData();
                formData.append('itemId', this.itemId); // 다른 서비스의 고유 id
                for (let i = 0; i < files.length; i++) {
                    formData.append('files', files[i]);
                }

                try {
                    const response = await axios.post('/documents/uploadfile', formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    });
                    console.log('Files uploaded successfully:', response.data);
                } catch (error) {
                    console.error('Error uploading files:', error);
                }
            },
            async createDocument(){
                const response = await axios.post('/documents', {
                    userId: '1',
                    userName: '홍길동',
                    fileType: 'text',
                    name: '새 문서',
                    text: '새 문서 내용',
                    timeStamp:  new Date().toISOString().substr(0, 10),
                },
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                const documentUrl = response.data._links.self.href;
                const documentId = documentUrl.split('/').pop();
                this.$router.push({ path: `/file/${documentId}` });
            },
            async createPDF(){
                // Create a new PDFDocument 
                const pdfDoc = await PDFDocument.create()
                // Embed the Times Roman font
                const timesRomanFont = await pdfDoc.embedFont(StandardFonts.TimesRoman)

                // Add a blank page to the document
                const page = pdfDoc.addPage()

                // Get the width and height of the page
                const { height } = page.getSize()

                // Draw a string of text toward the top of the page
                const fontSize = 30
                page.drawText('Creating PDFs in JavaScript is awesome!', {
                    x: 50,
                    y: height - 4 * fontSize,
                    size: fontSize,
                    font: timesRomanFont,
                    color: rgb(0, 0.53, 0.71),
                })

                // Serialize the PDFDocument to bytes (a Uint8Array)
                const pdfBytes = await pdfDoc.save()

                // Convert the Uint8Array to a Blob
                const pdfBlob = new Blob([pdfBytes], { type: 'application/pdf' });

                const formData = new FormData();
                const metadata = {
                    userId: '1',
                    userName: '홍길동',
                    fileType: 'pdf',
                    name: '새 문서',
                    timeStamp: new Date().toISOString().substr(0, 10)
                };
                formData.append('metadata', JSON.stringify(metadata));
                formData.append('file', pdfBlob, 'document.pdf');

                // Send the PDF to the server
                try {
                    const response = await axios.post('/documents', formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    });
                    const documentUrl = response.data._links.self.href;
                    console.log(documentUrl)
                    // const documentId = documentUrl.split('/').pop();
                    // this.$router.push({ path: `/file/${documentId}` });
                } catch (error) {
                    console.error('Error uploading PDF:', error);
                }

            },

            
        },
    }
</script>

