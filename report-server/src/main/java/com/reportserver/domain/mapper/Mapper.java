package com.reportserver.domain.mapper;

import java.util.List;

/**
 * Interface with basic methods for Report-Entity mapping for data reporting.
 */
public interface Mapper<Entity, Report> {

    /**
     * Map Entity to Report and return.
     * 
     * @param entity is Entity
     * @return Report
     */
    Report toReport(Entity entity);
    
    /**
     * Map list of entities to list of Reports and return.
     * 
     * @param entities is list of Entities
     * @return list od Reports
     */
    List<Report> toReportList(List<Entity> entities);
    
}
