package com.victor.agendadortarefas.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.victor.agendadortarefas.infraestructure.enums.StatusNotificacao;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("tarefa")
public class TarefasEntity {
    @Id
    private String id;
    private String nomeTarefa;
    private String descricaoTarefa;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEvento;
    private String emailUsuario;
    private LocalDateTime dataAlteracao;
    private StatusNotificacao statusNotificacao;
}
