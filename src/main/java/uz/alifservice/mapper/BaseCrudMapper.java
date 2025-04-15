package uz.alifservice.mapper;

import org.mapstruct.*;
import uz.alifservice.domain.Auditable;
import uz.alifservice.dto.GenericCrudDto;
import uz.alifservice.dto.GenericDto;

import java.util.List;

/**
 * @param <E>  - Entity type parameter
 * @param <D>  - Dto
 * @param <CD> - CrudDTO
 */

public interface BaseCrudMapper<E extends Auditable<Long>, D extends GenericDto, CD extends GenericCrudDto> {

    D toDto(E entity);

    E fromDto(D dto);

    List<D> toDto(List<E> entityList);

    List<E> fromDto(List<D> dtoList);

    @Mapping(ignore = true, target = "deleted")
    E fromCreateDto(CD crudDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E fromUpdate(CD crudDto, @MappingTarget E order);

    @Named(value = "toDtoOnlyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    D onlyId(E entity);

    @Mapping(source = ".", target = "id")
    E onlyEntityId(Long id);
}
