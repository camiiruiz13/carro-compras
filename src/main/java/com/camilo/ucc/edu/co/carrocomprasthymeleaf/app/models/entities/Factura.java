package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "factura")
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String descripcion;

    private String observacion;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM--dd")
    @Column( name = "fechacreacion")
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idfactura")
    private List<ItemFactura> items;

    public Factura() {
        this.items = new ArrayList<>();
    }

    public void addItemFactura(ItemFactura item){
        items.add(item);
    }

    @PrePersist
    public void prePersist(){
        fechaCreacion = new Date();
    }

    public Double getTotal(){
        Double total = 0.0;

        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).calcularImporte();
        }

        return  total;
    }
}