package TrabalhoPOO;

public class Item {

    private Produto produto;
    private double preco;
    private int quantidade;

    private Item() {}

    public static Item criar(Produto produto, int quantidade) {
        Item item = new Item();
        item.produto = produto;
        item.quantidade = quantidade;
        item.preco = produto.getPreco();
        return item;
    }

    public Produto getProduto() { return produto; }
    public double getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}