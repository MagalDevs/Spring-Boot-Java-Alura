package med.voll.api.medico;

import java.util.Optional;

public record MedicoDTO(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public MedicoDTO(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }


}
