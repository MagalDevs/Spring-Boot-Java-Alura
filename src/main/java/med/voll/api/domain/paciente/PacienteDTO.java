package med.voll.api.domain.paciente;

public record PacienteDTO(Long id, String nome, String email, String cpf) {

    public PacienteDTO(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
