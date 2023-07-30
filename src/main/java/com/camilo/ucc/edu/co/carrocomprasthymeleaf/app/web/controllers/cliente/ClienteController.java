package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.web.controllers.cliente;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.commons.utils.paginator.PageRender;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Cliente;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.cliente.IClienteService;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.uploadservice.IUploadService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

@Controller
@SessionAttributes("cliente")
@Slf4j
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadService uploadService;

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename){

        Resource recurso = null;

        try {
            recurso = uploadService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""+recurso.getFilename()
        +"\"").body(recurso);
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash){
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            flash.addFlashAttribute("error","No existe en la base de datos");
            return  "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Detalle cliente: ".concat(cliente.getNombre()));
        return "ver";
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 4);

        Page<Cliente> clientes = clienteService.findAll(pageRequest);
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clientes);
        model.addAttribute("page",pageRender);
        return "listar";
    }

    @RequestMapping(value = "/form")
    public String crear(Map<String, Object> model){
        Cliente cliente = new Cliente();
        model.put("cliente",cliente);
        model.put("titulo","Formulario Cliente");
        return "form";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        Cliente cliente =null;
        if (id > 0) {
             cliente = clienteService.findById(id);
            if (cliente == null) {
                flash.addFlashAttribute("error","No existe en la base de datos");
                return  "redirect:/listar";
            }
        }
            else {
                flash.addFlashAttribute("error","El cliente no puede ser eliminado de la base de datos");
                return  "redirect:/listar";
            }
        model.put("cliente",cliente);
        model.put("titulo","Editar Cliente");
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model
    , @RequestParam(name = "file")MultipartFile foto, RedirectAttributes flash, SessionStatus status){
        if (result.hasErrors()) {
            model.addAttribute("titulo","Formulario Cliente");
            return "form";
        }
        if (!foto.isEmpty()) {

            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
            && cliente.getFoto().length() > 0 ) {
                uploadService.delete(cliente.getFoto());
            }

            String uniqueFile = null;

            try {
                uniqueFile = uploadService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info","Has subidoo correctamente '" +
                    uniqueFile +"'");
            cliente.setFoto(uniqueFile);

        }
        String mensajeFlash =(cliente.getId() != null) ? "Cliente editado con exito! " :
                "Cliente creado con exito";

        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success",mensajeFlash);
        return  "redirect:/listar";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){

        if (id > 0) {
            Cliente cliente = clienteService.findById(id);

            clienteService.delete(id);
            flash.addFlashAttribute("success","Cliente eliminado con exito!!");
            if (uploadService.delete(cliente.getFoto()) ) {
                flash.addFlashAttribute("info","Foto " + cliente.getFoto() +" eliminada con exito!");
            }
        }
        return  "redirect:/listar";
    }
}
