package TrabalhoPOO;

public class Marca {

    private static int proximoCodigo = 1;

    private int codigo;
    private String nomeFantasia;
    private String fabricante;
    private String cnpj;

    private Marca() {}

    public static Marca criar(String nomeFantasia, String fabricante, String cnpj) {
        Marca m = new Marca();
        m.codigo = proximoCodigo++;
        m.nomeFantasia = nomeFantasia;
        m.fabricante = fabricante;
        m.cnpj = cnpj;
        return m;
    }

    public int getCodigo() { return codigo; }
    public String getNomeFantasia() { return nomeFantasia; }
    public String getFabricante() { return fabricante; }
    public String getCnpj() { return cnpj; }

    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}