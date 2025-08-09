package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicosControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> jsonDadosCadastroMedico;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> jsonDadosDetalhamentoMedico;

    @MockitoBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Deveria cadastrar um medico")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
        var endereco = new DadosEndereco("rua xpto", "bairro", "00000000", "Brasilia", "DF", null, null);
        var dadosCadastro = new DadosCadastroMedico("João da Silva", "joao.silva@voll.med.com", "123456", "19993764839", Especialidade.ORTOPEDIA, endereco);

        when(repository.save(any()))
                .thenReturn(new Medico(dadosCadastro));

        var response = mvc.perform(post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        jsonDadosCadastroMedico.write(
                                dadosCadastro
                        ).getJson()
                )).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = jsonDadosDetalhamentoMedico.write(
                new DadosDetalhamentoMedico(null,
                        "João da Silva",
                        "joao.silva@voll.med.com",
                        "123456",
                        "19993764839",
                        Especialidade.ORTOPEDIA,
                        endereco)
                ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria devolver 400 quando não informar os dados de cadastro")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {
        var response = mvc.perform(post("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}