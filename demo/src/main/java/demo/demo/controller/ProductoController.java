package demo.demo.controller;

import demo.demo.model.Producto;
import demo.demo.repository.ProductoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import java.util.List;

@RestController
@RequestMapping("/api/productos") 
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoRepository productoRepository;

    // Inyección de dependencias por constructor
    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // 1. OBTENER TODOS LOS PRODUCTOS (GET)
    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    // 2. OBTENER UN PRODUCTO POR ID (GET /{id})
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable int id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR UN NUEVO PRODUCTO (POST)
    @PostMapping
    public Producto crear(@RequestBody @NonNull Producto producto) {
        return productoRepository.save(producto);
    }

    // 4. ACTUALIZAR UN PRODUCTO EXISTENTE (PUT /{id})
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable int id, @RequestBody Producto datosActualizados) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    // Mapeamos los campos del JSON físico al registro real de la BD
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
                    
                    // Actualizamos las relaciones puras
                    productoExistente.setCategoria(datosActualizados.getCategoria());
                    productoExistente.setMaterial(datosActualizados.getMaterial());
                    productoExistente.setColor(datosActualizados.getColor());

                    Producto guardado = productoRepository.save(productoExistente);
                    return ResponseEntity.ok(guardado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5. ELIMINAR UN PRODUCTO (DELETE /{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }
}