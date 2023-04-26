package primeiro.projeto.cm;

import primeiro.projeto.cm.logica.Tabuleiro;
import primeiro.projeto.cm.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
	}
}
