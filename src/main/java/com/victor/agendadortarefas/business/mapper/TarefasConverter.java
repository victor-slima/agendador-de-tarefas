package com.victor.agendadortarefas.business.mapper;

import com.victor.agendadortarefas.business.dto.TarefasDTO;
import com.victor.agendadortarefas.infraestructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter
{
    TarefasEntity paraTarefaEntity(TarefasDTO tarefaDTO);

    TarefasDTO paraTarefaDTO(TarefasEntity tarefaEntity);

    List<TarefasEntity> paraTarefaEntity(List<TarefasDTO> tarefasDTO);

    List<TarefasDTO> paraTarefaDTO(List<TarefasEntity> tarefasEntity);
}
