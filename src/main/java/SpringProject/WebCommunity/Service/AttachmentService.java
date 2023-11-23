package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.FileStore;
import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Domain.Attachment;
import SpringProject.WebCommunity.Repository.ArticleRepos;
import SpringProject.WebCommunity.Repository.AttachmentQueryRepos;
import SpringProject.WebCommunity.Repository.AttachmentRepos;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final ArticleRepos articleRepos;
    private final AttachmentRepos attachmentRepos;
    private final FileStore fileStore;
    private final AttachmentQueryRepos attachmentQueryRepos;

    @Transactional
    public List<Long> save(Long articleId, List<MultipartFile> multipartFiles) throws IOException {
        Article article = articleRepos.findById(articleId).orElse(null);
        List<Attachment> attachments = fileStore.storeFiles(multipartFiles, article);
        List<Long> idList = new ArrayList<>();
        for (Attachment attach : attachments) {
            idList.add(attachmentRepos.save(attach).getId());
        }
        return idList;
    }

    public List<Tuple> findAttachments(Long articleId) {
        JPAQuery<Tuple> attachments = attachmentQueryRepos.findClientFileName(articleId);
        return attachments.stream().toList();
    }

    public Map<Long, String> mappingFileName(List<Long> idList) {
        Map<Long, String> fileMap = new HashMap<>();
        for (Long id : idList) {
            fileMap.put(id, findClientFileName(id));
        }
        return fileMap;
    }

    public String findClientFileName(Long id) {
        Attachment attachment = attachmentRepos.findById(id).orElseThrow();
        return attachment.getClientFileName();
    }

    public String findServerFileName(Long id) {
        Attachment attachment = attachmentRepos.findById(id).orElseThrow();
        return attachment.getServerFileName();
    }
}
