package demo.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private int idCategoria;
    @Column(name = "nombre_categoria", length = 50, nullable = false)
    private String nombre;
    //constructores
    public Categoria(){}
    public int getIdCategoria(){return idCategoria;}
    public void setIdCategoria(int idCategoria){this.idCategoria=idCategoria;}
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
}