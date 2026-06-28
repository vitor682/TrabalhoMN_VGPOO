package TrabalhoPOO;

public class Produto {

    private static int proximoCodigo = 1;

    private int codigo;
    private String nome;
    private double preco;
    private int quantEmEstoque;
    private boolean excluido = false;
    private Marca marca;

    private Produto() {}

    public static Produto criar(String nome, Marca marca, double preco, int quantEmEstoque) {
        Produto p = new Produto();
        p.codigo = proximoCodigo++;
        p.nome = nome;
        p.marca = marca;
        p.preco = preco;
        p.quantEmEstoque = quantEmEstoque;
        return p;
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantEmEstoque() { return quantEmEstoque; }
    public boolean isExcluido() { return excluido; }
    public Marca getMarca() { return marca; }

    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setQuantEmEstoque(int quantEmEstoque) { this.quantEmEstoque = quantEmEstoque; }
    public void setExcluido(boolean excluido) { this.excluido = excluido; }
    public void setMarca(Marca marca) { this.marca = marca; }
}