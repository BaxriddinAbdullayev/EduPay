package uz.alifservice.mapper.file;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.alifservice.domain.file.ResourceFile;
import uz.alifservice.dto.file.ResourceFileDto;
import uz.alifservice.mapper.BaseMapper;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourceFileMapper extends BaseMapper<ResourceFile, ResourceFileDto> {
}
