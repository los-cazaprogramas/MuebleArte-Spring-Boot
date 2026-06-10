package demo.demo.model;
import jakarta.persistence.*;

@Entity
@Table(name="materiales")
public class Material{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_material")
    private int idMaterial;
    @Column(name = "nombre_material", length = 50, nullable = false)
    private String nombre;
    public Material(){};
    public int getIdMaterial(){return idMaterial;}
    public void setIdMaterial(int idMaterial){this.idMaterial=idMaterial;}
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
}