package TrabalhoPOO;

import java.util.Scanner;
import java.text.SimpleDateFormat;

//Matheus Nascimento e Vitor Lucas
public class Main {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Sistema s = new Sistema();
        s.init();

        int op;

        do {
            System.out.println("\n=== SISTEMA ===");
            System.out.println("1 - Administrador");
            System.out.println("2 - Atendente");
            System.out.println("0 - Sair");

            op = scn.nextInt();

            switch (op) {
                case 1:
                    menuAdministrador(scn, s);
                    break;
                case 2:
                    menuAtendente(scn, s);
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (op != 0);
    }

    static void menuAdministrador(Scanner scn, Sistema s) {
        int op;

        do {
            System.out.println("\n=== ADMINISTRADOR ===");
            System.out.println("1 - CRUD Marca");
            System.out.println("2 - CRUD Produto");
            System.out.println("3 - Listagens");
            System.out.println("4 - Configurar Tabela");
            System.out.println("0 - Voltar");

            op = scn.nextInt();

            switch (op) {
                case 1:
                    crudMarca(scn, s);
                    break;
                case 2:
                    crudProduto(scn, s);
                    break;
                case 3:
                    menuListagens(scn, s);
                    break;
                case 4:
                    System.out.print("Nova largura: ");
                    int largura = scn.nextInt();
                    s.largura = largura;
                    break;
            }

        } while (op != 0);
    }

    static void crudMarca(Scanner scn, Sistema s) {
        int op;

        do {
            System.out.println("\n=== CRUD MARCA ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");

            op = scn.nextInt();

            switch (op) {

                case 1:
                    System.out.print("Nome Fantasia: ");
                    String nome = scn.next();

                    System.out.print("Fabricante: ");
                    String fabricante = scn.next();

                    System.out.print("CNPJ: ");
                    String cnpj = scn.next();

                    Marca m = s.criarMarca(nome, fabricante, cnpj);
                    s.inserirMarca(m);

                    System.out.println("Marca cadastrada!");
                    break;

                case 2:
                    listarMarcasTabela(s);
                    break;

                case 3:
                    listarMarcasTabela(s);

                    System.out.print("Código da marca a atualizar: ");
                    int codAtualizar = scn.nextInt();

                    Marca marcaAtualizar = s.buscarMarca(codAtualizar);

                    if (marcaAtualizar == null) {
                        System.out.println("Marca não encontrada!");
                        break;
                    }

                    System.out.println("Deixe em branco (pressione ENTER) para manter o valor atual.");

                    System.out.print("Novo Nome Fantasia [" + marcaAtualizar.nomeFantasia + "]: ");
                    String novoNome = scn.next();
                    if (novoNome.equals("-")) novoNome = "";

                    System.out.print("Novo Fabricante [" + marcaAtualizar.fabricante + "]: ");
                    String novoFabricante = scn.next();
                    if (novoFabricante.equals("-")) novoFabricante = "";

                    System.out.print("Novo CNPJ [" + marcaAtualizar.cnpj + "]: ");
                    String novoCnpj = scn.next();
                    if (novoCnpj.equals("-")) novoCnpj = "";

                    boolean atualizouMarca = s.atualizarMarca(codAtualizar, novoNome, novoFabricante, novoCnpj);

                    if (atualizouMarca) {
                        System.out.println("Marca atualizada!");
                    } else {
                        System.out.println("Erro ao atualizar marca!");
                    }
                    break;

                case 4:
                    listarMarcasTabela(s);

                    System.out.print("Código: ");
                    int cod = scn.nextInt();

                    boolean excluiuMarca = s.excluirMarca(cod);

                    if (excluiuMarca) {
                        System.out.println("Marca excluída!");
                    } else {
                        System.out.println("Marca possui produtos ou não existe!");
                    }
                    break;
            }

        } while (op != 0);
    }

    static void crudProduto(Scanner scn, Sistema s) {
        int op;

        do {
            System.out.println("\n=== CRUD PRODUTO ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");

            op = scn.nextInt();

            switch (op) {

                case 1:
                    System.out.print("Nome: ");
                    String nome = scn.next();

                    if (s.nomeProdutoExiste(nome)) {
                        System.out.println("Produto já existe!");
                        break;
                    }

                    listarMarcasTabela(s);

                    System.out.print("Código da marca: ");
                    int codMarcaCad = scn.nextInt();

                    Marca marcaCad = s.buscarMarca(codMarcaCad);

                    if (marcaCad == null) {
                        System.out.println("Marca inválida!");
                        break;
                    }

                    System.out.print("Preço: ");
                    double preco = scn.nextDouble();

                    System.out.print("Quantidade: ");
                    int qtd = scn.nextInt();

                    Produto p = s.criarProduto(nome, marcaCad, preco, qtd);
                    s.inserirProduto(p);

                    System.out.println("Produto cadastrado!");
                    break;

                case 2:
                    listarProdutosTabela(s, s.listarProdutos());
                    break;

                case 3:
                    listarProdutosTabela(s, s.listarProdutos());

                    System.out.print("Código do produto a atualizar: ");
                    int codAtualizar = scn.nextInt();

                    Produto prodAtualizar = s.buscarProduto(codAtualizar);

                    if (prodAtualizar == null) {
                        System.out.println("Produto não encontrado!");
                        break;
                    }

                    System.out.println("Digite '-' para manter o valor atual (texto) ou 0 para manter (número).");

                    System.out.print("Novo Nome [" + prodAtualizar.nome + "]: ");
                    String novoNome = scn.next();
                    if (novoNome.equals("-")) novoNome = "";

                    listarMarcasTabela(s);
                    System.out.print("Novo Código de Marca [" + prodAtualizar.marca.codigo + "] (0 para manter): ");
                    int novoCodMarca = scn.nextInt();

                    System.out.print("Novo Preço [" + prodAtualizar.preco + "] (0 para manter): ");
                    double novoPreco = scn.nextDouble();

                    System.out.print("Nova Quantidade [" + prodAtualizar.quantEmEstoque + "] (-1 para manter): ");
                    int novaQtd = scn.nextInt();

                    boolean atualizouProduto = s.atualizarProduto(codAtualizar, novoNome, novoCodMarca, novoPreco, novaQtd);

                    if (atualizouProduto) {
                        System.out.println("Produto atualizado!");
                    } else {
                        System.out.println("Erro ao atualizar produto! Verifique se a marca informada existe.");
                    }
                    break;

                case 4:
                    listarProdutosTabela(s, s.listarProdutos());

                    System.out.print("Código: ");
                    int cod = scn.nextInt();

                    boolean excluiuProduto = s.excluirProduto(cod);

                    if (excluiuProduto) {
                        System.out.println("Produto excluído!");
                    } else {
                        System.out.println("Produto marcado como excluído (possui vendas)!");
                    }
                    break;
            }

        } while (op != 0);
    }

    static void menuListagens(Scanner scn, Sistema s) {
        int op;

        do {
            System.out.println("\n=== LISTAGENS ===");
            System.out.println("1 - Todos produtos");
            System.out.println("2 - Produtos ordenados");
            System.out.println("3 - Todas marcas");
            System.out.println("4 - Produtos por marca");
            System.out.println("5 - Todas vendas");
            System.out.println("6 - Buscar venda");
            System.out.println("0 - Voltar");

            op = scn.nextInt();

            switch (op) {

                case 1:
                    listarProdutosTabela(s, s.listarProdutos());
                    break;

                case 2:
                    listarProdutosTabela(s, s.listarProdutosOrdenados());
                    break;

                case 3:
                    listarMarcasTabela(s);
                    break;

                case 4:
                    listarMarcasTabela(s);

                    System.out.print("Código marca: ");
                    int codMarca = scn.nextInt();

                    Produto[] produtosMarca = s.listarProdutosPorMarca(codMarca);
                    int qtdMarca = s.contarProdutosPorMarca(codMarca);

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "MARCA", "PRECO", "QTD");

                    for (int i = 0; i < qtdMarca; i++) {
                        Produto p = produtosMarca[i];
                        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                p.codigo,
                                p.nome,
                                p.marca.nomeFantasia,
                                p.preco,
                                p.quantEmEstoque);
                    }
                    break;

                case 5:
                    listarVendasTabela(s);
                    break;

                case 6:
                    listarVendasTabela(s);

                    System.out.print("Código venda: ");
                    int codVenda = scn.nextInt();

                    Venda v = s.buscarVenda(codVenda);

                    if (v != null) {
                        Venda venda = s.detalharVenda(v);
                        System.out.println("Código: " + venda.codigo);
                        System.out.println("Cliente: " + venda.nomeCliente);
                        System.out.println("Data: " + sdf.format(venda.data));
                        System.out.println("Data e horário: " + venda.data);

                        double total = 0;

                        for (int i = 0; i < venda.qtdItens; i++) {
                            Item item = venda.itensVendidos[i];
                            double subtotal = item.preco * item.quantidade;
                            total += subtotal;
                            System.out.println(item.produto.nome +
                                    " | QTD: " + item.quantidade +
                                    " | PREÇO: " + item.preco +
                                    " | SUBTOTAL: " + subtotal);
                        }

                        System.out.println("TOTAL: " + total);
                    } else {
                        System.out.println("Venda não encontrada!");
                    }
                    break;
            }

        } while (op != 0);
    }

    static void menuAtendente(Scanner scn, Sistema s) {
        Carrinho carrinho = new Carrinho();
        int op;

        do {
            System.out.println("\n=== ATENDENTE ===");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Ver carrinho");
            System.out.println("3 - Finalizar venda");
            System.out.println("0 - Voltar");

            op = scn.nextInt();

            switch (op) {

                case 1:
                    listarProdutosTabela(s, s.listarProdutos());

                    System.out.print("Código produto: ");
                    int codProduto = scn.nextInt();

                    Produto p = s.buscarProduto(codProduto);

                    if (p == null) {
                        System.out.println("Produto inválido!");
                        break;
                    }

                    System.out.print("Quantidade: ");
                    int qtd = scn.nextInt();

                    Item item = s.criarItem(p, qtd);
                    carrinho.addItem(item);

                    System.out.println("Produto adicionado!");
                    break;

                case 2:
                    mostrarCarrinho(carrinho);
                    break;

                case 3:
                    if (carrinho.qtdItens == 0) {
                        System.out.println("Carrinho vazio!");
                        break;
                    }

                    if (!s.estoqueValido(carrinho)) {
                        System.out.println("Estoque insuficiente!");
                        break;
                    }

                    System.out.print("Nome cliente: ");
                    String cliente = scn.next();

                    Venda venda = s.criarVenda(cliente, carrinho);
                    s.finalizarVenda(venda);

                    System.out.println("Venda realizada!");
                    carrinho = new Carrinho();
                    break;
            }

        } while (op != 0);
    }

    static void listarMarcasTabela(Sistema s) {
        Marca[] marcas = s.listarMarcas();
        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                "COD", "NOME", "FABRICANTE");
        for (int i = 0; i < s.qtdMarcas; i++) {
            System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                    marcas[i].codigo,
                    marcas[i].nomeFantasia,
                    marcas[i].fabricante);
        }
    }

    static void listarProdutosTabela(Sistema s, Produto[] produtos) {
        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                "COD", "NOME", "MARCA", "PRECO", "QTD");
        for (int i = 0; i < s.qtdProdutos; i++) {
            Produto p = produtos[i];
            if (p != null && p.excluido == false) {
                System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                        p.codigo,
                        p.nome,
                        p.marca.nomeFantasia,
                        p.preco,
                        p.quantEmEstoque);
            }
        }
    }

    static void listarVendasTabela(Sistema s) {
        Venda[] vendas = s.listarVendas();
        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                "COD", "CLIENTE", "DATA", "VALOR");
        for (int i = 0; i < s.qtdVendas; i++) {
            Venda v = vendas[i];
            System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                    v.codigo,
                    v.nomeCliente,
                    sdf.format(v.data),
                    s.valorVenda(v));
        }
    }

    static void mostrarCarrinho(Carrinho c) {
        System.out.println("\n=== CARRINHO ===");

        if (c.qtdItens == 0) {
            System.out.println("Carrinho vazio.");
            return;
        }

        double total = 0;

        for (int i = 0; i < c.qtdItens; i++) {
            Item item = c.itens[i];
            double subtotal = item.preco * item.quantidade;
            total += subtotal;
            System.out.println(item.produto.nome +
                    " | QTD: " + item.quantidade +
                    " | PREÇO: " + item.preco +
                    " | SUBTOTAL: " + subtotal);
        }

        System.out.println("TOTAL: " + total);
    }
}