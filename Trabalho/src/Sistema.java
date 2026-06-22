import java.util.Date;

public class Sistema {

    Produto[] produtos = new Produto[100];
    Marca[] marcas = new Marca[100];
    Venda[] vendas = new Venda[100];

    int qtdProdutos = 0;
    int qtdMarcas = 0;
    int qtdVendas = 0;

    int codProduto = 1;
    int codMarca = 1;
    int codVenda = 1;

    int largura = 15;

    Marca criarMarca(String nome, String fabricante, String cnpj) {

        Marca m = new Marca();

        m.codigo = codMarca++;
        m.nomeFantasia = nome;
        m.fabricante = fabricante;
        m.cnpj = cnpj;

        return m;
    }

    void inserirMarca(Marca m) {

        marcas[qtdMarcas++] = m;
    }

    Marca[] listarMarcas() {
        return marcas;
    }

    Marca buscarMarca(int cod) {

        for (int i = 0; i < qtdMarcas; i++) {

            if (marcas[i].codigo == cod) {

                return marcas[i];
            }
        }

        return null;
    }

    boolean marcaTemProduto(int codMarca) {

        for (int i = 0; i < qtdProdutos; i++) {

            if (produtos[i].marca.codigo == codMarca) {

                return true;
            }
        }

        return false;
    }

    boolean excluirMarca(int cod) {

        if (marcaTemProduto(cod)) {
            return false;
        }

        for (int i = 0; i < qtdMarcas; i++) {

            if (marcas[i].codigo == cod) {
                marcas[i] = marcas[qtdMarcas - 1];
                qtdMarcas--;

                return true;
            }
        }

        return false;
    }

    Produto criarProduto(String nome, Marca marca, double preco, int qtd) {

        Produto p = new Produto();

        p.codigo = codProduto++;
        p.nome = nome;
        p.marca = marca;
        p.preco = preco;
        p.quantEmEstoque = qtd;

        return p;
    }

    void inserirProduto(Produto p) {

        produtos[qtdProdutos++] = p;
    }

    boolean nomeProdutoExiste(String nome) {

        for (int i = 0; i < qtdProdutos; i++) {

            if (produtos[i].nome.equalsIgnoreCase(nome)) {

                return true;
            }
        }

        return false;
    }

    Produto buscarProduto(int cod) {

        for (int i = 0; i < qtdProdutos; i++) {

            if (produtos[i].codigo == cod && produtos[i].excluido == false) {

                return produtos[i];
            }
        }

        return null;
    }

    Produto[] listarProdutos() {
        return produtos;
    }

    boolean produtoTemVenda(int codProduto) {

        for (int i = 0; i < qtdVendas; i++) {

            Venda v = vendas[i];

            for (int j = 0; j < v.qtdItens; j++) {

                if (v.itensVendidos[j].produto.codigo == codProduto) {

                    return true;
                }
            }
        }

        return false;
    }

    boolean excluirProduto(int cod) {

        for (int i = 0; i < qtdProdutos; i++) {
            if (produtos[i].codigo == cod) {
                if (produtoTemVenda(cod)) {
                    produtos[i].excluido = true;
                    return false;
                }
                produtos[i] = produtos[qtdProdutos - 1];
                qtdProdutos--;
                return true;
            }
        }

        return false;
    }

    Item criarItem(Produto p, int qtd) {

        Item item = new Item();

        item.produto = p;
        item.quantidade = qtd;
        item.preco = p.preco;

        return item;
    }

    Venda criarVenda(String cliente, Carrinho c) {

        Venda v = new Venda();

        v.codigo = codVenda++;
        v.data = new Date();
        v.nomeCliente = cliente;

        for (int i = 0; i < c.qtdItens; i++) {

            v.itensVendidos[v.qtdItens++] = c.itens[i];
        }

        return v;
    }

    boolean estoqueValido(Carrinho c) {

        for (int i = 0; i < c.qtdItens; i++) {

            Item item = c.itens[i];

            if (item.quantidade > item.produto.quantEmEstoque) {

                return false;
            }
        }

        return true;
    }

    void finalizarVenda(Venda v) {

        vendas[qtdVendas++] = v;

        for (int i = 0; i < v.qtdItens; i++) {

            Item item = v.itensVendidos[i];

            item.produto.quantEmEstoque -= item.quantidade;
        }
    }

    double valorVenda(Venda v) {

        double total = 0;

        for (int i = 0; i < v.qtdItens; i++) {

            total += v.itensVendidos[i].preco * v.itensVendidos[i].quantidade;
        }

        return total;
    }

    Venda[] listarVendas() {

        return vendas;
    }

    Venda buscarVenda(int cod) {

        for (int i = 0; i < qtdVendas; i++) {

            if (vendas[i].codigo == cod) {

                return vendas[i];
            }
        }

        return null;
    }

    Venda detalharVenda(Venda v) {

        return v;
    }

    void init() {

        Marca m1 = criarMarca("PratoFino", "EmpresaA", "111");
        Marca m2 = criarMarca("Mara", "EmpresaB", "222");
        Marca m3 = criarMarca("Supangi", "EmpresaC", "333");

        inserirMarca(m1);
        inserirMarca(m2);
        inserirMarca(m3);

        Produto p1 = criarProduto("Arroz", m1, 25, 100);
        Produto p2 = criarProduto("Feijao", m2, 10, 50);
        Produto p3 = criarProduto("Cafe", m3, 15, 30);
        Produto p4 = criarProduto("Macarrao", m1, 7, 200);
        Produto p5 = criarProduto("Acucar", m2, 5, 500);

        inserirProduto(p1);
        inserirProduto(p2);
        inserirProduto(p3);
        inserirProduto(p4);
        inserirProduto(p5);

        Carrinho c1 = new Carrinho();

        c1.addItem(criarItem(p1, 2));
        c1.addItem(criarItem(p2, 1));

        Venda v1 = criarVenda("Matheus", c1);

        finalizarVenda(v1);

        Carrinho c2 = new Carrinho();

        c2.addItem(criarItem(p3, 1));

        Venda v2 = criarVenda("Joao", c2);

        finalizarVenda(v2);
    }

    Produto[] listarProdutosOrdenados() {

        Produto[] aux = new Produto[100];

        for (int i = 0; i < qtdProdutos; i++) {

            aux[i] = produtos[i];
        }

        for (int i = 0; i < qtdProdutos - 1; i++) {

            for (int j = i + 1; j < qtdProdutos; j++) {

                if (aux[i].nome.compareToIgnoreCase(aux[j].nome) > 0) {

                    Produto temp = aux[i];

                    aux[i] = aux[j];
                    aux[j] = temp;
                }
            }
        }

        return aux;
    }

    Produto[] listarProdutosPorMarca(int codMarca) {

        Produto[] aux = new Produto[100];

        int qtd = 0;

        for (int i = 0; i < qtdProdutos; i++) {

            Produto p = produtos[i];

            if (p.marca.codigo == codMarca && p.excluido == false) {

                aux[qtd] = p;

                qtd++;
            }
        }

        return aux;
    }
}