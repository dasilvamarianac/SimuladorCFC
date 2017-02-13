package app.robots.simuladorcfc;

public class QuestaoView {

    private String enunciado;
    private String alt1;
    private String alt2;
    private String alt3;
    private String alt4;
    private String img;
    private String correto;
    private boolean escolha;

    public QuestaoView()
    {
    }

    public QuestaoView(String enunciado, String alt1, String alt2, String alt3, String alt4, String correto, String img)
    {
        this.enunciado = enunciado;
        this.alt1 = alt1;
        this.alt2 = alt2;
        this.alt3 = alt3;
        this.alt4 = alt4;
        this.img = img;
        this.correto = correto;

    }
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getAlt1() {
        return alt1;
    }

    public void setAlt1(String alt1) {
        this.alt1 = alt1;
    }

    public String getAlt2() {
        return alt2;
    }

    public void setAlt2(String alt2) {
        this.alt2 = alt2;
    }

    public String getAlt3() {
        return alt3;
    }

    public void setAlt3(String alt3) {
        this.alt3 = alt3;
    }

    public String getAlt4() {
        return alt4;
    }

    public void setAlt4(String alt4) {
        this.alt4 = alt4;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCorreto() {
        return correto;
    }

    public void setCorreto(String correto) {
        this.correto = correto;
    }

    public boolean getEscolha() {
        return escolha;
    }

    public void setEscolha(boolean escolha) {
        this.escolha = escolha;
    }

}
