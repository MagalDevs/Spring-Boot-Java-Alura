package med.voll.api.domain.medico;

public enum Especialidade {
    ORTOPEDIA("Ortopedia"),
    CARDIOLOGIA ("Cardiologia"),
    GINECOLOGIA ("Ginecologia"),
    DERMATOLOGIA ("Dermatologia"),
    UROLOGIA ("Urologia"),;

    private final String especialidade;

    Especialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}
