import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class ProdutoPerecivel extends Produto {

    private LocalDate dataValidade;

    public ProdutoPerecivel(String desc, double precoCusto, double margemLucro, LocalDate dataValidade) {

        super(desc, precoCusto, margemLucro);

        if (dataValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de validade inválida.");
        }

        this.dataValidade = dataValidade;
    }

    public ProdutoPerecivel(String desc, double precoCusto, LocalDate dataValidade) {

        super(desc, precoCusto);

        if (dataValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de validade inválida.");
        }

        this.dataValidade = dataValidade;
    }

    @Override
    public double valorDeVenda() {

        if (LocalDate.now().isAfter(dataValidade)) {
            throw new IllegalStateException("Produto vencido. Não pode ser vendido.");
        }

        double preco = calcularPrecoBase();

        long dias = ChronoUnit.DAYS.between(LocalDate.now(), dataValidade);

        if (dias <= 7) {
            preco *= 0.75;
        }

        return preco;
    }

    @Override
    public String gerarDadosTexto() {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return String.format("2;%s;%.2f;%.2f;%s",
                descricao,
                precoCusto,
                margemLucro,
                dataValidade.format(formato));
    }
}