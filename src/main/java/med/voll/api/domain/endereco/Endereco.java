package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String numero;
    private String complemento;

    public Endereco(@NotNull @Valid DadosEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
    }

    public void atualizarInformacoes(DadosEndereco endereco) {
        if (endereco.logradouro() != null){
            this.logradouro = endereco.logradouro();
        }

        if (endereco.bairro() != null){
            this.bairro = endereco.bairro();
        }

        if (endereco.cep() != null){
            this.cep = endereco.cep();
        }

        if (endereco.cidade() != null){
            this.cidade = endereco.cidade();
        }

        if (endereco.uf() != null){
            this.uf = endereco.uf();
        }

        if (endereco.numero() != null){
            this.numero = endereco.numero();
        }

        if (endereco.complemento() != null){
            this.complemento = endereco.complemento();
        }
    }
}
