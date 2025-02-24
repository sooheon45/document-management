package documentmanagement.infra;

import documentmanagement.domain.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;


// @RequestMapping(value="/documents")
@EnableAsync
@RestController
@Transactional
public class DocumentController {

    @Value("${file.upload}")
    private String uploadDir;

    @Value("${file.preview}")
    private String previewDir;

    @Autowired
    DocumentRepository documentRepository;


    @RequestMapping(
        value = "/documents/uploadfile",
        method = RequestMethod.POST,
        produces = "application/json"
    )
    public Document uploadFile(
        HttpServletRequest request,
        HttpServletResponse response,
        @ModelAttribute UploadFileCommand uploadFileCommand
    ) throws Exception {
        System.out.println("##### /document/uploadFile  called #####");
        Long itemId = uploadFileCommand.getItemId();
        MultipartFile[] files = uploadFileCommand.getFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException("파일이 선택되지 않았습니다.");
        }

        // 서버 저장 경로 설정
        File dir = new File(uploadDir);
        File previewDirFile = new File(previewDir);

        if (!dir.exists()) dir.mkdirs();
        if (!previewDirFile.exists()) previewDirFile.mkdirs();

        for (MultipartFile file : files) {
            
            String contentType = file.getContentType();
            String originalFilename = file.getOriginalFilename();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = Paths.get(originalFilename).toString(); // 한글 깨짐 방지.
            
            String tmpFileName = timestamp + "_" + originalFilename;
            String filePath = Paths.get(uploadDir, tmpFileName).toString();
            String previewPath = Paths.get(previewDir, "preview_" + tmpFileName + ".png").toString();

            // 허용된 파일 타입 검사
            if (!contentType.startsWith("image/") && 
                !contentType.equals("application/pdf") &&
                !contentType.startsWith("video/")) {
                return null;
            }

            try {
                // Document 객체 생성 및 저장
                Document document = new Document();
                document.setItemId(itemId);
                document.setName(fileName);
                document.setFileType(contentType);
                document.setFilePath(filePath);
                document.setPreviewPath(previewPath);
                document.setFileSize(file.getSize());
                document.setTimeStamp(new Date()); 
                
                 // 파일 저장.
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(filePath));
                documentRepository.save(document);
                document.uploadFile(file);
            } catch (Exception e) {
                throw new RuntimeException("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
            }
        }

        return null;    
    }

    @RequestMapping(
        value = "/documents/downloadfile",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Document downloadFile(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody DownloadFileCommand downloadFileCommand
    ) throws Exception {
        System.out.println("##### /document/downloadFile  called #####");
        Document document = new Document();
        document.downloadFile(downloadFileCommand);
        return document;
    }

    @RequestMapping(
        value = "/documents/{id}/deletefile",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public Document deleteFile(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /document/deleteFile  called #####");
        Optional<Document> optionalDocument = documentRepository.findById(id);
        optionalDocument.orElseThrow(() -> new Exception("No Entity Found"));
        Document document = optionalDocument.get();

        File fileToDelete = new File(document.getFilePath());
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
        File previewToDelete = new File(document.getPreviewPath());
        if (previewToDelete.exists()) {
            previewToDelete.delete();
        }

        DeleteFileCommand deleteFileCommand = new DeleteFileCommand();
        deleteFileCommand.setId(id);
        document.deleteFile(deleteFileCommand);

        documentRepository.deleteById(id);
        return document;
    }

    @GetMapping(path = "/documents/loadpreview")
    public ResponseEntity<byte[]>  loadPreview(LoadPreviewQuery loadPreviewQuery) throws IOException {
        System.out.println("##### /document/loadpreview  called #####");
        Document document = documentRepository.findById(loadPreviewQuery.getId()).orElseThrow(() -> new RuntimeException("문서를 찾을 수 없습니다"));
        String previewPath = document.getPreviewPath();

        // 파일 서버에서 파일 읽기
        File file = new File(previewPath);
        Path path = file.toPath();
        byte[] fileBytes = Files.readAllBytes(path);

        if (fileBytes == null || fileBytes.length == 0) {
            throw new RuntimeException("파일을 읽을 수 없습니다");
        }

        // Content-Type 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(document.getFileType()));
        
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @GetMapping(path = "/documents/loadfile")
    public ResponseEntity<byte[]> loadFile(LoadFilesQuery loadFilesQuery) throws Exception {
        System.out.println("##### /document/loadFile  called #####");
        Document document = documentRepository.findById(loadFilesQuery.getId()).orElseThrow(() -> new RuntimeException("문서를 찾을 수 없습니다"));
        String filePath = document.getFilePath();

        HttpHeaders headers = new HttpHeaders();
        File file = new File(filePath);
        Path path = file.toPath();
        byte[] fileBytes;

        fileBytes = Files.readAllBytes(path);
        if (fileBytes == null || fileBytes.length == 0) {
            throw new RuntimeException("파일을 읽을 수 없습니다");
        }
        headers.setContentType(MediaType.parseMediaType(document.getFileType()));        

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);            
    }

    @GetMapping(path = "/documents/searchtext")
    public List<Document> searchtext(SearchTextQuery searchTextQuery) {
        System.out.println("##### /document/searchText  called #####");
        return documentRepository.searchtext(searchTextQuery.getName());
    
    }

    
}
