package demo.demo.model;
import jakarta.persistence.*;
@Entity
@Table(name="colores")
public class Color{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_color")
    private int idColor;
    @Column(name = "nombre_color", length = 50, nullable = false)
    private String nombre;
    public Color(){}
    public int getIdColor(){return idColor;}
    public void setIdColor(int idColor){this.idColor=idColor;}
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
}