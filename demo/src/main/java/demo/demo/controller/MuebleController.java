package demo.demo.controller;

import demo.demo.model.Mueble;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/muebles")
@CrossOrigin(origins = "*") 
public class MuebleController {
    private final List<Mueble> inventario = new ArrayList<>();
    public MuebleController() {
        // Mocks iniciales idénticos a los de tu frontend
        inventario.add(new Mueble("prod_1", "Silla Nordica de Roble", "sillas", 2450.00, 
                "Madera de roble con acabado mate.", "https://images.unsplash.com/photo-1567538096630-e0c55bd6374c?w=500"));
        inventario.add(new Mueble("prod_2", "Mesa de Comedor Parota", "mesas", 14900.00, 
                "Mesa artesanal de madera maciza.", "https://images.unsplash.com/photo-1577140917170-285929fb55b7?w=500"));
    }
    @GetMapping
    public List<Mueble> obtenerTodos() {
        return inventario;
    }
    @PostMapping
    public Mueble crear(@RequestBody Mueble nuevoMueble) {
        String nuevoId = "prod_" + (inventario.size() + 1);
        nuevoMueble.setId(nuevoId);
        inventario.add(nuevoMueble);
        return nuevoMueble;
    }
}

