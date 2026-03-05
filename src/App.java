public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Produto {

    private static final double MARGEM_PADRAO = 0.2;

    protected String descricao;
    protected double precoCusto;
    protected double margemLucro;

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
        return precoCusto * (1.0 + margemLucro);
    }

    public abstract double valorDeVenda();

    public abstract String gerarDadosTexto();

    @Override
    public String toString() {

        NumberFormat moeda = NumberFormat.getCurrencyInstance();

        return String.format("NOME: %s: %s", descricao, moeda.format(valorDeVenda()));
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Produto)) {
            return false;
        }

        Produto outro = (Produto) obj;

        return this.descricao.toLowerCase().equals(outro.descricao.toLowerCase());
    }

    public static Produto criarDoTexto(String linha) {

        Produto novoProduto = null;

        try {

            String[] partes = linha.split(";");

            int tipo = Integer.parseInt(partes[0]);
            String desc = partes[1];
            double preco = Double.parseDouble(partes[2]);
            double margem = Double.parseDouble(partes[3]);

            if (tipo == 1) {

                novoProduto = new ProdutoNaoPerecivel(desc, preco, margem);

            } else if (tipo == 2) {

                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate validade = LocalDate.parse(partes[4], formato);

                novoProduto = new ProdutoPerecivel(desc, preco, margem, validade);
            }

        } catch (Exception e) {

            System.out.println("Erro ao criar produto a partir do texto.");
        }

        return novoProduto;
    }
}