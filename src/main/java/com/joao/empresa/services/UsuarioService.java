package com.joao.empresa.services;

import com.joao.empresa.repository.ManutencaoRepository;
import com.joao.empresa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ManutencaoRepository manutencaoRepository;



}
