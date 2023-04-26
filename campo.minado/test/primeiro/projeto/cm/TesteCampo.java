package primeiro.projeto.cm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import primeiro.projeto.cm.excecao.ExplosaoException;
import primeiro.projeto.cm.logica.Campo;


class TesteCampo {

	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoEmCima() {
		Campo vizinho = new Campo(2,3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoEmBaixo() {
		Campo vizinho = new Campo(4,3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoNaEsquerda() {
		Campo vizinho = new Campo(3,2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoNaDireita() {
		Campo vizinho = new Campo(3,4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistanciaDiagonal() {
		Campo vizinho = new Campo(4,4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(3,5);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testeMarcacaoPadrao() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeMarcacaoTrue() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeMarcacaoFalse() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirCampoNaoMinadoNaoMarcado() {
		assertTrue(campo.abrirCampo());
	}
	
	@Test
	void testeAbrirCampoNaoMinadoMasMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrirCampo());
	}
	
	@Test
	void testeAbrirCampooMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, ()-> {
			campo.abrirCampo();
		});
	}
	
	@Test
	void testeAbrirCampoMinadoEMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrirCampo());
	}
	
	@Test
	void testeAbrirComVizinhos() {
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrirCampo();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhosMinados() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 1);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrirCampo();
		
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
	
	
	@Test
	void testeObjetivo() {
		campo.abrirCampo();
		assertTrue(campo.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivo2() {
		campo.minar();
		campo.alternarMarcacao();
		assertTrue(campo.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivo3() {
		Campo campo11 = new Campo(1, 1);
		campo11.alternarMarcacao();
		assertFalse(campo11.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivo4() {
		campo.minar();
		assertFalse(campo.objetivoAlcancado());
	}
	
	@Test 
	void testeMinasVizinhas() {
		Campo campo23 = new Campo(2, 3);
		Campo campo34 = new Campo(3, 4);
		Campo campo43 = new Campo(4, 3);
		
		campo23.minar();
		campo34.minar();
		
		campo.adicionarVizinho(campo23);
		campo.adicionarVizinho(campo34);
		campo.adicionarVizinho(campo43);
		
		assertEquals(2, campo.minasNaVizinhanca());
	}

	@Test
	void testeReiniciar() {
		campo.abrirCampo();
		campo.reiniciar();
		assertTrue(campo.isFechado());
	}
	
	@Test
	void testeDeStringMarcado() {
		campo.alternarMarcacao();
		assertEquals("x", campo.toString());
	}
	
	@Test
	void testeDeStringAberto() {
		campo.abrirCampo();
		assertEquals(" ", campo.toString());
	}
	
	@Test
	void testeDeStringAbertoEMinado() {
		campo.minar();
		assertThrows(ExplosaoException.class, ()-> {
			campo.abrirCampo();
			assertEquals("*", campo.toString());
		});
	}
	
	@Test
	void testeDeStringAbertoComMinas() {
		Campo campo23 = new Campo(2, 3);
		Campo campo34 = new Campo(3, 4);
		Campo campo43 = new Campo(4, 3);
	
		campo23.minar();
		campo34.minar();
		
		campo.adicionarVizinho(campo23);
		campo.adicionarVizinho(campo34);
		campo.adicionarVizinho(campo43);
		
		campo.abrirCampo();
		assertEquals("2", campo.toString());
	}
	
	@Test
	void testeVazio() {
		assertEquals("?", campo.toString());
	}
	
	
}
