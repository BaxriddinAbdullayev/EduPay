package uz.alifservice.mapper;


import uz.alifservice.dto.GenericDto;

import java.util.List;

/**
 * @param <E> - Entity type parameter
 * @param <D> - Dto
 */


public interface BaseMapper<E, D extends GenericDto> {

    D toDto(E entity);

    E fromDto(D dto);

    List<D> toDto(List<E> entityList);

    List<E> fromDto(List<D> dtoList);
}
