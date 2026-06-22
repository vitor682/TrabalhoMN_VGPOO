
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
            System.out.println("3 - Excluir");
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

                    break;

                case 2:

                    Marca[] marcas = s.listarMarcas();//vai fazer esse funcao ir no Sistema para apenas retornar o vetor de Marcas

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "FABRICANTE");

                    for (int i = 0; i < s.qtdMarcas; i++) {

                        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                marcas[i].codigo,
                                marcas[i].nomeFantasia,
                                marcas[i].fabricante);
                    }

                    break;

                case 3:

                    Marca[] marcasExcluir = s.listarMarcas();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "FABRICANTE");

                    for (int i = 0; i < s.qtdMarcas; i++) {

                        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                marcasExcluir[i].codigo,
                                marcasExcluir[i].nomeFantasia,
                                marcasExcluir[i].fabricante);
                    }

                    System.out.print("Código: ");
                    int cod = scn.nextInt();

                    boolean excluiuMarca = s.excluirMarca(cod);

                    if (excluiuMarca) {
                        System.out.println("Marca excluída!");
                    } else {
                        System.out.println("Marca possui produtos" +
                                " ou não existe!");
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
            System.out.println("3 - Excluir");
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

                    Marca[] marcas = s.listarMarcas();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "FABRICANTE");

                    for (int i = 0; i < s.qtdMarcas; i++) {

                        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                marcas[i].codigo,
                                marcas[i].nomeFantasia,
                                marcas[i].fabricante);
                    }

                    System.out.print("Código da marca: ");
                    int codMarca = scn.nextInt();

                    Marca marca = s.buscarMarca(codMarca);

                    if (marca == null) {

                        System.out.println("Marca inválida!");

                        break;
                    }

                    System.out.print("Preço: ");
                    double preco = scn.nextDouble();

                    System.out.print("Quantidade: ");
                    int qtd = scn.nextInt();

                    Produto p = s.criarProduto(nome, marca, preco, qtd);

                    s.inserirProduto(p);

                    break;

                case 2:

                    Produto[] produtos = s.listarProdutos();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "MARCA", "PRECO", "QTD");

                    for (int i = 0; i < s.qtdProdutos; i++) {

                        Produto prod = produtos[i];

                        if (prod.excluido == false) {

                            System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                    prod.codigo,
                                    prod.nome,
                                    prod.marca.nomeFantasia,
                                    prod.preco,
                                    prod.quantEmEstoque);
                        }
                    }

                    break;

                case 3:

                    Produto[] produtosExcluir = s.listarProdutos();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "MARCA", "PRECO", "QTD");

                    for (int i = 0; i < s.qtdProdutos; i++) {

                        Produto prod = produtosExcluir[i];

                        if (prod.excluido == false) {

                            System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                    prod.codigo,
                                    prod.nome,
                                    prod.marca.nomeFantasia,
                                    prod.preco,
                                    prod.quantEmEstoque);
                        }
                    }

                    System.out.print("Código: ");
                    int cod = scn.nextInt();

                    boolean excluiuProduto = s.excluirProduto(cod);

                    if (excluiuProduto) {
                        System.out.println("Produto excluído!");
                    } else {
                        System.out.println("Produto marcado como excluído!");
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

                    Produto[] produtos = s.listarProdutos();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "MARCA", "PRECO", "QTD");

                    for (int i = 0; i < s.qtdProdutos; i++) {

                        Produto p = produtos[i];

                        if (p.excluido == false) {

                            System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                    p.codigo,
                                    p.nome,
                                    p.marca.nomeFantasia,
                                    p.preco,
                                    p.quantEmEstoque);
                        }
                    }

                    break;

                case 2:

                    Produto[] ordenados = s.listarProdutosOrdenados();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "MARCA", "PRECO", "QTD");

                    for (int i = 0; i < s.qtdProdutos; i++) {

                        Produto p = ordenados[i];

                        if (p.excluido == false) {

                            System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                    p.codigo,
                                    p.nome,
                                    p.marca.nomeFantasia,
                                    p.preco,
                                    p.quantEmEstoque);
                        }
                    }

                    break;

                case 3:

                    Marca[] marcas = s.listarMarcas();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "FABRICANTE");

                    for (int i = 0; i < s.qtdMarcas; i++) {

                        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                marcas[i].codigo,
                                marcas[i].nomeFantasia,
                                marcas[i].fabricante);
                    }

                    break;

                case 4:

                    Marca[] listaMarca = s.listarMarcas();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "FABRICANTE");

                    for (int i = 0; i < s.qtdMarcas; i++) {

                        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                listaMarca[i].codigo,
                                listaMarca[i].nomeFantasia,
                                listaMarca[i].fabricante);
                    }

                    System.out.print("Código marca: ");
                    int codMarca = scn.nextInt();

                    Produto[] produtosMarca = s.listarProdutosPorMarca(codMarca);

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "MARCA", "PRECO", "QTD");

                    for (int i = 0; i < s.qtdProdutos; i++) {

                        Produto p = produtosMarca[i];

                        if (p != null && p.excluido == false) {

                            System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                    p.codigo,
                                    p.nome,
                                    p.marca.nomeFantasia,
                                    p.preco,
                                    p.quantEmEstoque);
                        }
                    }

                    break;

                case 5:

                    Venda[] vendas = s.listarVendas();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "DATA", "VALOR");

                    for (int i = 0; i < s.qtdVendas; i++) {

                        Venda v = vendas[i];

                        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                v.codigo,
                                sdf.format(v.data),
                                s.valorVenda(v));
                    }

                    break;

                case 6:
                    vendas = s.listarVendas();

                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "DATA", "VALOR");

                    for (int i = 0; i < s.qtdVendas; i++) {

                        Venda v = vendas[i];

                        System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                v.codigo,
                                sdf.format(v.data),
                                s.valorVenda(v));
                    }
                    System.out.print("Código venda: ");
                    int codVenda = scn.nextInt();

                    Venda v = s.buscarVenda(codVenda);

                    if (v != null) {

                        Venda venda = s.detalharVenda(v);
                        System.out.println("Código: " + venda.codigo);
                        System.out.println("Cliente: " + venda.nomeCliente);
                        System.out.println("Data: " + sdf.format(venda.data));
                        System.out.println("Data e horario : " + venda.data);

                        double total = 0;

                        for (int i = 0; i < venda.qtdItens; i++) {

                            Item item = venda.itensVendidos[i];

                            double subtotal = item.preco * item.quantidade;

                            total += subtotal;

                            System.out.println(item.produto.nome +
                                    " | QTD: " + item.quantidade +
                                    " | PREÇO: " + item.preco);
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

                    Produto[] produtos = s.listarProdutos();
                    System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                            "COD", "NOME", "MARCA", "PRECO", "QTD");

                    for (int i = 0; i < s.qtdProdutos; i++) {
                        Produto p = produtos[i];
                        if (p.excluido == false) {

                            System.out.printf("%-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s %-" + s.largura + "s\n",
                                    p.codigo,
                                    p.nome,
                                    p.marca.nomeFantasia,
                                    p.preco,
                                    p.quantEmEstoque);
                        }
                    }

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

    static void mostrarCarrinho(Carrinho c) {

        System.out.println("\n=== CARRINHO ===");

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