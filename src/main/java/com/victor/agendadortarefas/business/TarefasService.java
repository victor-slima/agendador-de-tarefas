package com.victor.agendadortarefas.business;

import com.victor.agendadortarefas.business.dto.TarefasDTO;
import com.victor.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.victor.agendadortarefas.business.mapper.TarefasConverter;
import com.victor.agendadortarefas.infraestructure.entity.TarefasEntity;
import com.victor.agendadortarefas.infraestructure.enums.StatusNotificacao;
import com.victor.agendadortarefas.infraestructure.exceptions.ResourceNotFoundException;
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
    private final TarefaUpdateConverter tarefasUpdateConverter;

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

    public void deletaTarefaPorId(String id) {
        try {
            tarefaRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por ID" + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacao status, String id) {
        try {
            TarefasEntity entity = tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa nao encontrada."));
            entity.setStatusNotificacao(status);
            return tarefasConverter.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar tarefa por id" + id, e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO tarefaDTO, String id) {
        try {
            TarefasEntity entity = tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa nao encontrada."));
            tarefasUpdateConverter.updateTarefas(tarefaDTO, entity);
            return tarefasConverter.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Tarefa nao encontrada.");
        }
    }

}
