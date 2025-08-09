package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository ConsultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Paciente não encontrado!");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Médico não encontrado!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedicos(dados);
        if (medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }
        var consulta = new Consulta(medico, paciente, dados.data());
        ConsultaRepository.save(consulta);
        var dtoConsulta = new DadosDetalhamentoConsulta(consulta);
        return dtoConsulta;
    }

    private Medico escolherMedicos(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatório, quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }


    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!ConsultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Consulta não encontrada!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = ConsultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivoCancelamento());
        ConsultaRepository.save(consulta);
    }
}
