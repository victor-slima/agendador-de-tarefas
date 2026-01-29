package com.victor.agendadortarefas.controller;

import com.victor.agendadortarefas.business.TarefasService;
import com.victor.agendadortarefas.business.dto.TarefasDTO;
import com.victor.agendadortarefas.infraestructure.entity.TarefasEntity;
import com.victor.agendadortarefas.infraestructure.enums.StatusNotificacao;
import com.victor.agendadortarefas.infraestructure.repository.TarefasRepository;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;
    private final TarefasRepository tarefasRepository;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefa(
            @RequestBody TarefasDTO tarefaDTO,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, tarefaDTO));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaTarefasPorEmail(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id){
        tarefasService.deletaTarefaPorId(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TarefasDTO> alterarStatusNotificacao(@RequestParam("status")StatusNotificacao status,
                                                               @RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.alteraStatus(status, id));
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> updateTarefas(@RequestBody TarefasDTO tarefaDTO,
                                                    @RequestParam("id") String id){
        return ResponseEntity.ok(tarefasService.updateTarefas(tarefaDTO, id));
    }
}
