package uz.alifservice.domain.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import uz.alifservice.domain.Auditable;

@Getter
@Setter
@Entity
@Table(name = "resource_files")
public class ResourceFile extends Auditable<Long> {

    @Column(name = "path")
    private String path;

    @Column(name = "extension")
    private String extension;

    @Column(name = "origen_name")
    private String origenName;

    @Column(name = "size")
    private Long size;

    @Column(name = "resource_hash", unique = true)
    private String resourceHash;

    @Column(name = "visible")
    private Boolean visible = true;
}
