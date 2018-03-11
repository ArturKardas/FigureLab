/*
 * Autor:Artur KArdas nr.Albumu:226148
 * Data:25.11.2017r.
 * Klasy: FigureApp
 * Klasa s�u��ca do zdefiniowania interfejsu graficznego
 * 
 * */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;




public class FigureApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String infoAutor ="Autor: Artur Kardas\nNr. Albumu: 226148";
	
	
	private final String DESCRIPTION = "OPIS PROGRAMU\n\n" + "Aktywna klawisze:\n"
			+ "   strzalki ==> przesuwanie figur\n"
			+ "   SHIFT + strzalki ==> szybkie przesuwanie figur\n"
			+ "   +,-  ==> powiekszanie, pomniejszanie\n"
			+ "   DEL  ==> kasowanie figur\n"
			+ "   p  ==> dodanie nowego punktu\n"
			+ "   c  ==> dodanie nowego kola\n"
			+ "   t  ==> dodanie nowego trojkata\n"
			+ "	  r  ==> dodanie nowego rombu\n"
			+ "	  w  ==> dodanie nowego wiatraka\n"
			+ "\nOperacje myszka:\n" + "   klik ==> zaznaczanie figur\n"
			+ "   ALT + klik ==> zmiana zaznaczenia figur\n"
			+ "   przeciaganie ==> przesuwanie figur";

	
	protected FigurePanel panel;
	
	//deklaracja menu bar
	JMenuBar menu=new JMenuBar();
	
	//pozycje do menu
	private JMenu FigureMenu= new JMenu("Figury");
	private JMenu EditMenu= new JMenu("Edytuj");
	private JMenu HelpMenu= new JMenu("Pomoc");
	
	//opcje do menu Figury
	
	private JMenuItem pointItem= new JMenuItem("Punkt");
	private JMenuItem circleItem= new JMenuItem("Koło");
	private JMenuItem triangleItem= new JMenuItem("Trójkąt");
	private JMenuItem diamondItem= new JMenuItem("Romb");
	private JMenuItem windMillItem= new JMenuItem("Wiatrak");
	private JMenuItem displayAllItem= new JMenuItem("Pokaż wszystkie");

	//opcje do menu Edit
	private JMenuItem [] editItems= {new JMenuItem("Powiększ"),
									 new JMenuItem("Pomniejsz"),
									 new JMenuItem("Przesuń w góre"),
									 new JMenuItem("Przesuń w dół"),
									 new JMenuItem("Przesuń w lewo"),
									 new JMenuItem("Przesń w prawo")};
	//opcje do menu Help
	private JMenuItem [] helpItems= {new JMenuItem("Informacje o autorze"),new JMenuItem("Pomoc do programu")};
	
	//przyciski do tworzenia nowych figur
	JButton pointButton=new JButton("Punkt");
	JButton circleButton=new JButton("Koło");
	JButton triangleButton=new JButton("Trójkąt");
	JButton diamondButton=new JButton("Romb");
	JButton windMillButton=new JButton("Wiatrak");
	
	
	/*no dzień dobry */
	
	
	
	public static void main(String[] args) {
		new FigureApp();
	}
	
	public FigureApp() {
		setSize(400,400);
		setTitle("Program Figure");
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//dodawanie do paska menu opcji Figury , Edytuj i Pomoc
		menu.add(FigureMenu);
		menu.add(EditMenu);
		menu.add(HelpMenu);
		//dodawanie pozycji do opcji Figury
		FigureMenu.add(pointItem);
		FigureMenu.add(circleItem);
		FigureMenu.add(triangleItem);
		FigureMenu.add(diamondItem);
		FigureMenu.add(windMillItem);
		FigureMenu.addSeparator();
		FigureMenu.add(displayAllItem);
		//dodawanie pozycji do menu Edit
		EditMenu.add(editItems[0]);
		EditMenu.add(editItems[1]);
		EditMenu.addSeparator();
		EditMenu.add(editItems[2]);
		EditMenu.add(editItems[3]);
		EditMenu.add(editItems[4]);
		EditMenu.add(editItems[5]);
		//dodawanie pozycji do menu Help
		HelpMenu.add(helpItems[0]);
		HelpMenu.add(helpItems[1]);
		setJMenuBar(menu);
		
		panel= new FigurePanel();
		panel.addKeyListener(panel);
		panel.setFocusable(true);
		panel.addMouseListener(panel);
		panel.addMouseMotionListener(panel);
		panel.setLayout(new FlowLayout());
		
		//dodawnaie przycisk�w do panelu
		panel.add(pointButton);
		panel.add(circleButton);
		panel.add(triangleButton);
		panel.add(diamondButton);
		panel.add(windMillButton);
		
		//dodawanie s�uchaczy do ka�dego obiketu
		pointButton.addActionListener(this);
		circleButton.addActionListener(this);
		triangleButton.addActionListener(this);
		diamondButton.addActionListener(this);
		windMillButton.addActionListener(this);
		
		pointItem.addActionListener(this);
		circleItem.addActionListener(this);
		triangleItem.addActionListener(this);
		diamondItem.addActionListener(this);
		windMillItem.addActionListener(this);
		displayAllItem.addActionListener(this);
		
		editItems[0].addActionListener(this);
		editItems[1].addActionListener(this);
		editItems[2].addActionListener(this);
		editItems[3].addActionListener(this);					
		editItems[4].addActionListener(this);
		editItems[5].addActionListener(this);
		
		helpItems[0].addActionListener(this);
		helpItems[1].addActionListener(this);
		setContentPane(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source=event.getSource();
		if(source==helpItems[0]) {
			JOptionPane.showMessageDialog(this,infoAutor);
		}
		if(source==helpItems[1]) {
			JOptionPane.showMessageDialog(this,DESCRIPTION);
		}
		if(source==pointItem||source==pointButton) {
			panel.addFigure(new Point());
		}
		if(source==circleItem||source==circleButton) {
			panel.addFigure(new Circle());
		}
		if(source==triangleItem||source==triangleButton) {
			panel.addFigure(new Triangle());
		}
		if(source==diamondItem||source==diamondButton) {
			panel.addFigure(new Diamond());
		}
		if(source==windMillItem||source==windMillButton) {
			panel.addFigure(new Windmill());
		}
		
		if(source==displayAllItem) {
			JOptionPane.showMessageDialog(null, panel.toString());
		}
		if(source==editItems[0]) {
			panel.scaleAllFigures(1.1f);
		}
		if(source==editItems[1]) {
			panel.scaleAllFigures(0.9f);
		}
		if(source==editItems[2]) {
			panel.moveAllFigures(0, -10);
		}
		if(source==editItems[3]) {
			panel.moveAllFigures(0, 10);
		}
		if(source==editItems[4]) {
			panel.moveAllFigures(-10, 0);
		}
		if(source==editItems[5]) {
			panel.moveAllFigures(10, 0);
		}
		panel.requestFocus();
	}

}
