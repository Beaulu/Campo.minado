package primeiro.projeto.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import primeiro.projeto.cm.excecao.ExplosaoException;
import primeiro.projeto.cm.excecao.SairException;
import primeiro.projeto.cm.logica.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro){
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			while(continuar) {
				cicloDoJogo();
				
				System.out.println("Deseja outra partida? [S/N]");
				String resposta = entrada.nextLine();
				
				if(resposta.equalsIgnoreCase("n")) {
					continuar = false;
				}else {
					tabuleiro.reiniciar();
				}
			}
		}catch(SairException e){
			System.out.println("Você saiu do jogo! Até a próxima!");
		} finally {
			entrada.close();
		}
	}

	private void cicloDoJogo() {
		try {
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro.toString());
				
				String digitado = captarValorJogado("Aonde deseja jogar? [l, c]");
				Iterator<Integer> lc = Arrays.stream(digitado.split(","))
					.map(e -> Integer.parseInt(e.trim()))
					.iterator();
				
				digitado = captarValorJogado("1 - para ABRIR / 2 - para (des)MARCAR: ");
				if(digitado.equals("1")) {
					tabuleiro.abrir(lc.next(), lc.next());
				} else if (digitado.equals("2")) {
					tabuleiro.alternarMarcacao(lc.next(), lc.next());
				}
			}
			System.out.println(tabuleiro);
			System.out.println("Você ganhou!");
		}catch(ExplosaoException e){
			System.out.println(tabuleiro);
			System.out.println("Você perdeu!");
		}
	}
	
	private String captarValorJogado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if(digitado.equalsIgnoreCase("sair")) {
			throw new SairException();
		}
		return digitado;
	}
	
}
