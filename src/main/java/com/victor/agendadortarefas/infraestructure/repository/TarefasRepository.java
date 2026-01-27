package com.victor.agendadortarefas.infraestructure.repository;

import com.victor.agendadortarefas.infraestructure.entity.TarefasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity, String> {

}
