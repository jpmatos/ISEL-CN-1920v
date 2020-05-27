package cn.operations;

import java.util.Date;
import java.util.Map;

public class Evento {
    public int evtID;
    public String nome;
    public String tipo;
    public Date dtInicio;
    public Date dtFinal;
    public Licenciamento licenciamento;
    public Map<String, String>  details;

    @Override
    public String toString() {
        return "Evento{" +
                "evtID=" + evtID +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", dtInicio=" + dtInicio +
                ", dtFinal=" + dtFinal +
                ", licenciamento=" + licenciamento +
                ", details=" + details +
                '}';
    }

    public Evento() {}
}
