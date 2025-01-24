package documentmanagement.service;


import documentmanagement.domain.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jcodec.api.FrameGrab;
import org.jcodec.scale.AWTUtil;

@Service
public class DocumentService {

    @Async
    public void saveFileAsync(MultipartFile file, String filePath) throws IOException {
        try {
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void generateAndSavePreviewImage(Document document, MultipartFile mpfile, String previewPath) {
        // 미리보기 이미지 생성 로직
        try {
            String fileType = mpfile.getContentType();
            String filePath = document.getFilePath();
            BufferedImage previewImage = null;
            File file = new File(filePath);
    
            if (fileType.equals("application/pdf")) {
                PDDocument pdf = PDDocument.load(file);
                PDFRenderer pdfRenderer = new PDFRenderer(pdf);
    
                previewImage = pdfRenderer.renderImageWithDPI(0, 300); // 첫 페이지를 300 DPI로 렌더링
            } else if (fileType.startsWith("image/")) {
                previewImage = ImageIO.read(file);
            } else if (fileType.startsWith("video/")) {
                previewImage = AWTUtil.toBufferedImage(FrameGrab.getFrameFromFile(file, 5));
            }
    
            if (previewImage != null) {
                File previewFile = new File(previewPath);
    
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(previewImage, "png", baos);
                byte[] imageBytes = baos.toByteArray();
                FileOutputStream fos = new FileOutputStream(previewFile);
                fos.write(imageBytes);
                fos.close();
            }
        } catch (Exception e) {
            // 예외 처리 로직 추가
            System.err.println("미리보기 이미지 생성 중 오류 발생: " + e.getMessage());
        }
    }
}

