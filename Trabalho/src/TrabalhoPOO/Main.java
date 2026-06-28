package TrabalhoPOO;

import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class Main {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Sistema s = Sistema.getInstancia();
        s.init();

        while (true) {
            Usuario usuario = telaLogin(scn, s);
            if (usuario == null) {
                System.out.println("Encerrando...");
                break;
            }

            boolean trocar = false;
            if (usuario.getTipo() == Tipousuario.ADMIN) {
                trocar = menuAdministrador(scn, s);
            } else {
                trocar = menuAtendente(scn, s);
            }

            if (!trocar) break;
        }
    }

    static Usuario telaLogin(Scanner scn, Sistema s) {
        System.out.println("\n=== LOGIN ===");
        System.out.println("0 - Sair");
        System.out.print("Usuário: ");
        String nomeUsuario = scn.next();

        if (nomeUsuario.equals("0")) return null;

        System.out.print("Senha: ");
        String senha = scn.next();

        Usuario u = s.buscarUsuarioPorLogin(nomeUsuario, senha);
        if (u == null) {
            System.out.println("Usuário ou senha inválidos!");
            return telaLogin(scn, s);
        }

        System.out.println("Bem-vindo, " + u.getNome() + " [" + u.getTipo() + "]");
        return u;
    }

    // Retorna true se o usuário quer trocar de usuário, false para sair do sistema
    static boolean menuAdministrador(Scanner scn, Sistema s) {
        int op;
        do {
            System.out.println("\n=== ADMINISTRADOR ===");
            System.out.println("1 - CRUD Marca");
            System.out.println("2 - CRUD Produto");
            System.out.println("3 - CRUD Usuário");
            System.out.println("4 - Listagens");
            System.out.println("5 - Configurar Tabela");
            System.out.println("9 - Trocar usuário");
            System.out.println("0 - Sair");

            op = scn.nextInt();

            switch (op) {
                case 1: crudMarca(scn, s); break;
                case 2: crudProduto(scn, s); break;
                case 3: crudUsuario(scn, s); break;
                case 4: menuListagens(scn, s); break;
                case 5:
                    System.out.print("Nova largura: ");
                    s.setLargura(scn.nextInt());
                    break;
                case 9: return true;
                case 0: return false;
            }
        } while (true);
    }

    static boolean menuAtendente(Scanner scn, Sistema s) {
        Carrinho carrinho = new Carrinho();
        int op;
        do {
            System.out.println("\n=== ATENDENTE ===");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Ver carrinho");
            System.out.println("3 - Finalizar venda");
            System.out.println("9 - Trocar usuário");
            System.out.println("0 - Sair");

            op = scn.nextInt();

            switch (op) {
                case 1:
                    listarProdutosTabela(s, s.listarProdutos());
                    System.out.print("Código produto: ");
                    int codProduto = scn.nextInt();
                    Produto p = s.buscarProduto(codProduto);
                    if (p == null) { System.out.println("Produto inválido!"); break; }
                    System.out.print("Quantidade: ");
                    int qtd = scn.nextInt();
                    carrinho.addItem(Item.criar(p, qtd));
                    System.out.println("Produto adicionado!");
                    break;

                case 2:
                    mostrarCarrinho(carrinho);
                    break;

                case 3:
                    if (carrinho.getQtdItens() == 0) { System.out.println("Carrinho vazio!"); break; }
                    if (!s.estoqueValido(carrinho)) { System.out.println("Estoque insuficiente!"); break; }
                    System.out.print("Nome cliente: ");
                    String cliente = scn.next();
                    s.finalizarVenda(Venda.criar(cliente, carrinho));
                    System.out.println("Venda realizada!");
                    carrinho = new Carrinho();
                    break;

                case 9: return true;
                case 0: return false;
            }
        } while (true);
    }

    // ========== CRUD MARCA ==========

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
                    s.inserirMarca(Marca.criar(nome, fabricante, cnpj));
                    System.out.println("Marca cadastrada!");
                    break;

                case 2:
                    listarMarcasTabela(s);
                    break;

                case 3:
                    listarMarcasTabela(s);
                    System.out.print("Código da marca a atualizar: ");
                    int codAt = scn.nextInt();
                    Marca marcaAt = s.buscarMarca(codAt);
                    if (marcaAt == null) { System.out.println("Marca não encontrada!"); break; }
                    System.out.println("Digite '-' para manter o valor atual.");
                    System.out.print("Novo Nome Fantasia [" + marcaAt.getNomeFantasia() + "]: ");
                    String novoNome = scn.next();
                    if (novoNome.equals("-")) novoNome = "";
                    System.out.print("Novo Fabricante [" + marcaAt.getFabricante() + "]: ");
                    String novoFab = scn.next();
                    if (novoFab.equals("-")) novoFab = "";
                    System.out.print("Novo CNPJ [" + marcaAt.getCnpj() + "]: ");
                    String novoCnpj = scn.next();
                    if (novoCnpj.equals("-")) novoCnpj = "";
                    System.out.println(s.atualizarMarca(codAt, novoNome, novoFab, novoCnpj) ? "Marca atualizada!" : "Erro ao atualizar!");
                    break;

                case 4:
                    listarMarcasTabela(s);
                    System.out.print("Código: ");
                    int cod = scn.nextInt();
                    System.out.println(s.excluirMarca(cod) ? "Marca excluída!" : "Marca possui produtos ou não existe!");
                    break;
            }
        } while (op != 0);
    }

    // ========== CRUD PRODUTO ==========

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
                    if (s.nomeProdutoExiste(nome)) { System.out.println("Produto já existe!"); break; }
                    listarMarcasTabela(s);
                    System.out.print("Código da marca: ");
                    int codMarcaCad = scn.nextInt();
                    Marca marcaCad = s.buscarMarca(codMarcaCad);
                    if (marcaCad == null) { System.out.println("Marca inválida!"); break; }
                    System.out.print("Preço: ");
                    double preco = scn.nextDouble();
                    System.out.print("Quantidade: ");
                    int qtd = scn.nextInt();
                    s.inserirProduto(Produto.criar(nome, marcaCad, preco, qtd));
                    System.out.println("Produto cadastrado!");
                    break;

                case 2:
                    listarProdutosTabela(s, s.listarProdutos());
                    break;

                case 3:
                    listarProdutosTabela(s, s.listarProdutos());
                    System.out.print("Código do produto a atualizar: ");
                    int codAt = scn.nextInt();
                    Produto prodAt = s.buscarProduto(codAt);
                    if (prodAt == null) { System.out.println("Produto não encontrado!"); break; }
                    System.out.println("Digite '-' para manter o valor atual (texto) ou 0 para manter (número).");
                    System.out.print("Novo Nome [" + prodAt.getNome() + "]: ");
                    String novoNome = scn.next();
                    if (novoNome.equals("-")) novoNome = "";
                    System.out.print("Novo Código de Marca [" + prodAt.getMarca().getCodigo() + "]: ");
                    int novoCodMarca = scn.nextInt();
                    System.out.print("Novo Preço [" + prodAt.getPreco() + "]: ");
                    double novoPreco = scn.nextDouble();
                    System.out.print("Nova Qtd [" + prodAt.getQuantEmEstoque() + "]: ");
                    int novaQtd = scn.nextInt();
                    if (novaQtd == 0) novaQtd = -1;
                    boolean atualizou = s.atualizarProduto(codAt, novoNome, novoCodMarca, novoPreco, novaQtd);
                    System.out.println(atualizou ? "Produto atualizado!" : "Erro ao atualizar!");
                    break;

                case 4:
                    listarProdutosTabela(s, s.listarProdutos());
                    System.out.print("Código: ");
                    int cod = scn.nextInt();
                    boolean excluiu = s.excluirProduto(cod);
                    if (excluiu) {
                        System.out.println("Produto excluído!");
                    } else {
                        System.out.println("Produto marcado como excluído (possui vendas)!");
                    }
                    break;
            }
        } while (op != 0);
    }

    // ========== CRUD USUARIO ==========

    static void crudUsuario(Scanner scn, Sistema s) {
        int op;
        do {
            System.out.println("\n=== CRUD USUÁRIO ===");
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
                    System.out.print("Nome de usuário: ");
                    String nomeUsuario = scn.next();
                    if (s.nomeUsuarioExiste(nomeUsuario)) { System.out.println("Nome de usuário já existe!"); break; }
                    System.out.print("Senha: ");
                    String senha = scn.next();
                    System.out.println("Tipo (1 - ADMIN / 2 - ATENDENTE): ");
                    int tipoInt = scn.nextInt();
                    Tipousuario tipo = tipoInt == 1 ? Tipousuario.ADMIN : Tipousuario.ATENDENTE;
                    s.inserirUsuario(Usuario.criar(nome, nomeUsuario, senha, tipo));
                    System.out.println("Usuário cadastrado!");
                    break;

                case 2:
                    listarUsuariosTabela(s);
                    break;

                case 3:
                    listarUsuariosTabela(s);
                    System.out.print("ID do usuário a atualizar: ");
                    int idAt = scn.nextInt();
                    Usuario uAt = s.buscarUsuario(idAt);
                    if (uAt == null) { System.out.println("Usuário não encontrado!"); break; }
                    System.out.println("Digite '-' para manter o valor atual.");
                    System.out.print("Novo Nome [" + uAt.getNome() + "]: ");
                    String novoNome = scn.next();
                    if (novoNome.equals("-")) novoNome = "";
                    System.out.print("Novo Nome de Usuário [" + uAt.getNomeUsuario() + "]: ");
                    String novoNomeUsuario = scn.next();
                    if (novoNomeUsuario.equals("-")) novoNomeUsuario = "";
                    System.out.print("Nova Senha: ");
                    String novaSenha = scn.next();
                    if (novaSenha.equals("-")) novaSenha = "";
                    System.out.println("Novo Tipo (1 - ADMIN / 2 - ATENDENTE / 0 - manter): ");
                    int novoTipoInt = scn.nextInt();
                    Tipousuario novoTipo = novoTipoInt == 1 ? Tipousuario.ADMIN : novoTipoInt == 2 ? Tipousuario.ATENDENTE : null;
                    System.out.println(s.atualizarUsuario(idAt, novoNome, novoNomeUsuario, novaSenha, novoTipo) ? "Usuário atualizado!" : "Erro!");
                    break;

                case 4:
                    listarUsuariosTabela(s);
                    System.out.print("ID: ");
                    int id = scn.nextInt();
                    System.out.println(s.excluirUsuario(id) ? "Usuário excluído!" : "Usuário não encontrado!");
                    break;
            }
        } while (op != 0);
    }

    // ========== LISTAGENS ==========

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
                case 1: listarProdutosTabela(s, s.listarProdutos()); break;
                case 2: listarProdutosTabela(s, s.listarProdutosOrdenados()); break;
                case 3: listarMarcasTabela(s); break;
                case 4:
                    listarMarcasTabela(s);
                    System.out.print("Código marca: ");
                    int codMarca = scn.nextInt();
                    listarProdutosTabela(s, s.listarProdutosPorMarca(codMarca));
                    break;
                case 5: listarVendasTabela(s); break;
                case 6:
                    listarVendasTabela(s);
                    System.out.print("Código venda: ");
                    int codVenda = scn.nextInt();
                    Venda v = s.buscarVenda(codVenda);
                    if (v != null) {
                        System.out.println("Código: " + v.getCodigo());
                        System.out.println("Cliente: " + v.getNomeCliente());
                        System.out.println("Data: " + sdf.format(v.getData()));
                        double total = 0;
                        for (Item item : v.getItensVendidos()) {
                            double subtotal = item.getPreco() * item.getQuantidade();
                            total += subtotal;
                            System.out.println(item.getProduto().getNome() +
                                    " | QTD: " + item.getQuantidade() +
                                    " | PREÇO: " + item.getPreco() +
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

    // ========== TABELAS ==========

    static void listarMarcasTabela(Sistema s) {
        int l = s.getLargura();
        System.out.printf("%-" + l + "s %-" + l + "s %-" + l + "s\n", "COD", "NOME", "FABRICANTE");
        for (Marca m : s.listarMarcas()) {
            System.out.printf("%-" + l + "s %-" + l + "s %-" + l + "s\n",
                    m.getCodigo(), m.getNomeFantasia(), m.getFabricante());
        }
    }

    static void listarProdutosTabela(Sistema s, List<Produto> produtos) {
        int l = s.getLargura();
        System.out.printf("%-" + l + "s %-" + l + "s %-" + l + "s %-" + l + "s %-" + l + "s\n",
                "COD", "NOME", "MARCA", "PRECO", "QTD");
        for (Produto p : produtos) {
            if (p != null && !p.isExcluido()) {
                System.out.printf("%-" + l + "s %-" + l + "s %-" + l + "s %-" + l + "s %-" + l + "s\n",
                        p.getCodigo(), p.getNome(), p.getMarca().getNomeFantasia(),
                        p.getPreco(), p.getQuantEmEstoque());
            }
        }
    }

    static void listarVendasTabela(Sistema s) {
        int l = s.getLargura();
        System.out.printf("%-" + l + "s %-" + l + "s %-" + l + "s %-" + l + "s\n",
                "COD", "CLIENTE", "DATA", "VALOR");
        for (Venda v : s.listarVendas()) {
            System.out.printf("%-" + l + "s %-" + l + "s %-" + l + "s %-" + l + "s\n",
                    v.getCodigo(), v.getNomeCliente(), sdf.format(v.getData()), s.valorVenda(v));
        }
    }

    static void listarUsuariosTabela(Sistema s) {
        int l = s.getLargura();
        System.out.printf("%-" + l + "s %-" + l + "s %-" + l + "s %-" + l + "s\n",
                "ID", "NOME", "USUÁRIO", "TIPO");
        for (Usuario u : s.listarUsuarios()) {
            System.out.printf("%-" + l + "s %-" + l + "s %-" + l + "s %-" + l + "s\n",
                    u.getId(), u.getNome(), u.getNomeUsuario(), u.getTipo());
        }
    }

    static void mostrarCarrinho(Carrinho c) {
        System.out.println("\n=== CARRINHO ===");
        if (c.getQtdItens() == 0) { System.out.println("Carrinho vazio."); return; }
        double total = 0;
        for (Item item : c.getItens()) {
            double subtotal = item.getPreco() * item.getQuantidade();
            total += subtotal;
            System.out.println(item.getProduto().getNome() +
                    " | QTD: " + item.getQuantidade() +
                    " | PREÇO: " + item.getPreco() +
                    " | SUBTOTAL: " + subtotal);
        }
        System.out.println("TOTAL: " + total);
    }
}