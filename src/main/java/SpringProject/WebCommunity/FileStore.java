package SpringProject.WebCommunity;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Domain.Attachment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${upload.dir}")
    private String uploadDir;

    public String getFullUploadPath(String fileName) {
        return uploadDir + fileName;
    }

    public List<Attachment> storeFiles(List<MultipartFile> multipartFiles, Article article) throws IOException {
        List<Attachment> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            storeFileResult.add(storeFile(multipartFile, article));
        }
        return storeFileResult;
    }

    public Attachment storeFile(MultipartFile multipartFile, Article article) throws IOException{
        if (multipartFile.isEmpty())
            return null;

        String clientFileName = multipartFile.getOriginalFilename();
        String serverFileName = createStoreFileName(clientFileName);
        multipartFile.transferTo(new File(getFullUploadPath(serverFileName)));

        return new Attachment(clientFileName, serverFileName, article);
    }

    private String createStoreFileName(String clientFileName) {
        String ext = extractExt(clientFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String clientFileName) {
        int pos = clientFileName.lastIndexOf(".");
        return clientFileName.substring(pos + 1);
    }
}
