package documentmanagement.domain;

import documentmanagement.ThumbnailApplication;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.persistence.*;
import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jcodec.api.FrameGrab;
import org.jcodec.scale.AWTUtil;

@Entity
@Table(name = "Thumbnail_table")
@Data
public class Thumbnail {

    @Value("${file.preview}")
    private String previewDir;
    
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
    
        public static ThumbnailRepository repository() {
            ThumbnailRepository thumbnailRepository = ThumbnailApplication.applicationContext.getBean(
                ThumbnailRepository.class
            );
            return thumbnailRepository;
        }
    
        //<<< Clean Arch / Port Method
        public void processThumbnail(FileSaved fileSaved) {
            //implement business logic here:
             try {
                String fileType = fileSaved.getFileType();
                String filePath = fileSaved.getFilePath();
                String previewPath = fileSaved.getPreviewPath();
                BufferedImage previewImage = null;

                File file = new File(filePath);
                if (fileType.equals("application/pdf")) {
                    PDDocument pdf = PDDocument.load(file);
                    PDFRenderer pdfRenderer = new PDFRenderer(pdf);
        
                    previewImage = pdfRenderer.renderImageWithDPI(0, 300); // 첫 페이지를 300 DPI로 렌더링
                } else if (fileType.startsWith("image/")) {
                    previewImage = ImageIO.read(file);
                    // previewImage = ImageIO.read(file.getInputStream());
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
                    ThumbnailProcessed thumbnailProcessed = new ThumbnailProcessed(this);
                    thumbnailProcessed.setId(fileSaved.getId());
                    thumbnailProcessed.setStatus("SUCCESS");
                    thumbnailProcessed.setReason("미리보기 이미지가 생성되었습니다.");
                    thumbnailProcessed.publishAfterCommit();
                }
            } catch (Exception e) {
                // 예외 처리 로직 추가
                System.err.println("미리보기 이미지 생성 중 오류 발생: " + e.getMessage());
            }

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
