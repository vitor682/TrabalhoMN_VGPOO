package TrabalhoPOO;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<Item> itens = new ArrayList<>();

    public boolean addItem(Item item) {
        for (Item i : itens) {
            if (i.getProduto().getCodigo() == item.getProduto().getCodigo()) {
                i.setQuantidade(i.getQuantidade() + item.getQuantidade());
                return true;
            }
        }
        itens.add(item);
        return true;
    }

    public List<Item> getItens() { return itens; }
    public int getQtdItens() { return itens.size(); }
}