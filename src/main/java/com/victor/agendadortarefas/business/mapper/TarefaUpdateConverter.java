package com.victor.agendadortarefas.business.mapper;

import com.victor.agendadortarefas.business.dto.TarefasDTO;
import com.victor.agendadortarefas.infraestructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {
    void updateTarefas(TarefasDTO tarefaDTO, @MappingTarget TarefasEntity tarefasEntity);
}
