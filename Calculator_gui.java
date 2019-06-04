package Calculator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Button{
	String n[]= {
			"|","&","^","1/x","x©÷","%",
			"<<",">>","CE","CLR","¡ç","+",
			"A","D","7","8","9","-",
			"B","E","4","5","6","¡¿",
			"C","F","1","2","3","¡À",
			"HEX","DEC","¡¾","0",".","="};
	Color back_cl[]= {
			Color.LIGHT_GRAY, Color.LIGHT_GRAY,Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
			Color.LIGHT_GRAY, Color.LIGHT_GRAY,Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
			Color.white,Color.white,Color.white,Color.white,Color.white,Color.LIGHT_GRAY,
			Color.white,Color.white,Color.white,Color.white,Color.white,Color.LIGHT_GRAY,
			Color.white,Color.white,Color.white,Color.white,Color.white,Color.LIGHT_GRAY,
			Color.LIGHT_GRAY, Color.LIGHT_GRAY,Color.white,Color.white,Color.white,Color.LIGHT_GRAY,
	};
	
	Color fore_cl[]= {
			Color.black, Color.black, Color.black, Color.black,Color.black, Color.black,
			Color.black, Color.black, Color.black, Color.black,Color.black, Color.black,
			Color.black, Color.black, Color.black, Color.black,Color.black, Color.black,
			Color.black, Color.black, Color.black, Color.black, Color.black, Color.black,
			Color.black, Color.black, Color.black, Color.black, Color.black, Color.black,
			Color.black, Color.black, Color.black, Color.black, Color.black, Color.black
	};
	JButton jb[] = new JButton[n.length];
	
}

public class Calculator_gui extends Calculator_calculate {
	JFrame f = new JFrame("Calculater");
	Toolkit kit = Toolkit.getDefaultToolkit();
	JLabel jt = new JLabel();
	JLabel jp = new JLabel();
	JLabel jty = new JLabel();
	final int HEIGHT = 300;
	final int WIDTH = 400;
	Button b=new Button();

	Calculator_gui() {
		Dimension screenSize = kit.getScreenSize();
		f.setLayout(null);
		f.setSize(WIDTH, HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocation(screenSize.width / 3, screenSize.height / 3);

		panels_init();
		icon();
	}

	void show() {
		f.setVisible(true);
	}

	void panels_init() {
		f.setLayout(new GridLayout(2, 1, 4, 4));
		txt_panel();
		number_button();
		f.addKeyListener(new K_Action());
		f.setFocusable(true);
	}

	void number_button() {
		
		JPanel num_p = new JPanel(new GridLayout(6, 6, 2, 2));

		for (int i = 0; i < b.n.length; i++) {
			b.jb[i] = new JButton(b.n[i]);
			b.jb[i].setForeground(b.fore_cl[i]);
			b.jb[i].setBackground(b.back_cl[i]);
			num_p.add(b.jb[i]);
			b.jb[i].addActionListener(new B_Action());
		}
		f.add(num_p);
	}

	void txt_panel() {
		JPanel txt_p = new JPanel(new GridLayout(3, 1, 1, 1));
		jp.setForeground(Color.DARK_GRAY);
		txt_p.add(jp);
		txt_p.add(jt);
		txt_p.add(jty);
		txt_p.setBackground(Color.white);
		jty.setForeground(Color.GRAY);
		jp.setForeground(Color.GRAY);

		jt.setBorder(null);
		f.add(txt_p);
	}

	void icon() {
		Image img = kit.getImage("calculator-icon.png");
		f.setIconImage(img);
	}
	
	
	
	// ************button_Action**************
	
	
	//Key Action
	class K_Action implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			int dot=34;

			char str = e.getKeyChar();
			calculate(Character.toString(str));
			
			//set dot button Color
			b.jb[dot].setBackground(type_color());
			
			
			jp.setText(print_pre());
			jt.setText(print_now());
			jty.setText(print_type());
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e){}

		
	}

	//button action
	class B_Action implements ActionListener {

		int a;
		int buf = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			int dot=34;
			
			String str = e.getActionCommand();

			calculate(str);
			
			//set dot button color
			b.jb[dot].setBackground(type_color());
			
			
			jp.setText(print_pre());
			jt.setText(print_now());
			jty.setText(print_type());
			f.requestFocus();
		}

	}
}
