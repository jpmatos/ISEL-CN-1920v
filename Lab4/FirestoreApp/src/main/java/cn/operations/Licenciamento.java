package cn.operations;

import java.util.Date;

public class Licenciamento {
    public String code;
    public Date dtLicenc;

    @Override
    public String toString() {
        return "Licenciamento{" +
                "code='" + code + '\'' +
                ", dtLicenc=" + dtLicenc +
                '}';
    }

    public Licenciamento() {}
}
