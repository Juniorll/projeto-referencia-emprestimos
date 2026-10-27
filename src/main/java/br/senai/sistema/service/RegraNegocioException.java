package br.senai.sistema.service;

/**
 * Erro de REGRA DE NEGÓCIO: a operação pedida não é permitida pelas regras do sistema.
 * A mensagem é escrita para o usuário e aparece no aviso vermelho da tela.
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
