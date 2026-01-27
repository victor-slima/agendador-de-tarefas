package com.victor.agendadortarefas.business;

import com.victor.agendadortarefas.business.dto.TarefasDTO;
import com.victor.agendadortarefas.business.mapper.TarefasConverter;
import com.victor.agendadortarefas.infraestructure.entity.TarefasEntity;
import com.victor.agendadortarefas.infraestructure.enums.StatusNotificacao;
import com.victor.agendadortarefas.infraestructure.repository.TarefasRepository;
import com.victor.agendadortarefas.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefaRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO tarefaDTO) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setStatusNotificacao(StatusNotificacao.PENDENTE);
        tarefaDTO.setEmailUsuario(email);
        TarefasEntity entity = tarefasConverter.paraTarefaEntity(tarefaDTO);

        return tarefasConverter.paraTarefaDTO(tarefaRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(
            LocalDateTime dataInicial,
            LocalDateTime dataFinal) {
        return tarefasConverter.paraTarefaDTO(tarefaRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefaRepository.findByEmailUsuario(email);
        return tarefasConverter.paraTarefaDTO(listaTarefas);
    }

}
