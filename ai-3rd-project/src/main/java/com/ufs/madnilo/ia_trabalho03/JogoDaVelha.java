package com.ufs.madnilo.ia_trabalho03;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class JogoDaVelha extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	public int nosExpandidos = 0, profundidade = 0;

	private JPanel painelConteudo = null;
	private static JPanel painelTabuleiro = null;

	private int[][] combinacoesVitoria = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 },
			{ 1, 5, 9 }, { 3, 5, 7 } };

	private static JButton[] botoes = new JButton[10];
	private int contador = 0;
	private boolean venceu = false;
	private String resultado;
	private String jogador = null;
	private static int vitoriasDeO = 0, derrotasDeO = 0, empates = 0, vitoriasDeX = 0, derrotasDeX = 0;
	private int[] tabuleiro = new int[10];


	private JPanel getPainelTabuleiro() {
		if (painelTabuleiro == null) {
			painelTabuleiro = new JPanel();
			painelTabuleiro.setLayout(null);
			painelTabuleiro.setBounds(new Rectangle(167, 101, 500, 500));
			painelTabuleiro.setLayout(new GridLayout(3, 3));
			painelTabuleiro.setVisible(true);
			painelTabuleiro.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createBevelBorder(BevelBorder.LOWERED), "Tabuleiro", TitledBorder.CENTER,
					TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 18), Color.DARK_GRAY));
		}
		return painelTabuleiro;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JogoDaVelha jogo = new JogoDaVelha();
				jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jogo.setVisible(true);

			}
		});
	}

	public JogoDaVelha() {
		super();
		inicializar();

		for (int i = 1; i <= 9; i++) {
			botoes[i] = new JButton();
			botoes[i].setFont(new Font("Arial", Font.BOLD, 72));
			botoes[i].setText("");
			botoes[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			painelTabuleiro.add(botoes[i]);
			botoes[i].addActionListener(this);
		}

	}

	private void inicializar() {
		setSize(800, 700);
		setContentPane(getPainelConteudo());
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private JPanel getPainelConteudo() {
		if (painelConteudo == null) {
			painelConteudo = new JPanel();
			painelConteudo.setLayout(null);
			painelConteudo.add(getPainelTabuleiro(), null);
		}
		return painelConteudo;
	}

	public static void resetarPontuacao(ActionEvent evt) {
		vitoriasDeO = (derrotasDeO = empates = 0);
		vitoriasDeX = (derrotasDeX = 0);
	}

	public void actionPerformed(ActionEvent action) {
		JButton botaoPressionado = (JButton) action.getSource();
		if (botaoPressionado.getText().equals("")) {
			botaoPressionado.setText("X");
			botaoPressionado.setForeground(Color.GREEN);
			tabuleiro = novoTabuleiro();
			contador += 1;
			checarVencedor();
			int posMax = minimax(tabuleiro);
			botoes[posMax].setText("O");
			botoes[posMax].setForeground(Color.DARK_GRAY);
			contador += 1;
			checarVencedor();
		} else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Escolha outro movimento.", "Jogo Da Velha ",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public int[] poz_lib_cp(int[] cop) {
		int bb = 0;
		int[] poz_0 = new int[10];
		for (int i = 1; i <= 9; i++) {
			if (cop[i] == 0) {
				bb++;
				poz_0[bb] = i;
			}
		}
		return poz_0;
	}

	public int[] novoTabuleiro() {
		int[] tabuleiro = new int[10];
		for (int i = 1; i <= 9; i++)
			if (botoes[i].getText().equals("X")) {
				tabuleiro[i] = 1;
			} else if (botoes[i].getText().equals("O")) {
				tabuleiro[i] = 2;
			} else {
				tabuleiro[i] = 0;
			}
		return tabuleiro;
	}


	public int vencedorTabuleiro(int[] cc) {
		int rez = -1;
		int zero = 0;
		@SuppressWarnings("unused")
		String vencedor = "";
		boolean gameOver = false;

		for (int i = 0; i <= 7; i++) {
			if ((cc[combinacoesVitoria[i][0]] == 1) && (cc[combinacoesVitoria[i][0]] == cc[combinacoesVitoria[i][1]])
					&& (cc[combinacoesVitoria[i][1]] == cc[combinacoesVitoria[i][2]]) && (cc[combinacoesVitoria[i][0]] != 0)) {
				gameOver = true;
				vencedor = "X";
				rez = -1000000;
			}

			if ((cc[combinacoesVitoria[i][0]] != 2) || (cc[combinacoesVitoria[i][0]] != cc[combinacoesVitoria[i][1]])
					|| (cc[combinacoesVitoria[i][1]] != cc[combinacoesVitoria[i][2]]) || (cc[combinacoesVitoria[i][0]] == 0)) {
				continue;
			}
			gameOver = true;
			vencedor = "O";
			rez = 1000000;
		}

		for (int c = 1; c <= 9; c++) {
			if (cc[c] != 0) {
				zero++;
			}
		}

		if ((zero >= 9) && (!gameOver)) {
			vencedor = "Empate";
			rez = 0;
		}

		return rez;
	}

	public void mostrarVencedor(int vitorias, int empates, int derrotas, String msg) {
		int opcao = JOptionPane.showConfirmDialog(null,
				vitorias + " Vitórias\n" + derrotas + " Empates\n" + empates + " Derrotas\n" + "Jogar de novo?", msg, 0);
		if (opcao == 0) {
			novoJogo();
		} else
			System.exit(0);
	}

	public void novoJogo() {
		contador = 0;
		venceu = false;
		resultado = "";
		profundidade = 0;
		for (int i = 1; i <= 9; i++) {
			botoes[i].setText("");
			tabuleiro[i] = 0;
		}
	}

	public int minimax(int[] board) {
		int melhorValor = -1000000;
		int posicao = 0;
		int[] melhorJogada = new int[10];
		int[] p_lib = new int[10];
		p_lib = poz_lib_cp(board);
		int nr_poz = 0;

		for (int cc = 1; cc <= 9; cc++) {
			if (p_lib[cc] > 0) {
				nr_poz++;
			}
		}

		int nr = 1;
		while (nr <= nr_poz) {
			int mut = p_lib[nr];
			board[mut] = 2;

			int val = movimentoMin(board);
			if (val > melhorValor) {
				melhorValor = val;
				posicao = 0;
				melhorJogada[posicao] = mut;
			} else if (val == melhorValor) {
				posicao++;
				melhorJogada[posicao] = mut;
			}
			board[mut] = 0;
			nr++;
		}

		int r = 0;
		if (posicao > 0) {
			Random x = new Random();
			r = x.nextInt(posicao);
		}
		return melhorJogada[r];
	}

	public int movimentoMin(int[] board) {
		int pos_value = vencedorTabuleiro(board);

		nosExpandidos += 1;

		if (pos_value != -1) {
			return pos_value;
		}

		int best_val = 1000000;
		int[] p_lib = new int[10];
		p_lib = poz_lib_cp(board);
		int nr_poz = 0;
		for (int cc = 1; cc <= 9; cc++) {
			if (p_lib[cc] > 0) {
				nr_poz++;
			}
		}
		int nr = 1;
		while (nr <= nr_poz) {
			int mut = p_lib[nr];
			board[mut] = 1;
			int val = movimentoMax(board);
			if (val < best_val) {
				best_val = val;
			}
			board[mut] = 0;
			nr++;
		}
		return best_val;
	}

	public int movimentoMax(int[] board) {
		int pos_value = vencedorTabuleiro(board);

		nosExpandidos += 1;

		if (pos_value != -1) {
			return pos_value;
		}
		int best_val = -1000000;
		int[] p_lib = new int[10];
		p_lib = poz_lib_cp(board);
		int nr_poz = 0;
		for (int cc = 1; cc <= 9; cc++) {
			if (p_lib[cc] > 0) {
				nr_poz++;
			}
		}
		int nr = 1;

		while (nr <= nr_poz) {
			int mut = p_lib[nr];
			board[mut] = 2;
			int val = movimentoMin(board);
			if (val > best_val) {
				best_val = val;
			}

			board[mut] = 0;
			nr++;
		}
		return best_val;
	}

	public void checarVencedor() {
		int nr = 0;
		venceu = false;

		for (int i = 0; i <= 7; i++) {
			if ((botoes[combinacoesVitoria[i][0]].getText().equals("X"))
					&& (botoes[combinacoesVitoria[i][0]].getText().equals(botoes[combinacoesVitoria[i][1]].getText()))
					&& (botoes[combinacoesVitoria[i][1]].getText().equals(botoes[combinacoesVitoria[i][2]].getText()))
					&& (!botoes[combinacoesVitoria[i][0]].getText().equals(""))) {
				jogador = "X";
				venceu = true;
			}

			if ((!botoes[combinacoesVitoria[i][0]].getText().equals("O"))
					|| (!botoes[combinacoesVitoria[i][0]].getText().equals(botoes[combinacoesVitoria[i][1]].getText()))
					|| (!botoes[combinacoesVitoria[i][1]].getText().equals(botoes[combinacoesVitoria[i][2]].getText()))
					|| (botoes[combinacoesVitoria[i][0]].getText().equals(""))) {
				continue;
			}
			jogador = "O";
			venceu = true;
		}

		for (int c = 1; c <= 9; c++) {
			if ((botoes[c].getText().equals("X")) || (botoes[c].getText().equals("O"))) {
				nr++;
			}
		}
		if (venceu == true) {

			if (jogador == "X") {
				vitoriasDeX += 1;
				derrotasDeO += 1;
				resultado = "X Ganhou!";
				mostrarVencedor(vitoriasDeX, derrotasDeX, empates, resultado);
			}

			if (jogador == "O") {
				vitoriasDeO += 1;
				derrotasDeX += 1;
				resultado = "0 Ganhou!";
				mostrarVencedor(vitoriasDeO, derrotasDeO, empates, resultado);
			}
		}


		if ((nr == 9) && (contador >= 9) && (!venceu)) {
			empates += 1;
			resultado = "Jogo empatado!";

			if (JOptionPane.showConfirmDialog(null, empates + " Empates\n" + "Jogar de novo?", resultado, 0) != 1) {
				novoJogo();
			} else
				System.exit(0);

		}
		System.out.println("Nós Expandidos: " + nosExpandidos);
		nosExpandidos = 0;

	}

}