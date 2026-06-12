package demo.demo.controller;

import demo.demo.model.Categoria;
import demo.demo.model.Color;
import demo.demo.model.Material;
import demo.demo.model.Producto;
import demo.demo.repository.CategoriaRepository;
import demo.demo.repository.ColorRepository;
import demo.demo.repository.MaterialRepository;
import demo.demo.repository.ProductoRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos") 
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;

    private static final String UPLOAD_DIR = "uploads";

    public ProductoController(ProductoRepository productoRepository,
                              CategoriaRepository categoriaRepository,
                              ColorRepository colorRepository,
                              MaterialRepository materialRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.colorRepository = colorRepository;
        this.materialRepository = materialRepository;
    }

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable int id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Producto crear(@RequestBody @NonNull Producto producto) {
        return productoRepository.save(producto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> crearConImagen(
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam("stock") int stock,
            @RequestParam("codigoProducto") String codigoProducto,
            @RequestParam(value = "descripcionProducto", required = false) String descripcionProducto,
            @RequestParam(value = "detallesProducto", required = false) String detallesProducto,
            @RequestParam(value = "altoCm", required = false) BigDecimal altoCm,
            @RequestParam(value = "anchoCm", required = false) BigDecimal anchoCm,
            @RequestParam(value = "profundidadCm", required = false) BigDecimal profundidadCm,
            @RequestParam(value = "pesoKg", required = false) BigDecimal pesoKg,
            @RequestParam(value = "informacionAdicional", required = false) String informacionAdicional,
            @RequestParam("categoria.idCategoria") int categoriaId,
            @RequestParam(value = "color.idColor", defaultValue = "1") int colorId,
            @RequestParam(value = "material.idMaterial", defaultValue = "1") int materialId,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        Producto producto = new Producto();
        producto.setNombreProducto(nombreProducto);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCodigoProducto(codigoProducto);
        producto.setDescripcionProducto(descripcionProducto);
        producto.setDetallesProducto(detallesProducto);
        producto.setAltoCm(altoCm);
        producto.setAnchoCm(anchoCm);
        producto.setProfundidadCm(profundidadCm);
        producto.setPesoKg(pesoKg);
        producto.setInformacionAdicional(informacionAdicional);

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada: " + categoriaId));
        producto.setCategoria(categoria);

        Color color = colorRepository.findById(colorId)
                .orElseThrow(() -> new RuntimeException("Color no encontrado: " + colorId));
        producto.setColor(color);

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material no encontrado: " + materialId));
        producto.setMaterial(material);

        if (imagen != null && !imagen.isEmpty()) {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String fileName = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.copy(imagen.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            producto.setImagenUrl("/uploads/" + fileName);
        }

        Producto guardado = productoRepository.save(producto);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable int id, @RequestBody Producto datosActualizados) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    productoExistente.setCodigoProducto(datosActualizados.getCodigoProducto());
                    productoExistente.setNombreProducto(datosActualizados.getNombreProducto());
                    productoExistente.setPrecio(datosActualizados.getPrecio());
                    productoExistente.setStock(datosActualizados.getStock());
                    productoExistente.setDescripcionProducto(datosActualizados.getDescripcionProducto());
                    productoExistente.setDetallesProducto(datosActualizados.getDetallesProducto());
                    productoExistente.setAltoCm(datosActualizados.getAltoCm());
                    productoExistente.setAnchoCm(datosActualizados.getAnchoCm());
                    productoExistente.setProfundidadCm(datosActualizados.getProfundidadCm());
                    productoExistente.setPesoKg(datosActualizados.getPesoKg());
                    productoExistente.setInformacionAdicional(datosActualizados.getInformacionAdicional());
                    if (datosActualizados.getImagenUrl() != null)
                        productoExistente.setImagenUrl(datosActualizados.getImagenUrl());

                    productoExistente.setCategoria(datosActualizados.getCategoria());
                    productoExistente.setMaterial(datosActualizados.getMaterial());
                    productoExistente.setColor(datosActualizados.getColor());

                    Producto guardado = productoRepository.save(productoExistente);
                    return ResponseEntity.ok(guardado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}