package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.cliente.ClienteRepository;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;
import com.api_gestor_comercial.gcomer.domain.producto.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;


    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository
            productoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;

    }

    public Page<Pedido> findAllByDay(Pageable pageable) {
        LocalDate today = LocalDate.now();
        return pedidoRepository.findAllByFechaDePedido(today, pageable);
    }

    public Page<Pedido> findByEnviadoDia(Pageable pageable){
        LocalDate today = LocalDate.now();
        return pedidoRepository.findByEnviadoTrueAndFechaDePedido(today, pageable);
    }
    public Page<Pedido> findByNoEnviadoDia(Pageable pageable){
        LocalDate today = LocalDate.now();
        return pedidoRepository.findByEnviadoFalseAndFechaDePedido(today, pageable);
    }

    public Page<Pedido> findEnviadosByMonth(int year, int month, Pageable pageable) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return pedidoRepository.findByEnviadoTrueAndFechaDePedidoBetween(startDate, endDate, pageable);
    }

    public Page<Pedido> findEnviadosByYear(int year, Pageable pageable) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return pedidoRepository.findByEnviadoTrueAndFechaDePedidoBetween(startDate, endDate, pageable);
    }

    public Page<Pedido> findByFormaDePagoByDay(String formaDePago, Pageable pageable){
        return pedidoRepository.findByFormaDePagoAndFechaDePedido(formaDePago, LocalDate.now(), pageable);
    }

    @Transactional
    public boolean delete(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }

    @Transactional
    public Pedido pedidoUpdate(DatosActualizarPedido datosActualizarPedido) {
    Pedido pedido = pedidoRepository.findById(datosActualizarPedido.id())
            .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

    if (datosActualizarPedido.clienteId() != null) {
        Cliente nuevoCliente = clienteRepository.findById(datosActualizarPedido.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        pedido.setCliente(nuevoCliente);
    }

    if (datosActualizarPedido.productos() != null) {

        Map<Producto, Integer> nuevosProductos = new HashMap<>();
        double precioTotal = 0;
        for (Map.Entry<Long, Integer> entry : datosActualizarPedido.productos().entrySet()) {
            Producto producto = productoRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + entry.getKey()));
            nuevosProductos.put(producto, entry.getValue());
            precioTotal += producto.getPrecio() * entry.getValue();
        }

        pedido.setProductos(nuevosProductos);
        pedido.setFormaDePago(datosActualizarPedido.formaDePago());
        pedido.setPrecioTotal(precioTotal);
    }

    if(datosActualizarPedido.enviado()){
        pedido.setEnviado(true);
    }

    return pedidoRepository.save(pedido);
}

    @Transactional
    public Pedido crearPedido(CrearPedido crearPedido) {
        Cliente cliente = clienteRepository.findById(crearPedido.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Map<Producto, Integer> productosMap = new HashMap<>();
        double precioTotal = 0;

        for (Map.Entry<Long, Integer> entry : crearPedido.productos().entrySet()) {
            Producto producto = productoRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + entry.getKey()));
            productosMap.put(producto, entry.getValue());
            precioTotal += producto.getPrecio() * entry.getValue();
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProductos(productosMap);
        pedido.setPrecioTotal(precioTotal);
        pedido.setFormaDePago(crearPedido.formaDePago());

        return pedidoRepository.save(pedido);
    }
}
