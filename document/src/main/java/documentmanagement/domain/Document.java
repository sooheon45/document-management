package documentmanagement.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import documentmanagement.DocumentApplication;
import documentmanagement.domain.FileDownloaded;
import documentmanagement.domain.FileFound;
import documentmanagement.domain.FileSaved;
import documentmanagement.domain.FilesFound;
import documentmanagement.domain.TextSearched;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Document_table")
@Data
//<<< DDD / Aggregate Root
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String userId;

    private String userName;

    private String text;

    private Date timeStamp;

    private String status;

    private String reason;

    @PostPersist
    public void onPostPersist() {
        FilesFound filesFound = new FilesFound(this);
        filesFound.publishAfterCommit();

        FileSaved fileSaved = new FileSaved(this);
        fileSaved.publishAfterCommit();

        TextSearched textSearched = new TextSearched(this);
        textSearched.publishAfterCommit();

        FileDownloaded fileDownloaded = new FileDownloaded(this);
        fileDownloaded.publishAfterCommit();

        FileFound fileFound = new FileFound(this);
        fileFound.publishAfterCommit();
    }

    public static DocumentRepository repository() {
        DocumentRepository documentRepository = DocumentApplication.applicationContext.getBean(
            DocumentRepository.class
        );
        return documentRepository;
    }

    //<<< Clean Arch / Port Method
    public void saveFile(SaveFileCommand saveFileCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void searchText(SearchTextCommand searchTextCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void downloadFile(DownloadFileCommand downloadFileCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void findFile(FindFileCommand findFileCommand) {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
