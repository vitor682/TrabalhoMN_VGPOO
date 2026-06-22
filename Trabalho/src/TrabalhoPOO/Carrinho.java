package TrabalhoPOO;

public class Carrinho {
    Item[] itens = new Item[100];
    int qtdItens = 0;
    boolean addItem(Item item) {
        for (int i = 0; i < qtdItens; i++) { // quando adicionar um produto se ele ja existir vai add a qtd dele
            if (itens[i].produto.codigo == item.produto.codigo) {
                itens[i].quantidade += item.quantidade;
                return true;
            }
        }
        itens[qtdItens++] = item;
        return true;
    }
}