package cn.operations;

import com.google.cloud.firestore.GeoPoint;

public class Localizacao {
  public GeoPoint point;
  public Coordenadas coord;
  public String freguesia;
  public String local;

  @Override
  public String toString() {
    return "Localizacao{" +
            "point=" + point +
            ", coord=" + coord +
            ", freguesia='" + freguesia + '\'' +
            ", local='" + local + '\'' +
            '}';
  }

  public Localizacao() {}
}
