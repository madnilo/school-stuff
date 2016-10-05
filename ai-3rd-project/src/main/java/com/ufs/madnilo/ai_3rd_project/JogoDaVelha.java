package com.ufs.madnilo.ai_3rd_project;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JogoDaVelha extends Applet implements MouseListener, ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;

	JFrame frame;
	int flag = 2;
	int n;
	int m;
	static int bug = 0;
	char[] matriz = new char[9];
	JButton limpar;
	JButton sair;
	String s1 = "";

	public JogoDaVelha() {
		frame = new JFrame("Jogo da Velha");
		limpar = new JButton("Limpar");
		sair = new JButton("Sair");
		frame.add(limpar);
		frame.add(sair);
		frame.addWindowListener(this);
		frame.getContentPane().setBackground(Color.decode("064273"));
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setSize(800, 600);
		limpar.setBounds(650, 50, 90, 60);
		sair.setBounds(650, 250, 90, 60);

		frame.addMouseListener(this);
		for (int i = 0; i < 9; i += 1)
			matriz[i] = 'B';
		limpar.addActionListener(this);
		sair.addActionListener(this);

		Graphics g = frame.getGraphics();
		g.drawLine(200, 0, 200, 600);
		g.drawLine(400, 0, 400, 600);
		g.drawLine(0, 200, 600, 200);
		g.drawLine(0, 400, 600, 400);
		g.drawLine(600, 0, 600, 600);
	}

	public void keyPressed(KeyEvent k) {
		System.out.print("");
	}

	public void keyTyped(KeyEvent k) {
		s1 += k.getKeyChar();
	}

	public void keyReleased(KeyEvent k) {
		System.out.print("");
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == limpar) {
			frame.setVisible(false);
			bug = 0;
			new JogoDaVelha();
		}
		if (ae.getSource() == sair) {
			System.exit(0);
		}
	}

	public void windowClosing(WindowEvent de) {
		System.exit(0);
	}

	public void windowOpened(WindowEvent de) {
	}

	public void windowClosed(WindowEvent de) {
	}

	public void windowActivated(WindowEvent de) {
	}

	public void windowDeactivated(WindowEvent de) {
	}

	public void windowIconified(WindowEvent de) {
	}

	public void windowDeiconified(WindowEvent de) {
	}

	public void mouseClicked(MouseEvent e) {
		Graphics2D g2;
		Graphics g = frame.getGraphics();
		g.drawLine(200, 0, 200, 600);
		g.drawLine(400, 0, 400, 600);
		g.drawLine(0, 200, 600, 200);
		g.drawLine(0, 400, 600, 400);
		g.drawLine(600, 0, 600, 600);
		flag -= 1;
		int x = e.getX();
		int y = e.getY();
		if (flag == 1) {
			if ((x < 200) && (y < 200)) {
				m = 0;
				n = 0;
				matriz[0] = 'R';
			}
			if ((x > 200) && (x < 400) && (y < 200)) {
				m = 200;
				n = 0;
				matriz[1] = 'R';
			}
			if ((x > 400) && (x < 600) && (y < 200)) {
				m = 400;
				n = 0;
				matriz[2] = 'R';
			}
			if ((x < 200) && (y > 200) && (y < 400)) {
				m = 0;
				n = 200;
				matriz[3] = 'R';
			}
			if ((x > 200) && (x < 400) && (y > 200) && (y < 400)) {
				m = 200;
				n = 200;
				matriz[4] = 'R';
			}
			if ((x > 400) && (x < 600) && (y > 200) && (y < 400)) {
				m = 400;
				n = 200;
				matriz[5] = 'R';
			}
			if ((x < 200) && (y > 400) && (y < 600)) {
				m = 0;
				n = 400;
				matriz[6] = 'R';
			}
			if ((x > 200) && (x < 400) && (y > 400) && (y < 600)) {
				m = 200;
				n = 400;
				matriz[7] = 'R';
			}
			if ((x > 400) && (x < 600) && (y > 400) && (y < 600)) {
				m = 400;
				n = 400;
				matriz[8] = 'R';
			}
			g.setColor(Color.WHITE);
			g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(10.0F));
			g.drawOval(m + 10, n + 10, 159, 159);
		}

		if (flag == 0) {
			if ((x < 200) && (y < 200)) {
				m = 0;
				n = 20;
				matriz[0] = 'P';
			}
			if ((x > 200) && (x < 400) && (y < 200)) {
				m = 200;
				n = 20;
				matriz[1] = 'P';
			}
			if ((x > 400) && (x < 600) && (y < 200)) {
				m = 400;
				n = 20;
				matriz[2] = 'P';
			}
			if ((x < 200) && (y > 200) && (y < 400)) {
				m = 0;
				n = 200;
				matriz[3] = 'P';
			}
			if ((x > 200) && (x < 400) && (y > 200) && (y < 400)) {
				m = 200;
				n = 200;
				matriz[4] = 'P';
			}
			if ((x > 400) && (x < 600) && (y > 200) && (y < 400)) {
				m = 400;
				n = 200;
				matriz[5] = 'P';
			}
			if ((x < 200) && (y > 400) && (y < 600)) {
				m = 0;
				n = 400;
				matriz[6] = 'P';
			}
			if ((x > 200) && (x < 400) && (y > 400) && (y < 600)) {
				m = 200;
				n = 400;
				matriz[7] = 'P';
			}
			if ((x > 400) && (x < 600) && (y > 400) && (y < 600)) {
				m = 400;
				n = 400;
				matriz[8] = 'P';
			}
			g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(10.0F));
			g.setColor(Color.BLACK);
			g.drawLine(m + 10, n + 13, m + 169, n + 164);
			g.drawLine(m + 169, n + 10, m + 10, n + 169);
			flag += 2;
		}

		for (int i = 0; i < 3; i += 1) {
			if ((matriz[i] != 'B') && (matriz[(i + 3)] == matriz[i]) && (matriz[(i + 6)] == matriz[i])) {
				new Tabuleiro().vitoria();
				bug = 1;
			}
		}

		for (int i = 0; i < 7; i += 1) {
			if (matriz[i] != 'B') {
				if ((matriz[i] == matriz[(i + 1)]) && (matriz[i] == matriz[(i + 2)])) {
					new Tabuleiro().vitoria();
					bug = 1;
				}
				i += 2;
			} else {
				i += 2;
			}
		}
		if ((matriz[4] != 'B') && ((((matriz[0] == matriz[4]) && (matriz[4] == matriz[8]))
				|| ((matriz[2] == matriz[4]) && (matriz[4] == matriz[6]))))) {
			new Tabuleiro().vitoria();
			bug = 1;
		}

		for (int i = 0; (i < 9) && (matriz[i] != 'B'); i += 1) {
			if (i == 8) {
				if (bug == 0)
					new Tabuleiro().empate();
				bug = 0;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		System.out.print("");
	}

	public void mouseEntered(MouseEvent e) {
		System.out.print("");
	}

	public void mouseExited(MouseEvent e) {
		System.out.print("");
	}

	public void mousePressed(MouseEvent e) {
		System.out.print("");
	}

	public static void main(String[] args) {
		new JogoDaVelha();
	}
}
