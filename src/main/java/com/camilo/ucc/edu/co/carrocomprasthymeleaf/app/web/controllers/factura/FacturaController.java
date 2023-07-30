package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.web.controllers.factura;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Cliente;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Factura;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.ItemFactura;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Producto;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.cliente.IClienteService;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.factura.IFacturaService;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.producto.IProductoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
@Slf4j
public class FacturaController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IFacturaService facturaService;

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value="id") Long id,  Model model,
                      RedirectAttributes flash) {

        Factura factura = facturaService.findFacturaById(id);

        if (factura == null) {
            flash.addFlashAttribute("error", "La factura no existe en la base de datos!");
            return "redirect:/listar";
        }

        model.addAttribute("factura", factura);
        model.addAttribute("listado", "Listado de ITEMS");
        model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));

        return "factura/ver";
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {

        List<Producto> productos = productoService.buscarPorProducto(term);
        log.info(productos.toString());
        return  productos;
    }

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model, RedirectAttributes flash) {

        Cliente cliente = clienteService.findById(clienteId);

        if (cliente == null) {
            flash.addFlashAttribute("error", "No existe en la base de datos");
            return "redirect:/listar";
        }
        String consulta= "Pana";
        List<Producto> productos = productoService.buscarPorProducto(consulta);
        log.info(productos.toString());
        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Crear factura");

        return "factura/form";

    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura, BindingResult result, Model model,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Crear Factura");
            return "factura/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", "Crear Factura");
            model.addAttribute("error", "Error: La factura NO puede no tener líneas!");
            return "factura/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Producto producto = productoService.findProductoById(itemId[i]);

            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.addItemFactura(linea);

            log.info("Id item: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
        }

        facturaService.saveFactura(factura);
        status.setComplete();

        flash.addFlashAttribute("success", "Factura creada con éxito!");


        return "redirect:/ver/" + factura.getCliente().getId();

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
        Factura factura = facturaService.findFacturaById(id);

        if (factura != null) {
            facturaService.deleteFactura(id);
            flash.addFlashAttribute("success", "Factura eliminada con éxito!");
            return "redirect:/ver/" + factura.getCliente().getId();
        }

        flash.addFlashAttribute("error", "La factura no existe en la base de datos, no se pudo eliminar!");
        return "redirect:/listar";
    }

}
