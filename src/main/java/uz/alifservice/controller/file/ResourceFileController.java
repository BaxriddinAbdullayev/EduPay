package uz.alifservice.controller.file;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.alifservice.controller.GenericController;
import uz.alifservice.criteria.file.ResourceFileCriteria;
import uz.alifservice.domain.file.ResourceFile;
import uz.alifservice.dto.AppResponse;
import uz.alifservice.dto.file.ResourceFileDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.file.ResourceFileMapper;
import uz.alifservice.service.file.ResourceFileService;
import uz.alifservice.service.message.ResourceBundleService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "${app.api.base-path}", produces = "application/json")
public class ResourceFileController implements GenericController<ResourceFileDto, ResourceFileCriteria> {

    private final ResourceFileService service;
    private final ResourceFileMapper mapper;
    private final ResourceBundleService bundleService;

    @Override
    @RequestMapping(value = "/file/resource-file/{id}", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<ResourceFileDto>> get(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = ResourceFile.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(mapper.toDto(service.get(id, lang)), messsage));
    }

    @Override
    @RequestMapping(value = "/file/resource-file", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<Page<ResourceFileDto>>> list(
            ResourceFileCriteria criteria,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = ResourceFile.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(service.list(criteria, lang).map(mapper::toDto), messsage));
    }


}
