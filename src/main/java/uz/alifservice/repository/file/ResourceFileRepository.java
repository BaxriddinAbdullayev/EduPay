package uz.alifservice.repository.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.alifservice.domain.file.ResourceFile;

import java.util.Optional;

@Repository
public interface ResourceFileRepository extends JpaRepository<ResourceFile, Long>, JpaSpecificationExecutor<ResourceFile> {

    Optional<ResourceFile> findByResourceHashAndDeletedFalse(String resourceHash);

    void deleteByResourceHashAndDeletedFalse(String resourceHash);
}
