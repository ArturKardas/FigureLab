/*
 * Autor:Artur KArdas nr.Albumu:226148
 * Data:25.11.2017r.
 * Klasy: Figure, Point, Circle, Triangle, Diamond, WindMill
 * Klsay te służą one do zdefiniowania figur wyświetlanych w programie.
 * 
 * */
import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;



abstract public class Figure {
	static Random random = new Random();

	private boolean selected = false;

	public boolean isSelected() { return selected; }
	public void select() {	selected = true; }
	public void select(boolean z) { selected = z; }
	public void unselect() { selected = false; }

	protected void setColor(Graphics g) {
		if (selected) g.setColor(Color.RED);
		           else g.setColor(Color.BLACK);
	}

	public abstract boolean isInside(float px, float py);
	public boolean isInside(int px, int py) {
		return isInside((float) px, (float) py);
	}

	protected String properties() {
		String s = String.format("  Pole: %.0f  Obwod: %.0f", computeArea(), computePerimeter());
		if (isSelected()) s = s + "   [SELECTED]";
		return s;
	}

	abstract String getName();
	abstract float  getX();
	abstract float  getY();

    abstract float computeArea();
    abstract float computePerimeter();

    abstract void move(float dx, float dy);
    abstract void scale(float s);

    abstract void draw(Graphics g);

    @Override
    public String toString(){
        return getName();
    }

}

class Point extends Figure{

	protected float x, y;

	Point()
	{ this.x=random.nextFloat()*400;
	  this.y=random.nextFloat()*400;
	}

	Point(float x, float y)
	{ this.x=x;
	  this.y=y;
	}

	@Override
	public boolean isInside(float px, float py) {
		// by umo�liwi� zaznaczanie punktu myszk�
		// miejsca odleg�e nie wi�cej ni� 6 le�� wewn�trz
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= 6);
	}


    @Override
	String getName() {
		return "Point(" + x + ", " + y + ")";
	}

	@Override
	float getX() {
		return x;
	}

	@Override
	float getY() {
		return y;
	}

	@Override
    float computeArea(){ return 0; }

	@Override
	float computePerimeter(){ return 0; }

	@Override
    void move(float dx, float dy){ x+=dx; y+=dy; }

	@Override
    void scale(float s){ }

	@Override
    void draw(Graphics g){
		setColor(g);
		g.fillOval((int)(x-3),(int)(y-3), 6,6);
	}

    String toStringXY(){ return "(" + x + " , " + y + ")"; }

}


class Circle extends Point{
    float r;
    
    Circle(){
        super();
        r=random.nextFloat()*100;
    }

    Circle(float px, float py, float pr){
        super(px,py);
        r=pr;
    }

    @Override
	public boolean isInside(float px, float py) {
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= r);
	}

    @Override
   	String getName() {
   		return "Circle(" + x + ", " + y + ")";
   	}

    @Override
    float computeArea(){ return (float)Math.PI*r*r; }

    @Override
    float computePerimeter(){ return (float)Math.PI*r*2; }

    @Override
    void scale(float s){ r*=s; }

    @Override
    void draw(Graphics g){
    	setColor(g);
        g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
    }

}


class Triangle extends Figure{
    Point point1, point2, point3;

    Triangle(){
    	point1 = new Point();
    	point2 = new Point();
    	point3 = new Point();
    }

    Triangle(Point p1, Point p2, Point p3){
        point1=p1; point2=p2; point3=p3;
    }

    @Override
    public boolean isInside(float px, float py)
    { float d1, d2, d3;
      d1 = px*(point1.y-point2.y) + py*(point2.x-point1.x) +
           (point1.x*point2.y-point1.y*point2.x);
      d2 = px*(point2.y-point3.y) + py*(point3.x-point2.x) +
           (point2.x*point3.y-point2.y*point3.x);
      d3 = px*(point3.y-point1.y) + py*(point1.x-point3.x) +
           (point3.x*point1.y-point3.y*point1.x);
      return ((d1<=0)&&(d2<=0)&&(d3<=0)) || ((d1>=0)&&(d2>=0)&&(d3>=0));
    }

