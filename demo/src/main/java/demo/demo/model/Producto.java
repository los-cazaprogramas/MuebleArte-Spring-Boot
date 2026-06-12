package demo.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private int id;

    @Column(name = "codigo_producto", length = 20)
    private String codigoProducto;

    @Column(name = "nombre_producto", length = 100, nullable = false)
    private String nombreProducto;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "stock")
    private int stock;

    @Column(name = "descripcion_producto", columnDefinition = "TEXT")
    private String descripcionProducto;

    @Column(name = "detalles_producto", columnDefinition = "TEXT")
    private String detallesProducto;

    @Column(name = "alto_cm", precision = 5, scale = 2)
    private BigDecimal altoCm;

    @Column(name = "ancho_cm", precision = 5, scale = 2)
    private BigDecimal anchoCm;

    @Column(name = "profundidad_cm", precision = 5, scale = 2)
    private BigDecimal profundidadCm;

    @Column(name = "peso_kg", precision = 5, scale = 2)
    private BigDecimal pesoKg;

    @Column(name = "informacion_adicional", columnDefinition = "TEXT")
    private String informacionAdicional;

    @Column(name = "imagen_url", columnDefinition = "TEXT")
    private String imagenUrl;

    // ==========================================
    // RELACIONES REALES (ESTRATEGIA EFICIENTE)
    // ==========================================
    
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_material")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "id_color")
    private Color color;

    // Constructor vacío obligatorio para JPA
    public Producto() {}

    // Getters y Setters completos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getDescripcionProducto() { return descripcionProducto; }
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }
    public String getDetallesProducto() { return detallesProducto; }
    public void setDetallesProducto(String detallesProducto) { this.detallesProducto = detallesProducto; }
    public BigDecimal getAltoCm() { return altoCm; }
    public void setAltoCm(BigDecimal altoCm) { this.altoCm = altoCm; }
    public BigDecimal getAnchoCm() { return anchoCm; }
    public void setAnchoCm(BigDecimal anchoCm) { this.anchoCm = anchoCm; }
    public BigDecimal getProfundidadCm() { return profundidadCm; }
    public void setProfundidadCm(BigDecimal profundidadCm) { this.profundidadCm = profundidadCm; }
    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }
    public String getInformacionAdicional() { return informacionAdicional; }
    public void setInformacionAdicional(String informacionAdicional) { this.informacionAdicional = informacionAdicional; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
}