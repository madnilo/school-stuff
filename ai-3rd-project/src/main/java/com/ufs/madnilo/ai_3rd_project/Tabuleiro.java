package com.ufs.madnilo.ai_3rd_project;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Tabuleiro implements WindowListener {
	@SuppressWarnings("deprecation")
	public void vitoria() {
		String message = "Congratulations !!!!! \n    \nYou win \n";

		JOptionPane pane = new JOptionPane(message);
		JDialog dialog = pane.createDialog(new JFrame(), "Dilaog");
		dialog.show();
	}

	@SuppressWarnings("deprecation")
	public void empate() {
		String message = "O jogo empatou! \n Ninguém ganhou!";

		JOptionPane pane = new JOptionPane(message);
		JDialog dialog = pane.createDialog(new JFrame(), "Dilaog");
		dialog.show();
	}

	public void windowClosing(WindowEvent we) {
		System.exit(0);
	}

	public void windowOpened(WindowEvent we) {
	}

	public void windowClosed(WindowEvent we) {
	}

	public void windowActivated(WindowEvent we) {
	}

	public void windowDeactivated(WindowEvent we) {
	}

	public void windowIconified(WindowEvent we) {
	}

	public void windowDeiconified(WindowEvent we) {
	}
}