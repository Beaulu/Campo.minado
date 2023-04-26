package primeiro.projeto.cm.logica;

import java.util.ArrayList;
import java.util.List;

import primeiro.projeto.cm.excecao.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	 public boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha- vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal){
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	 
	public void alternarMarcacao() {
		 if(!aberto) {
			 marcado = !marcado;
		 }
	 }
	 
	public boolean abrirCampo() {
		 if(!aberto && !marcado) {
			 aberto = true;
			 
			 if(minado) {
				 throw new ExplosaoException();
			 }
			 
			 if(vizinhancaSegura()) {
				 vizinhos.forEach(v -> v.abrirCampo());
			 }
			 return true;
		 }else {
			 return false;
		 }
	 }
	
	public void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	 
	 boolean vizinhancaSegura() {
		 return vizinhos.stream().noneMatch(v -> v.minado);
	 }
	 
	 public boolean isMarcado() {
		 return marcado;
	 }
	 
	 public void setAberto(boolean aberto) {
		 this.aberto = aberto;
		
	 }

	public boolean isAberto() {
		 return aberto;
	 }
	 
	 public boolean isFechado() {
		 return !isAberto();
	 }

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	 
	public boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido; 
	}
	
	public long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	public void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	 
	public String toString() {
		if(marcado) {
			return "x";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}
}