    @Override
	String getName() {
    	return "Triangle{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+"}";
	}

	@Override
	float getX() {
		return (point1.x+point2.x+point3.x)/3;
	}

	@Override
	float getY() {
		return (point1.y+point2.y+point3.y)/3;
	}

	@Override
	float computeArea(){
        float a = (float)Math.sqrt( (point1.x-point2.x)*(point1.x-point2.x)+
                                    (point1.y-point2.y)*(point1.y-point2.y));
        float b = (float)Math.sqrt( (point2.x-point3.x)*(point2.x-point3.x)+
                                    (point2.y-point3.y)*(point2.y-point3.y));
        float c = (float)Math.sqrt( (point1.x-point3.x)*(point1.x-point3.x)+
                                    (point1.y-point3.y)*(point1.y-point3.y));
        float p=(a+b+c)/2;
        return (float)Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }

	@Override
    float computePerimeter(){
        float a = (float)Math.sqrt( (point1.x-point2.x)*(point1.x-point2.x)+
                                    (point1.y-point2.y)*(point1.y-point2.y));
        float b = (float)Math.sqrt( (point2.x-point3.x)*(point2.x-point3.x)+
                                    (point2.y-point3.y)*(point2.y-point3.y));
        float c = (float)Math.sqrt( (point1.x-point3.x)*(point1.x-point3.x)+
                                    (point1.y-point3.y)*(point1.y-point3.y));
        return a+b+c;
    }

	@Override
    void move(float dx, float dy){
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
    }

	@Override
    void scale(float s){
        Point sr1 = new Point((point1.x+point2.x+point3.x)/3,
                              (point1.y+point2.y+point3.y)/3);
        point1.x*=s;point1.y*=s;
        point2.x*=s; point2.y*=s;
        point3.x*=s; point3.y*=s;
        Point sr2 = new Point((point1.x+point2.x+point3.x)/3,
                              (point1.y+point2.y+point3.y)/3);
        float dx=sr1.x-sr2.x;
        float dy=sr1.y-sr2.y;
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
        
    }

	@Override
    void draw(Graphics g){
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                   (int)point1.x, (int)point1.y);
    }

}
/*Klasa Diamond służy do zdefiniowania figury Romb, klasa dziedziczy po klasie Point, składowe nadrzednej
 * klasy definiują nam srodek rombu, a do składowych klasy Diamond należą dwie zmienne typu float 
 * które definiują nam początkowe długości przekątnych w tym rombie, do składowych także należa 
 * dwa onbiekty typu Triangle, definiują one nam romb który jest zbudowany z dwóch trójkątów.
 * Na podstawie tych dwóch obiektów typu Triangle które przedstawiają dwa trójkąty obliczamy pole rombu,
 * jego obwód a także sprawdzamy czy kursor myszki jest w środku posługująć sie metoda isInsert tych obiektów.
 *
 */
class Diamond extends Point{
	float f,e;
	Triangle t1,t2;
	Diamond(){
		super();
        e=random.nextFloat()*100;
        f=random.nextFloat()*100;
        t1=new Triangle(new Point(x-e/2,y),new Point(x,y-f/2),new Point(x+e/2,y));
        t2=new Triangle(new Point(x-e/2,y),new Point(x,y+f/2),new Point(x+e/2,y));
	}
	Diamond(float dx,float dy,float df,float de){
		super(dx,dy);
		f=df;
		e=de;
	}
	@Override
	public boolean isInside(float px, float py) {
		if(t1.isInside(px, py)) return true;
		if(t2.isInside(px, py)) return true;
		return false;
	}
	@Override
	String getName() {
		return "Diamond{"+ x +","+y+","+e+","+f+"}";
	}
	 void move(float dx, float dy){
	       t1.move(dx, dy);
	       t2.move(dx, dy);
	       
	    }
	@Override
    float computeArea(){ return t1.computeArea()+t2.computeArea();}
	
	@Override
    float computePerimeter(){ 
		return t1.computePerimeter()+t2.computePerimeter()-(2*(t1.point1.x-t1.point3.x)); 
		}

    @Override
    void scale(float s){
    	t1.point1.move(-((e*s)-e), 0);
    	t1.point2.move(0, -((f*s)-f));//zmiana
    	t1.point3.move(((e*s)-e), 0);
    	t2.point1.move(-((e*s)-e), 0);
    	t2.point2.move(0, ((f*s)-f));//zmiana
    	t2.point3.move(((e*s)-e), 0);
    	}
    @Override
    void draw(Graphics g){
		setColor(g);
        g.drawLine((int)t1.point1.x, (int)t1.point1.y,
                   (int)t1.point2.x, (int)t1.point2.y);
        g.drawLine((int)t1.point2.x, (int)t1.point2.y,
        			(int)t1.point3.x, (int)t1.point3.y);
        g.drawLine((int)t1.point3.x, (int)t1.point3.y,
        			(int)t2.point2.x, (int)t2.point2.y);
        g.drawLine((int)t2.point2.x, (int)t2.point2.y,
        			(int)t1.point1.x, (int)t1.point1.y);
    }
}
/*Klasa Windmill przedstawia nam obiekt podobny do wiatraka.
 *Wiatrak oparty jest na czterech jednakowych trójkątach 
 *oraz zdefiniowane sa dwie składowe do określenia początkowych długości przyprostokątnych tych trójkątów
 * */
