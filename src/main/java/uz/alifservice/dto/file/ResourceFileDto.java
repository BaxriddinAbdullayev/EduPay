package uz.alifservice.dto.file;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericDto;

@Getter
@Setter
public class ResourceFileDto extends GenericDto {

    private String path;
    private String extension;
    private String origenName;
    private Long size;
    private String resourceHash;
    private String url;
}
