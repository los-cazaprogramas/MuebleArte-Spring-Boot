package demo.demo.controller;

import demo.demo.model.Mueble;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/muebles")
@CrossOrigin(origins = "*") //Mantenemos compativilidad local
public class MuebleController {
    private final List<Mueble> inventario = new ArrayList<>();
    public MuebleController() {
        // Mocks iniciales idénticos a los de tu frontend
        inventario.add(new Mueble("prod_1", "Silla Nordica de Roble", "sillas", 2450.00, 
                "Madera de roble con acabado mate.", "https://images.unsplash.com/photo-1567538096630-e0c55bd6374c?w=500"));
        inventario.add(new Mueble("prod_2", "Mesa de Comedor Parota", "mesas", 14900.00, 
                "Mesa artesanal de madera maciza.", "https://images.unsplash.com/photo-1577140917170-285929fb55b7?w=500"));
    }
    //GET
    @GetMapping
    public List<Mueble> obtenerTodos() {
        return inventario;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Mueble> obtenerPorId(@PathVariable String id){
        Optional<Mueble> muebleOpt=inventario.stream().filter(m->m.getId().equals(id)).findFirst();
        return muebleOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    //POST
    @PostMapping
    public Mueble crear(@RequestBody Mueble nuevoMueble) {
        String nuevoId = "prod_" + (inventario.size() + 1);
        nuevoMueble.setId(nuevoId);
        inventario.add(nuevoMueble);
        return nuevoMueble;
    }
    //PUT (actualizar esta madre)
    @PutMapping("/{id}")
    public ResponseEntity<Mueble> actualizar(@PathVariable String id, @RequestBody Mueble muebleActualizado) {
        for(int i=0;i<inventario.size();i++){
            Mueble m=inventario.get(i);
            if(m.getId().equals(id)){
                muebleActualizado.setId(id);
                inventario.set(i,muebleActualizado);
                return ResponseEntity.ok(muebleActualizado);
            }
        }
        return ResponseEntity.notFound().build();
    }
    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id){
        boolean removido=inventario.removeIf(m->m.getId().equals(id));
        if(removido){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}

