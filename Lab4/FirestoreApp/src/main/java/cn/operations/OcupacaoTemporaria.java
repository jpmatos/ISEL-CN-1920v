package cn.operations;

public class OcupacaoTemporaria {
    public int ID;
    public Localizacao location;
    public Evento event;

    @Override
    public String toString() {
        return "OcupacaoTemporaria{" +
                "ID=" + ID +
                ", location=" + location +
                ", event=" + event +
                '}';
    }

    public OcupacaoTemporaria() {}

}
