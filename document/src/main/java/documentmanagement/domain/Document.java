package documentmanagement.domain;

import documentmanagement.DocumentApplication;

import java.sql.SQLException;
import java.util.Date;
import javax.persistence.*;
import javax.sql.rowset.serial.SerialException;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Entity
@Table(name = "Document_table", indexes = @Index(columnList = "name"))
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String userName;
    private String fileType;
    private String name;

    private String previewPath;
    private String filePath;
    private Long fileSize;

    private Date timeStamp;
    private String status;
    private String reason;

   
    @PostPersist
    public void onPostPersist() {}

    public static DocumentRepository repository() {
        DocumentRepository documentRepository = DocumentApplication.applicationContext.getBean(
            DocumentRepository.class
        );
        return documentRepository;
    }

    public void saveFile(MultipartFile file) throws SerialException, SQLException {
        FileSaved fileSaved = new FileSaved(this);
        fileSaved.setName(file.getOriginalFilename());
        fileSaved.setFilePath(this.getFilePath());
        fileSaved.setTimeStamp(new Date());
        fileSaved.setStatus("SUCCESS");
        fileSaved.setReason("파일이 저장되었습니다.");
        fileSaved.publishAfterCommit();
    }


    public void searchText(Integer size) {
        TextSearched textSearched = new TextSearched(this);
        textSearched.setSize(size);
        textSearched.setStatus("SUCCESS");
        textSearched.setReason("문서가 검색되었습니다.");
        textSearched.publishAfterCommit();
    }

    public void downloadFile(DownloadFileCommand downloadFileCommand) {
        FileDownloaded fileDownloaded = new FileDownloaded(this);
        fileDownloaded.setId(downloadFileCommand.getId());
        fileDownloaded.setStatus("SUCCESS");
        fileDownloaded.setReason("파일이 다운로드되었습니다.");
        fileDownloaded.publishAfterCommit();
    }

    public void loadFile(Document document) {
        FileLoaded fileLoaded = new FileLoaded(this);
        fileLoaded.setId(document.getId());
        fileLoaded.setStatus("SUCCESS");
        fileLoaded.setReason("파일을 불러왔습니다.");
        fileLoaded.publishAfterCommit();
    }

    public void deleteFile(DeleteFileCommand deleteFileCommand) {
        FileDeleted fileDeleted = new FileDeleted(this);
        fileDeleted.setId(deleteFileCommand.getId());
        fileDeleted.setStatus("SUCCESS");
        fileDeleted.setReason("파일이 삭제되었습니다.");
        fileDeleted.publishAfterCommit();
    }

}
