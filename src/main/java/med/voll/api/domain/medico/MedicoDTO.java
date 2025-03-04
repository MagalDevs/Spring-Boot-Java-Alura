package med.voll.api.domain.medico;

public record MedicoDTO(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public MedicoDTO(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }


}
