package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Boolean existsByMedicoIdAndData(long medicoId, LocalDateTime data);

    Boolean existsByPacienteIdAndDataBetween(long pacienteId, LocalDateTime dataAfter, LocalDateTime dataBefore);
}
