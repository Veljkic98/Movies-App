package com.authserver.domain.mapper;

import java.util.List;

public interface Mapper<Entity, ResponseDTO, RequestDTO> {

    /**
     * Map DTO to Entity and return.
     * 
     * @param dto is request Data Transfer Object
     * @return Entity
     */
    Entity toEntity(RequestDTO dto);

    /**
     * Map Entity to DTO and return.
     * 
     * @param entity is Entity
     * @return DTO
     */
    ResponseDTO toDto(Entity entity);

    /**
     * Map list of entities to list of respnose DTOs and return.
     * 
     * @param entities is list of entities
     * @return list of DTOs
     */
    List<ResponseDTO> toDtoList(List<Entity> entities);
    
}
