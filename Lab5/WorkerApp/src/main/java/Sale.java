public class Sale {
    public String id;
    public String caixa;
    public String item;
    public double quant;
    public double precoUnit;
    public double total;

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + id + '\'' +
                ", caixa='" + caixa + '\'' +
                ", item='" + item + '\'' +
                ", quant='" + quant + '\'' +
                ", precoUnit='" + precoUnit + '\'' +
                ", total='" + total + '\'' +
                '}';
    }

    public Sale(String id, String caixa, String item, double quant, double precoUnit, double total) {
        this.id = id;
        this.caixa = caixa;
        this.item = item;
        this.quant = quant;
        this.precoUnit = precoUnit;
        this.total = total;
    }
}
