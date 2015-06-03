package Concesionario.GUI;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import concesionarioCoches.*;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JLabel;

public class Principal {

	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu mnArchivo;
	private JMenu mnCoche;
	private JMenu mnBuscar;
	private JMenu mnAyuda;
	private Concesionario concesionario = new Concesionario();
	private Alta alta;
	private VentanaPadre baja;
	private Mostrar mostrar;
	private ElegirColor elegirColor;
	private MostrarPorMatricula mostrarPorMatricula;
	protected static final Component parent = null;
	private About about;
	private File file = new File("SinTitulo.obj");
	FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos.obj",  "obj");
	private boolean modificado;
	private boolean guardado;
	private int opcion;
	private int respuesta;
	private final String message = "Error, no se ha podido hacer la operación con el archivo";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(file.getName());
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnArchivo = new JMenu("Archivo");
		mnArchivo.setMnemonic('A');
		menuBar.add(mnArchivo);
		
		JMenuItem mntmNuevoConcesionario = new JMenuItem("Nuevo");
		mntmNuevoConcesionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(modificado == true){
					int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios antes de salir?",
							"¿Deseas salir?", JOptionPane.YES_NO_CANCEL_OPTION);
					if(respuesta == JOptionPane.NO_OPTION){
						concesionario = new Concesionario();
					}
					if(respuesta == JOptionPane.YES_OPTION){
						if (guardado == true){
							guardar();
							setGuardado(true);
						}
						else
							guardarComo();
						concesionario = new Concesionario();
					}
				}else
					concesionario = new Concesionario();
			}
		});
		mntmNuevoConcesionario.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnArchivo.add(mntmNuevoConcesionario);
		
		JMenuItem mntmAbrirConcesionario = new JMenuItem("Abrir");
		mntmAbrirConcesionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modificado == true){
					int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios antes de salir?",
							"¿Deseas salir?", JOptionPane.YES_NO_CANCEL_OPTION);
					if(respuesta == JOptionPane.NO_OPTION){
						abrir();
					}
					if(respuesta == JOptionPane.YES_OPTION){
						if (guardado == true){
							guardar();
							setGuardado(true);
						}
						else
							guardarComo();
						abrir();
					}
				}else
					abrir();
			}
		});
		mntmAbrirConcesionario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnArchivo.add(mntmAbrirConcesionario);
		
		mnArchivo.addSeparator();
		
		JMenuItem mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(guardado == false){
						guardarComo();
						setModificado(false);
					}else
						guardar();
				}catch(Exception e1){
					e1.printStackTrace();
					JOptionPane.showInternalMessageDialog(parent, message);
				}
			}
		});
		mntmGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnArchivo.add(mntmGuardar);
		
		JMenuItem mntmGuardarComo = new JMenuItem("Guardar como...");
		mntmGuardarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarComo();
				setModificado(false);
				setGuardado(true);
			}
		});
		mntmGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnArchivo.add(mntmGuardarComo);
		
		mnArchivo.addSeparator();
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(modificado == true){
					int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios antes de salir?",
							"¿Deseas salir?", JOptionPane.YES_NO_CANCEL_OPTION);
					if(respuesta == JOptionPane.NO_OPTION){
						System.exit(0);
					}
					if(respuesta == JOptionPane.YES_OPTION){
						if (guardado == true){
							guardar();
							setGuardado(true);
						}
						else
							guardarComo();
						System.exit(0);
					}
				}else
					System.exit(0);
			}
		});
		mnArchivo.add(mntmSalir);
		
		mnCoche = new JMenu("Coche");
		mnCoche.setMnemonic('C');
		menuBar.add(mnCoche);
		
		JMenuItem mntmAlta = new JMenuItem("Alta");
		mntmAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alta();
				setModificado(true);
			}
		});
		mntmAlta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnCoche.add(mntmAlta);
		
		JMenuItem mntmBaja = new JMenuItem("Baja");
		mntmBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				baja();
				setModificado(true);
			}
		});
		mntmBaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		mnCoche.add(mntmBaja);
		
		mnCoche.addSeparator();
		
		JMenuItem mntmMostrarConcesionario = new JMenuItem("Mostrar concesionario");
		mntmMostrarConcesionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrar();
			}
		});
		mnCoche.add(mntmMostrarConcesionario);
		
		mnBuscar = new JMenu("Buscar");
		mnBuscar.setMnemonic('B');
		menuBar.add(mnBuscar);
		
		JMenuItem mntmCochePorMatricula = new JMenuItem("Por matr\u00EDcula...");
		mntmCochePorMatricula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPorMatricula();
			}
		});
		mnBuscar.add(mntmCochePorMatricula);
		
		JMenuItem mntmCochePorColor = new JMenuItem("Por color...");
		mntmCochePorColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPorColor();
			}
		});
		mnBuscar.add(mntmCochePorColor);
		
		mnAyuda = new JMenu("Ayuda");
		mnAyuda.setMnemonic('A');
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAyuda = new JMenuItem("Ayuda");
		mnAyuda.add(mntmAyuda);
		
		JMenuItem mntmSobreConcesionario = new JMenuItem("Sobre concesionario");
		mntmSobreConcesionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about = new About();
				about.setVisible(true);
			}
		});
		mnAyuda.add(mntmSobreConcesionario);
		
		frame.getContentPane().setLayout(null);
		
		ImageIcon prin = new ImageIcon("src/img/principal.png");
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(prin);
		lblNewLabel.setBounds(10, 11, 424, 232);
		frame.getContentPane().add(lblNewLabel);
	}
	
	private void setGuardado(boolean b) {
		this.guardado = b;
	}


	private void alta() {
		alta = new Alta(concesionario);
		alta.setVisible(true);
	}
	
	private void baja() {		
		baja = new Baja(concesionario);
		baja.setVisible(true);
	}
	
	private void mostrar() {
		if (concesionario.size() == 0) {
			JOptionPane.showMessageDialog(frame.getContentPane(),
					"No hay coches en el concesionario.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		mostrar = new Mostrar(concesionario);
		mostrar.setVisible(true);
	}

	private void mostrarPorColor() {
		if (concesionario.size() == 0) {
			JOptionPane.showMessageDialog(frame.getContentPane(),
					"No hay coches en el concesionario.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		elegirColor = new ElegirColor(concesionario);
		elegirColor.setVisible(true);
	}
	
	private void mostrarPorMatricula() {
		if (concesionario.size() == 0) {
			JOptionPane.showMessageDialog(frame.getContentPane(),
					"No hay coches en el concesionario.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		mostrarPorMatricula = new MostrarPorMatricula(concesionario);
		mostrarPorMatricula.setVisible(true);
	}
	
	private void setModificado(boolean b){
		this.modificado=b;
	}

	private void guardar() {
		try{
			Fichero.guardar(file, concesionario);
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		setModificado(false);
		setGuardado(true);
	}

	private void guardarComo() {
		file=null;
		JFileChooser guardar = new JFileChooser();
		guardar.setFileFilter(filtro);
		int opcion = guardar.showSaveDialog(frame);
		if(opcion==JFileChooser.APPROVE_OPTION){
			file = guardar.getSelectedFile();
			try{
				Fichero.guardarComo(file, concesionario);
			}catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		setModificado(false);
		setGuardado(true);
	}

	private void abrir() {
		JFileChooser abrir = new JFileChooser();
		abrir.setFileFilter(filtro);
		int opcion = abrir.showOpenDialog(frame);
		if(opcion==JFileChooser.APPROVE_OPTION){
			file = abrir.getSelectedFile();
			try{
				concesionario = (Concesionario) Fichero.leer(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1){
				e1.printStackTrace();
			}catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		frame.setTitle(file.getName());
	}
	
}