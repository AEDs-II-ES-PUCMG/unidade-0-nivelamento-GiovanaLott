import java.text.NumberFormat;

public abstract class Produto {

    private static final double MARGEM_PADRAO = 0.2;
    private String descricao;
    private double precoCusto;
    private double margemLucro;

    private void init(String desc, double precoCusto, double margemLucro) {

        if ((desc.length() >= 3) && (precoCusto > 0.0) && (margemLucro > 0.0)) {
            descricao = desc;
            this.precoCusto = precoCusto;
            this.margemLucro = margemLucro;
        } else {
            throw new IllegalArgumentException("Valores inválidos para os dados do produto.");
        }
    }

    public Produto(String desc, double precoCusto, double margemLucro) {
        init(desc, precoCusto, margemLucro);
    }

    public Produto(String desc, double precoCusto) {
        init(desc, precoCusto, MARGEM_PADRAO);
    }

    protected double calcularPrecoBase() {
        return (precoCusto * (1.0 + margemLucro));
    }

    public abstract double valorDeVenda();

    @Override
    public String toString() {

        NumberFormat moeda = NumberFormat.getCurrencyInstance();

        return String.format("NOME: " + descricao + ": " + moeda.format(valorDeVenda()));
    }
}