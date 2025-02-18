package models.enums;

public enum StatusMatricula {
    ATIVA(1),
    TRANCADO(2),
    DESATIVADO(3);

    private int codigo;

    StatusMatricula(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
