package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepos extends JpaRepository<Attachment, Long> {
}
