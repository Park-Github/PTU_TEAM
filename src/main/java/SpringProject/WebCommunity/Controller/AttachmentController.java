package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.FileStore;
import SpringProject.WebCommunity.Service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final FileStore fileStore;

    @GetMapping(value = {"/download/attachments/{id}", "/market/buy/attachments/{id}", "/market/sell/attachments/{id}"})
    public ResponseEntity<Resource> downloadAttachments(@PathVariable Long id) throws MalformedURLException {
        String serverFileName = attachmentService.findServerFileName(id);
        String clientFileName = attachmentService.findClientFileName(id);
        UrlResource resource = new UrlResource("file:" +
                fileStore.getFullUploadPath(serverFileName));
        log.info("clientFileName={}", clientFileName);
        String encodedUploadFileName = UriUtils.encode(clientFileName,
                StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" +
                encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
