package com.joao.empresa.services;

import com.joao.empresa.exceptions.EmpresaJaCadastradaException;
import com.joao.empresa.exceptions.EmpresaNaoEncontradaException;
import com.joao.empresa.exceptions.EntidadeEmUsoException;
import com.joao.empresa.model.Empresa;
import com.joao.empresa.repository.EmpresaRepository;
import com.joao.empresa.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // só ler, não é pra alterar nada no banco
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EquipamentoRepository equipamentoRepository;

    public Empresa buscarPorId(Integer id) {

        validarId(id);

        return empresaRepository.findById(id)
                .orElseThrow(
                        () -> new EmpresaNaoEncontradaException(
                                "Empresa com ID " + id + " não encontrada."
                        )
                );
    }

    public List<Empresa> listar() {
        return empresaRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id") // ordenadas pelo id do menor para o maior
        );
    }

    // a controller não vai criar a empresa e mandar pra cá
    // aqui vai receber os dados e reconstruir o objeto aqui
    @Transactional
    public Empresa cadastrar(
            String nome,
            String cnpj,
            String endereco,
            String segmento,
            Empresa.Status status
    ){

        if(empresaRepository.existsByCnpj(cnpj){
            throw new EmpresaJaCadastradaException(
                    "Já existe uma empresa cadastrada com o CNPJ "
                            + cnpj + "."
            );
        }

        Empresa empresa = new Empresa(
                nome,
                cnpj,
                endereco,
                segmento,
                status
        );

        return empresaRepository.save(empresa);

    }

    @Transactional
    public Empresa atualizar(
            Integer id,
            String nome,
            String cnpj,
            String endereco,
            String segmento,
            Empresa.Status status
    ){

        Empresa empresa = buscarPorId(id);

        // Empresa diferente da empresa do id
        if (empresaRepository.existsByCnpjAndIdNot(cnpj, id)) {
            throw new EmpresaJaCadastradaException(
                    "Já existe outra empresa cadastrada com o CNPJ "
                            + cnpj + "."
            );
        }

        empresa.atualizarDados(
                nome,
                cnpj,
                endereco,
                segmento,
                status
        );

        return empresa;
    }

    @Transactional
    public void excluir(Integer id) {

        validarId(id);

        Empresa empresa = buscarPorId(id);

        if (equipamentoRepository.existsByEmpresaId(id)) {
            throw new EntidadeEmUsoException(
                    "Não é possível excluir a empresa de ID "
                            + id
                            + " porque ela possui equipamentos cadastrados."
            );
        }

        empresaRepository.delete(empresa);
    }

    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da empresa deve ser um número positivo."
            );
        }
    }

}




