package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioCancelamento implements ValidadorCancelamentoDeConsulta{

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var dataAgora = LocalDateTime.now();
        var consulta = repository.getReferenceById(dados.idConsulta());

        var diferencaEmMinutos = Duration.between(dataAgora, consulta.getData()).toMinutes();

        if (diferencaEmMinutos < 1440) { // 24 horas em minutos
            throw new ValidacaoException("Cancelamento só é permitido com 24 horas de antecedência.");
        }
    }
}
