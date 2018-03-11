/*
 * Autor:Artur KArdas nr.Albumu:226148
 * Data:25.11.2017r.
 * Klasy: FigurePanel
 * Klasa definiuje nam panel na którym rysujemy figury.
 * 
 * */
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

public class FigurePanel extends JPanel implements KeyListener, MouseListener,MouseMotionListener{

	int x,y;
	private static final long serialVersionUID = 1L;

	Vector<Figure> figures = new Vector<Figure>();
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Figure f : figures)
			f.draw(g);
	}
	
	void addFigure(Figure fig) {
		for (Figure f : figures){ 
			f.unselect();
			}
		fig.select();
	    figures.add(fig);
	    repaint();
	}
	
	void moveAllFigures(float dx, float dy){
	    	for (Figure f : figures){
	    		if (f.isSelected()) f.move(dx,dy);
	    	}
	        repaint();
	    }

	void scaleAllFigures(float s){
	    	for (Figure f : figures){ 
	    		if (f.isSelected()) 
	    			f.scale(s);
	        	}
	          repaint();
	    }

	public String toString(){
		String str = "Rysunek{ ";
		for(Figure f : figures)
			str+=f.toString() +"\n         ";
	        str+="}";
	        return str;
		}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int px = e.getX();
	     int py = e.getY();
	     for (Figure f : figures)
	       { if (e.isAltDown()==false) f.unselect();
	         if (f.isInside(px,py)) f.select( !f.isSelected() );
	       }
	     repaint();
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.x=e.getX();
		this.y=e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		int dist;
	       if (evt.isShiftDown()) dist = 10;
	                         else dist = 1;
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_UP:
				moveAllFigures(0, -dist);
				break;
			case KeyEvent.VK_DOWN:
				moveAllFigures(0, dist);
				break;
			case KeyEvent.VK_LEFT:
				moveAllFigures(-dist,0);
				break;
			case KeyEvent.VK_RIGHT:
				moveAllFigures(dist,0);
				break;
			case KeyEvent.VK_DELETE:
				Iterator<Figure> i = figures.iterator();
				while (i.hasNext()) {
					Figure f = i.next();
					if (f.isSelected()) {
						i.remove();
					}
				}
				repaint();
				break;
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		 char znak=evt.getKeyChar(); //reakcja na przycisku na naci�ni�cie klawisza
			switch (znak) {
			case 'p':
				addFigure(new Point());
				break;
			case 'c':
				addFigure(new Circle());
				break;
			case 't':
				addFigure(new Triangle());
				break;
			case 'r':
				addFigure(new Diamond());
				break;
			case 'w':
				addFigure(new Windmill());
				break;
			case '+':
				scaleAllFigures(1.1f);
				break;
			case '-':
				scaleAllFigures(0.9f);
				break;
			}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int cx=e.getX()-this.x;
		int cy=e.getY()-this.y;
		
		moveAllFigures(cx, cy);
		x=e.getX();
		y=e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