class Windmill extends Point{
	float a,b;
	Triangle t1,t2,t3,t4;
	Windmill(){
		super();
		a=random.nextFloat()*50;
		b=random.nextFloat()*100;
		t1=new Triangle(new Point(x,y),new Point(x,y-b),new Point(x-a,y-b));
		t2=new Triangle(new Point(x,y),new Point(x+b,y),new Point(x+b,y-a));
		t3=new Triangle(new Point(x,y),new Point(x,y+b),new Point(x+a,y+b));
		t4=new Triangle(new Point(x,y),new Point(x-b,y),new Point(x-b,y+a));
	}
	Windmill(float px,float py, float pa, float pb){
		super(px,py);
		a=pa;
		b=pb;
		t1=new Triangle(new Point(x,y),new Point(x,y-b),new Point(x-a,y-b));
		t2=new Triangle(new Point(x,y),new Point(x+b,y),new Point(x+b,y-a));
		t3=new Triangle(new Point(x,y),new Point(x,y+b),new Point(x+a,y+b));
		t4=new Triangle(new Point(x,y),new Point(x-b,y),new Point(x-b,y+a));
	}
	@Override
	public boolean isInside(float px, float py) {
		if(t1.isInside(px, py)) return true;
		if(t2.isInside(px, py)) return true;
		if(t3.isInside(px, py)) return true;
		if(t4.isInside(px, py)) return true;
		return false;
	}
	@Override
	String getName() {
    	return "Windmill{" +a+ "," +b+ "," +x+ "," +y+ "}";
	}

	@Override
	float getX() {
		return x;
	}

	@Override
	float getY() {
		return y;
	}

	@Override
	float computeArea(){
        
        return t1.computeArea()+t2.computeArea()+t3.computeArea()+t4.computeArea();
    }

	@Override
    float computePerimeter(){

        return t1.computePerimeter()+t2.computePerimeter()+t3.computePerimeter()+t4.computePerimeter();
    }

	@Override
    void move(float dx, float dy){
       t1.move(dx, dy);
       t2.move(dx, dy);
       t3.move(dx, dy);
       t4.move(dx, dy);
    }

	@Override
    void scale(float s){
		
        t1.point2.move(0, -((b*s)-b));
        t1.point3.move(-((a*s)-a), -((b*s)-b));
        
        t2.point2.move(((b*s)-b), 0);
        t2.point3.move(((b*s)-b), -((a*s)-a));
        
        t3.point2.move(0, ((b*s)-b));
        t3.point3.move(((a*s)-a), ((b*s)-b));
        
        t4.point2.move(-((b*s)-b), 0);
        t4.point3.move(-((b*s)-b), ((a*s)-a));
		
    }

	@Override
    void draw(Graphics g){
		setColor(g);
		
		g.drawLine((int)t1.point1.x,(int)t1.point1.y,(int)t1.point2.x,(int)t1.point2.y);
		g.drawLine((int)t1.point2.x,(int)t1.point2.y,(int)t1.point3.x,(int)t1.point3.y);
		g.drawLine((int)t1.point3.x,(int)t1.point3.y,(int)t1.point1.x,(int)t1.point1.y);
		
		g.drawLine((int)t2.point1.x,(int)t2.point1.y,(int)t2.point2.x,(int)t2.point2.y);
		g.drawLine((int)t2.point2.x,(int)t2.point2.y,(int)t2.point3.x,(int)t2.point3.y);
		g.drawLine((int)t2.point3.x,(int)t2.point3.y,(int)t2.point1.x,(int)t2.point1.y);
		
		g.drawLine((int)t3.point1.x,(int)t3.point1.y,(int)t3.point2.x,(int)t3.point2.y);
		g.drawLine((int)t3.point2.x,(int)t3.point2.y,(int)t3.point3.x,(int)t3.point3.y);
		g.drawLine((int)t3.point3.x,(int)t3.point3.y,(int)t3.point1.x,(int)t3.point1.y);
		
		g.drawLine((int)t4.point1.x,(int)t4.point1.y,(int)t4.point2.x,(int)t4.point2.y);
		g.drawLine((int)t4.point2.x,(int)t4.point2.y,(int)t4.point3.x,(int)t4.point3.y);
		g.drawLine((int)t4.point3.x,(int)t4.point3.y,(int)t4.point1.x,(int)t4.point1.y);
    }
	
}
