package documentmanagement.infra;

import documentmanagement.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/documents")
@Transactional
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;

    @RequestMapping(
        value = "/documents/savefile",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Document saveFile(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody SaveFileCommand saveFileCommand
    ) throws Exception {
        System.out.println("##### /document/saveFile  called #####");
        Document document = new Document();
        document.saveFile(saveFileCommand);
        documentRepository.save(document);
        return document;
    }

    @RequestMapping(
        value = "/documents/searchtext",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Document searchText(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody SearchTextCommand searchTextCommand
    ) throws Exception {
        System.out.println("##### /document/searchText  called #####");
        Document document = new Document();
        document.searchText(searchTextCommand);
        documentRepository.save(document);
        return document;
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
        documentRepository.save(document);
        return document;
    }

    @RequestMapping(
        value = "/documents/findfile",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Document findFile(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody FindFileCommand findFileCommand
    ) throws Exception {
        System.out.println("##### /document/findFile  called #####");
        Document document = new Document();
        document.findFile(findFileCommand);
        documentRepository.save(document);
        return document;
    }
}
//>>> Clean Arch / Inbound Adaptor
