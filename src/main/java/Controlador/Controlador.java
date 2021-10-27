
package Controlador;

import Modelo.Producto;
import Modelo.RepositorioProducto;
import Vista.Actualizar;
import Vista.Principal1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Controlador implements ActionListener {
    
    RepositorioProducto repositorio;
    DefaultTableModel defaultTableModel;
    Principal1 ventanaPrincipal;
    Actualizar ventanaActualizar;
    
    public String nombreActualizar;

    public Controlador() {
        super();
    }

    public Controlador(RepositorioProducto repositorio, Principal1 ventanaPrincipal, Actualizar ventanaActualizar) {
        super();
        this.repositorio = repositorio;
        this.ventanaPrincipal = ventanaPrincipal;
        this.ventanaActualizar = ventanaActualizar;
        ventanaPrincipal.setVisible(true);
        controlTabla();
        agregarEventos();
        
    }
    private void controlTabla(){
        String[] titulos = new String[]{"codigo","Nombre","Precio","Inventario"};
        defaultTableModel = new DefaultTableModel(titulos, 0);
        
        ventanaPrincipal.getTabla().setModel(defaultTableModel);
        
        List<Producto> listaProducto = (List<Producto>) repositorio.findAll();
        
        for(Producto producto: listaProducto){
        defaultTableModel.addRow(new Object[]{producto.getCodigo(),
                                              producto.getNombre(),
                                              producto.getPrecio(),
                                              producto.getInventario()});
        }
        ventanaPrincipal.getTabla().removeColumn(ventanaPrincipal.getTabla().getColumnModel().getColumn(0));   
        
    }
    
     private void agregarEventos() {
        ventanaActualizar.getBtnActualizar2().addActionListener(this);
        ventanaPrincipal.getBtnAgregar().addActionListener(this);
        ventanaPrincipal.getBtnActualizar().addActionListener(this);
        ventanaPrincipal.getBtnBorrar().addActionListener(this);
        ventanaPrincipal.getBtnInforme().addActionListener(this);
        ventanaPrincipal.getTabla().addMouseListener(new MouseAdapter() { 
           public void mouseClicked(MouseEvent evento) {
                llenarCajas(evento);
            }
        });
    }
    private void llenarCajas(MouseEvent evento){
        JTable target=(JTable)evento.getSource();
       nombreActualizar = ventanaPrincipal.getTabla().getModel().getValueAt(target.getSelectedRow(), 0).toString();
        ventanaPrincipal.getTxtNombre().setText(ventanaPrincipal.getTabla().getModel().getValueAt(target.getSelectedRow(), 1).toString());
        ventanaPrincipal.getTxtPrecio().setText(ventanaPrincipal.getTabla().getModel().getValueAt(target.getSelectedRow(), 2).toString());
        ventanaPrincipal.getTxtInventario().setText(ventanaPrincipal.getTabla().getModel().getValueAt(target.getSelectedRow(), 3).toString());
    }
    private void limpiarCampos(){
        
        ventanaPrincipal.getTxtNombre().setText("");
        ventanaPrincipal.getTxtPrecio().setText("");
        ventanaPrincipal.getTxtInventario().setText("");
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource()== ventanaPrincipal.getBtnActualizar()){
            ventanaActualizar.setVisible(true);
            ventanaActualizar.setLocationRelativeTo(ventanaPrincipal);
            
            ventanaActualizar.getTxtNombre2().setText(ventanaPrincipal.getTxtNombre().getText());
            ventanaActualizar.getTxtPrecio2().setText(ventanaPrincipal.getTxtPrecio().getText());
            ventanaActualizar.getTxtInventario2().setText(ventanaPrincipal.getTxtInventario().getText());
        }
        if(evento.getSource()== ventanaActualizar.getBtnActualizar2()){
            actualizar();
            ventanaActualizar.dispose();
        
        }
            if (evento.getSource()==ventanaPrincipal.getBtnAgregar()){
            agregar();
        }
        
        
        if (evento.getSource()==ventanaPrincipal.getBtnBorrar()){
            borrar();
        }
         if (evento.getSource()==ventanaPrincipal.getBtnInforme()){
            generarInforme();
        }
        
        
        
    }
    
    
    public void agregar(){
           Producto producto = new Producto(
                                         ventanaPrincipal.getTxtNombre().getText(),
                                         Double.parseDouble(ventanaPrincipal.getTxtPrecio().getText()),
                                         Integer.parseInt(ventanaPrincipal.getTxtInventario().getText()));
        repositorio.save(producto);
        limpiarCampos();
        controlTabla();  
    
    }
    public void borrar(){
        
         Producto producto = new Producto(Integer.parseInt(nombreActualizar),
                                         ventanaPrincipal.getTxtNombre().getText(),
                                         Double.parseDouble(ventanaPrincipal.getTxtPrecio().getText()),
                                         Integer.parseInt(ventanaPrincipal.getTxtInventario().getText()));
        repositorio.delete(producto);
        limpiarCampos();
        controlTabla();  
    
    }
    public void actualizar(){
        
        Producto producto = new Producto(Integer.parseInt(nombreActualizar),
                                         ventanaActualizar.getTxtNombre2().getText(),
                                         Double.parseDouble(ventanaActualizar.getTxtPrecio2().getText()),
                                         Integer.parseInt(ventanaActualizar.getTxtInventario2().getText()));
        repositorio.save(producto);
        limpiarCampos();
        controlTabla();   
    
    }
    public void generarInforme(){
        
          ArrayList<Producto> inventario=new ArrayList<Producto>();
        for (Producto elemento:repositorio.findAll()){
            inventario.add(elemento);
        }
        for(Producto producto: inventario){
            System.out.println(producto.getCodigo()+" "+ producto.getPrecio()+" "+producto.getNombre()+" "+producto.getInventario());
        }
        String nombrePrecioMayor = inventario.iterator().next().getNombre();
        double precio = inventario.iterator().next().getPrecio();
        for (Producto producto: inventario) {
            if (producto.getPrecio() > precio){
                nombrePrecioMayor = producto.getNombre();
                precio = producto.getPrecio();
            }
        }
        
        String nombrePrecioMenor = inventario.iterator().next().getNombre();
        double precioMenor = inventario.iterator().next().getPrecio();
        for (Producto producto: inventario) {
            if (producto.getPrecio() < precioMenor){
                nombrePrecioMenor = producto.getNombre();
                precioMenor = producto.getPrecio();
            }
        }
       
        
        double promedio = 0;
        for (Producto producto: inventario) {
            promedio += producto.getPrecio();       
        }
        promedio /= inventario.size() ;
        
        double totalInventario = 0;
        for (Producto producto: inventario) {
            totalInventario += producto.getPrecio() * producto.getInventario();       
        }
        
         JOptionPane.showMessageDialog(ventanaPrincipal,"\n "+
                                                        "Producto Precio Mayor: "  +
                                                        nombrePrecioMayor+
                                                        "\n "+ 
                                                        "\nProducto Precio Menor: " +
                                                        nombrePrecioMenor+
                                                        "\n "+ 
                                                        "\nPromedio Precio: " +
                                                        promedio + 
                                                        "\n "+ 
                                                        "\nValor del Inventario: "+
                                                        totalInventario); 
    
    }
    
    
}
