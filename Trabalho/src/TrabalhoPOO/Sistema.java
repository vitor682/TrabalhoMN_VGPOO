package TrabalhoPOO;

import java.util.ArrayList;
import java.util.List;

public class Sistema {

    private static Sistema instancia;

    private List<Produto> produtos = new ArrayList<>();
    private List<Marca> marcas = new ArrayList<>();
    private List<Venda> vendas = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    private int largura = 15;

    private Sistema() {}

    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public int getLargura() { return largura; }
    public void setLargura(int largura) { this.largura = largura; }

    // ========== MARCA ==========

    public void inserirMarca(Marca m) {
        marcas.add(m);
    }

    public List<Marca> listarMarcas() {
        return marcas;
    }

    public Marca buscarMarca(int cod) {
        for (Marca m : marcas) {
            if (m.getCodigo() == cod) return m;
        }
        return null;
    }

    public boolean marcaTemProduto(int codMarca) {
        for (Produto p : produtos) {
            if (p.getMarca().getCodigo() == codMarca) return true;
        }
        return false;
    }

    public boolean excluirMarca(int cod) {
        if (marcaTemProduto(cod)) return false;
        for (int i = 0; i < marcas.size(); i++) {
            if (marcas.get(i).getCodigo() == cod) {
                marcas.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean atualizarMarca(int cod, String novoNome, String novoFabricante, String novoCnpj) {
        Marca m = buscarMarca(cod);
        if (m == null) return false;
        if (novoNome != null && !novoNome.isEmpty()) m.setNomeFantasia(novoNome);
        if (novoFabricante != null && !novoFabricante.isEmpty()) m.setFabricante(novoFabricante);
        if (novoCnpj != null && !novoCnpj.isEmpty()) m.setCnpj(novoCnpj);
        return true;
    }

    // ========== PRODUTO ==========

    public void inserirProduto(Produto p) {
        produtos.add(p);
    }

    public boolean nomeProdutoExiste(String nome) {
        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) return true;
        }
        return false;
    }

    public Produto buscarProduto(int cod) {
        for (Produto p : produtos) {
            if (p.getCodigo() == cod && !p.isExcluido()) return p;
        }
        return null;
    }

    public List<Produto> listarProdutos() {
        return produtos;
    }

    public boolean produtoTemVenda(int codProduto) {
        for (Venda v : vendas) {
            for (Item item : v.getItensVendidos()) {
                if (item.getProduto().getCodigo() == codProduto) return true;
            }
        }
        return false;
    }

    public boolean excluirProduto(int cod) {
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            if (p.getCodigo() == cod) {
                if (produtoTemVenda(cod)) {
                    p.setExcluido(true);
                    return false;
                }
                produtos.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean atualizarProduto(int cod, String novoNome, int codMarca, double novoPreco, int novaQtd) {
        Produto p = buscarProduto(cod);
        if (p == null) return false;
        if (novoNome != null && !novoNome.isEmpty()) p.setNome(novoNome);
        if (codMarca > 0) {
            Marca m = buscarMarca(codMarca);
            if (m == null) return false;
            p.setMarca(m);
        }
        if (novoPreco > 0) p.setPreco(novoPreco);
        if (novaQtd >= 0) p.setQuantEmEstoque(novaQtd);
        return true;
    }

    public List<Produto> listarProdutosOrdenados() {
        List<Produto> aux = new ArrayList<>(produtos);
        for (int i = 0; i < aux.size() - 1; i++) {
            for (int j = i + 1; j < aux.size(); j++) {
                if (aux.get(i).getNome().compareToIgnoreCase(aux.get(j).getNome()) > 0) {
                    Produto temp = aux.get(i);
                    aux.set(i, aux.get(j));
                    aux.set(j, temp);
                }
            }
        }
        return aux;
    }

    public List<Produto> listarProdutosPorMarca(int codMarca) {
        List<Produto> aux = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getMarca().getCodigo() == codMarca && !p.isExcluido()) {
                aux.add(p);
            }
        }
        return aux;
    }

    // ========== VENDA ==========

    public boolean estoqueValido(Carrinho c) {
        for (Item item : c.getItens()) {
            if (item.getQuantidade() > item.getProduto().getQuantEmEstoque()) return false;
        }
        return true;
    }

    public void finalizarVenda(Venda v) {
        vendas.add(v);
        for (Item item : v.getItensVendidos()) {
            item.getProduto().setQuantEmEstoque(item.getProduto().getQuantEmEstoque() - item.getQuantidade());
        }
    }

    public double valorVenda(Venda v) {
        double total = 0;
        for (Item item : v.getItensVendidos()) {
            total += item.getPreco() * item.getQuantidade();
        }
        return total;
    }

    public List<Venda> listarVendas() {
        return vendas;
    }

    public Venda buscarVenda(int cod) {
        for (Venda v : vendas) {
            if (v.getCodigo() == cod) return v;
        }
        return null;
    }

    // ========== USUARIO ==========

    public void inserirUsuario(Usuario u) {
        usuarios.add(u);
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario buscarUsuario(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public Usuario buscarUsuarioPorLogin(String nomeUsuario, String senha) {
        for (Usuario u : usuarios) {
            if (u.getNomeUsuario().equals(nomeUsuario) && u.getSenha().equals(senha)) return u;
        }
        return null;
    }

    public boolean nomeUsuarioExiste(String nomeUsuario) {
        for (Usuario u : usuarios) {
            if (u.getNomeUsuario().equals(nomeUsuario)) return true;
        }
        return false;
    }

    public boolean excluirUsuario(int id) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                usuarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean atualizarUsuario(int id, String novoNome, String novoNomeUsuario, String novaSenha, Tipousuario novoTipo) {
        Usuario u = buscarUsuario(id);
        if (u == null) return false;
        if (novoNome != null && !novoNome.isEmpty()) u.setNome(novoNome);
        if (novoNomeUsuario != null && !novoNomeUsuario.isEmpty()) u.setNomeUsuario(novoNomeUsuario);
        if (novaSenha != null && !novaSenha.isEmpty()) u.setSenha(novaSenha);
        if (novoTipo != null) u.setTipo(novoTipo);
        return true;
    }

    // ========== INIT ==========

    public void init() {
        Marca m1 = Marca.criar("PratoFino", "EmpresaA", "111");
        Marca m2 = Marca.criar("Mara", "EmpresaB", "222");
        Marca m3 = Marca.criar("Supangi", "EmpresaC", "333");
        inserirMarca(m1);
        inserirMarca(m2);
        inserirMarca(m3);

        Produto p1 = Produto.criar("Arroz", m1, 25, 100);
        Produto p2 = Produto.criar("Feijao", m2, 10, 50);
        Produto p3 = Produto.criar("Cafe", m3, 15, 30);
        Produto p4 = Produto.criar("Macarrao", m1, 7, 200);
        Produto p5 = Produto.criar("Acucar", m2, 5, 500);
        inserirProduto(p1);
        inserirProduto(p2);
        inserirProduto(p3);
        inserirProduto(p4);
        inserirProduto(p5);

        Carrinho c1 = new Carrinho();
        c1.addItem(Item.criar(p1, 2));
        c1.addItem(Item.criar(p2, 1));
        finalizarVenda(Venda.criar("Matheus", c1));

        Carrinho c2 = new Carrinho();
        c2.addItem(Item.criar(p3, 1));
        finalizarVenda(Venda.criar("Joao", c2));

        inserirUsuario(Usuario.criar("Administrador", "admin", "1234", Tipousuario.ADMIN));
        inserirUsuario(Usuario.criar("Atendente", "atendente", "1234", Tipousuario.ATENDENTE));
    }
}