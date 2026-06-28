package TrabalhoPOO;

public class Usuario {

    private static int proximoCodigo = 1;

    private int id;
    private String nome;
    private String nomeUsuario;
    private String senha;
    private Tipousuario tipo;

    private Usuario() {}

    public static Usuario criar(String nome, String nomeUsuario, String senha, Tipousuario tipo) {
        Usuario u = new Usuario();
        u.id = proximoCodigo++;
        u.nome = nome;
        u.nomeUsuario = nomeUsuario;
        u.senha = senha;
        u.tipo = tipo;
        return u;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getNomeUsuario() { return nomeUsuario; }
    public String getSenha() { return senha; }
    public Tipousuario getTipo() { return tipo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setTipo(Tipousuario tipo) { this.tipo = tipo; }
}