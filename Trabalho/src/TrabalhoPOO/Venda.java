package TrabalhoPOO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {

    private static int proximoCodigo = 1;

    private int codigo;
    private Date data;
    private List<Item> itensVendidos = new ArrayList<>();
    private String nomeCliente;

    private Venda() {}

    public static Venda criar(String nomeCliente, Carrinho carrinho) {
        Venda v = new Venda();
        v.codigo = proximoCodigo++;
        v.data = new Date();
        v.nomeCliente = nomeCliente;
        for (Item item : carrinho.getItens()) {
            v.itensVendidos.add(item);
        }
        return v;
    }

    public int getCodigo() { return codigo; }
    public Date getData() { return data; }
    public List<Item> getItensVendidos() { return itensVendidos; }
    public String getNomeCliente() { return nomeCliente; }
    public int getQtdItens() { return itensVendidos.size(); }
}