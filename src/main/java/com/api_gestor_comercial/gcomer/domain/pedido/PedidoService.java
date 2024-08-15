package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.cliente.ClienteRepository;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;
import com.api_gestor_comercial.gcomer.domain.producto.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private ProductoRepository productoRepository;


    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository
            productoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;

    }

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Page<Pedido> findByEnviadoFalse(Pageable pageable){
        return pedidoRepository.findByEnviadoFalse(pageable);
    }

    public Page<Pedido> findByEnviadoTrue(Pageable pageable){
        return pedidoRepository.findByEnviadoTrue(pageable);
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

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido update(DatosActualizarPedido datosActualizarPedido) {
        Pedido pedido = pedidoRepository.findById(datosActualizarPedido.id())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
        pedido.actualizarPedido(datosActualizarPedido);
        return pedido;
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

    public double calculaPrecioTotal(Map<Producto, Integer> productos) {
        return productos.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrecio() * entry.getValue())
                .sum();
    }

    public Pedido actualizarPedido(Long pedidoId, DatosActualizarPedido datosActualizarPedido) {
    Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

    // Actualizar cliente si es necesario
    if (datosActualizarPedido.cliente() != null) {
        Cliente nuevoCliente = clienteRepository.findById(datosActualizarPedido.cliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        pedido.setCliente(nuevoCliente);
    }

    // Actualizar productos si es necesario
    if (datosActualizarPedido.productos() != null) {
        // Crear un nuevo Map para evitar modificar el original directamente
        Map<Producto, Integer> nuevosProductos = new HashMap<>();

        // Iterar sobre los productos del DTO y actualizar o agregar
        for (Map.Entry<Producto, Integer> entry : datosActualizarPedido.productos().entrySet()) {
            Producto producto = productoRepository.findById(entry.getKey().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
            nuevosProductos.put(producto, entry.getValue());
        }

        // Reemplazar el Map de productos del pedido
        pedido.setProductos(nuevosProductos);
    }

    // Recalcular el precio total
    pedido.setPrecioTotal(calculaPrecioTotal(pedido.getProductos()));

    // Persistir los cambios
    return pedidoRepository.save(pedido);
}

    public Pedido crearPedido(DatosPedido datosPedido) {
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setFechaDePedido(LocalDate.now());

        Cliente cliente = clienteRepository.findById(datosPedido.cliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        nuevoPedido.setCliente(cliente);

        // Crear un Map de productos a partir de los IDs y cantidades
        Map<Producto, Integer> productos = datosPedido.productos()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> productoRepository.findById(entry.getKey().getId()).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado")),
                        Map.Entry::getValue
                ));
        nuevoPedido.setProductos(productos);
        return pedidoRepository.save(nuevoPedido);
    }
}
