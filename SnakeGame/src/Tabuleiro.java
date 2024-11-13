
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Graphics;


public class Tabuleiro extends JFrame {

	private JPanel painel;
	private JPanel menu;
	private JButton iniciarButton;
	private JButton resetButton;
	private JButton pauseButton;
	private JTextField placarField;
	private String direcao = "direita";
	private long tempoAtualizacao = 100;
	private int incremento = 10;
	private Quadrado obstaculo, obstaculo2, obstaculo3, cobra;
	private ArrayList<Quadrado> corpoCobra; // Lista para armazenar o corpo da cobra
	private int larguraTabuleiro, alturaTabuleiro;
	private int placar = 0;
	private boolean emJogo = false; // Status do jogo 
	private boolean pausado = false;
	private boolean colidirComBorda = true; // Colisão com bordas
	private boolean bananaComida = false; // Controle para verificar se a banana foi comida
	private ImageIcon imagemCapa;
	

	public Tabuleiro() {
		larguraTabuleiro = alturaTabuleiro = 400;

		cobra = new Quadrado(10, 10, Color.BLACK);
		cobra.x = larguraTabuleiro / 2;
		cobra.y = alturaTabuleiro / 2;

		// Lista que inicia a cobra apenas com a cabeça
		corpoCobra = new ArrayList<>();
		corpoCobra.add(cobra);

		obstaculo = new Quadrado(8, 8, Color.red);
		gerarNovaMaca();
		
		obstaculo2 = new Quadrado(8, 8, Color.blue);
		gerarNovoMirtilo();
		
		obstaculo3 = new Quadrado(8, 8, Color.yellow);
		gerarNovaBanana();

		setTitle("Jogo da Cobrinha");
		setSize(alturaTabuleiro + 16, larguraTabuleiro + 75);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		imagemCapa = new ImageIcon(getClass().getResource("/image/SnakeGameCapa.jpg"));


		menu = new JPanel();
		menu.setLayout(new FlowLayout());

		iniciarButton = new JButton("Iniciar");
		resetButton = new JButton("Reiniciar");
		pauseButton = new JButton("Pausar");
		placarField = new JTextField("Placar: 0");
		placarField.setEditable(false);
		placarField.setColumns(5);

		menu.add(iniciarButton);
		menu.add(resetButton);
		menu.add(pauseButton);
		menu.add(placarField);

		painel = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				
				 if (!emJogo) {
	                    g.drawImage(imagemCapa.getImage(), 0, 0, getWidth(), getHeight(), this); // Desenha a imagem de fundo
	                }

				// Define o tamanho dos quadrados do tabuleiro
				 if (emJogo) {
				int tamanhoQuadrado = incremento * 4; 
											

				// Desenha o tabuleiro xadrez
				for (int i = 0; i < larguraTabuleiro / tamanhoQuadrado; i++) {
					for (int j = 0; j < alturaTabuleiro / tamanhoQuadrado; j++) {
						if ((i + j) % 2 == 0) {
							g.setColor(new Color(162, 209, 74)); 
						} else {
							g.setColor(new Color(169, 216, 81)); 
						}
						g.fillRect(i * tamanhoQuadrado, j * tamanhoQuadrado, tamanhoQuadrado, tamanhoQuadrado);
					}
				}

				 // Desenha a cobra
			    for (int i = 0; i < corpoCobra.size(); i++) {
			        Quadrado parte = corpoCobra.get(i);
			        g.setColor(parte.cor);
			        g.fillRect(parte.x, parte.y, parte.largura, parte.altura);

			        // Adiciona olhos à cabeça da cobra (primeiro elemento da lista)
			        if (i == 0) {
			            g.setColor(Color.WHITE); // Cor dos olhos
			            int tamanhoOlho = parte.largura / 4; // Define o tamanho dos olhos

			            // Posição dos olhos com base na direção da cabeça
			            if (direcao.equals("direita")) {
			                // Olhos à direita
			                g.fillOval(parte.x + 3 * tamanhoOlho / 2, parte.y + parte.altura / 4, tamanhoOlho, tamanhoOlho);
			                g.fillOval(parte.x + 3 * tamanhoOlho / 2, parte.y + 3 * parte.altura / 4 - tamanhoOlho, tamanhoOlho, tamanhoOlho);
			            } else if (direcao.equals("esquerda")) {
			                // Olhos à esquerda
			                g.fillOval(parte.x + tamanhoOlho / 2, parte.y + parte.altura / 4, tamanhoOlho, tamanhoOlho);
			                g.fillOval(parte.x + tamanhoOlho / 2, parte.y + 3 * parte.altura / 4 - tamanhoOlho, tamanhoOlho, tamanhoOlho);
			            } else if (direcao.equals("cima")) {
			                // Olhos para cima
			                g.fillOval(parte.x + parte.largura / 4, parte.y + tamanhoOlho / 2, tamanhoOlho, tamanhoOlho);
			                g.fillOval(parte.x + 3 * parte.largura / 4 - tamanhoOlho, parte.y + tamanhoOlho / 2, tamanhoOlho, tamanhoOlho);
			            } else if (direcao.equals("baixo")) {
			                // Olhos para baixo
			                g.fillOval(parte.x + parte.largura / 4, parte.y + 3 * tamanhoOlho / 2, tamanhoOlho, tamanhoOlho);
			                g.fillOval(parte.x + 3 * parte.largura / 4 - tamanhoOlho, parte.y + 3 * tamanhoOlho / 2, tamanhoOlho, tamanhoOlho);
			            }
			        }
			    }

				g.setColor(obstaculo.cor);
				g.fillRect(obstaculo.x, obstaculo.y, obstaculo.largura, obstaculo.altura);
				
				g.setColor(obstaculo2.cor);
				g.fillRect(obstaculo2.x,obstaculo2.y, obstaculo2.largura, obstaculo2.altura);
				
				g.setColor(obstaculo3.cor);
				g.fillRect(obstaculo3.x,obstaculo3.y, obstaculo3.largura, obstaculo3.altura);
		
				 }
			}
		};

		add(menu, BorderLayout.NORTH);
		add(painel, BorderLayout.CENTER);

		setVisible(true);

		// ActionListener para o botão Iniciar
		iniciarButton.addActionListener(e -> {
			if (!emJogo && !pausado) {
				Iniciar();
				painel.requestFocusInWindow(); // Devolve o foco para o painel
			}
			
		});

		// ActionListener para o botão Reset
		resetButton.addActionListener(e -> Reiniciar());

		// ActionListener para o botão Pausar
		pauseButton.addActionListener(e -> Pausar());

		painel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!pausado && emJogo) { // Verifica se o jogo está em andamento
					switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						if (!direcao.equals("direita")) {
							direcao = "esquerda";
						}
						break;
					case KeyEvent.VK_RIGHT:
						if (!direcao.equals("esquerda")) {
							direcao = "direita";
						}
						break;
					case KeyEvent.VK_UP:
						if (!direcao.equals("baixo")) {
							direcao = "cima";
						}
						break;
					case KeyEvent.VK_DOWN:
						if (!direcao.equals("cima")) {
							direcao = "baixo";
						}
						break;
					}
				}
			}
		});

		painel.setFocusable(true);
		painel.requestFocusInWindow();
	}

	private void Iniciar() {
		emJogo = true; // Começa o jogo
		new Thread(() -> {
			while (emJogo) {
				try {
					Thread.sleep(tempoAtualizacao);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (!pausado) {
					moverCobra();
					verificarColisoes();
					painel.repaint();
				}
			}
		}).start();
	}

	private void moverCobra() {
		// Salva a posição anterior da cabeça para atualizar o corpo
		int anteriorX = cobra.x;
		int anteriorY = cobra.y;

		// Move a cabeça da cobra na direção atual
		switch (direcao) {
		case "esquerda":
			cobra.x -= incremento;
			break;
		case "direita":
			cobra.x += incremento;
			break;
		case "cima":
			cobra.y -= incremento;
			break;
		case "baixo":
			cobra.y += incremento;
			break;
		}

		// Atualiza as posições das partes do corpo
		for (int i = 1; i < corpoCobra.size(); i++) {
			Quadrado parte = corpoCobra.get(i);
			int tempX = parte.x;
			int tempY = parte.y;
			parte.x = anteriorX;
			parte.y = anteriorY;
			anteriorX = tempX;
			anteriorY = tempY;
		}
	}

	private void Reiniciar() {
		emJogo = false; // Para o jogo
		corpoCobra.clear();
		cobra = new Quadrado(10, 10, Color.BLACK);
		cobra.x = larguraTabuleiro / 2;
		cobra.y = alturaTabuleiro / 2;
		corpoCobra.add(cobra);
		placar = 0;
		tempoAtualizacao = 100;
		direcao = "direita";
		gerarNovaMaca();
		placarField.setText("Placar: " + placar);
		painel.repaint();
		JOptionPane.showMessageDialog(this, "Jogo Reiniciado!", "Reset", JOptionPane.INFORMATION_MESSAGE);
	}

	private void Pausar() {
		pausado = !pausado; // Alterna o estado de pausa
		String msg = pausado ? "Jogo Pausado!" : "Jogo Continuado!";
		JOptionPane.showMessageDialog(this, msg);
	}

	public static void main(String[] args) {
		new Tabuleiro();
	}

	private void gerarNovaMaca() {
		// Gera uma nova posição aleatória para a maçã (obstáculo), sem sobrepor a cobra
		int maxX = larguraTabuleiro / incremento;
		int maxY = alturaTabuleiro / incremento;
		boolean posicaoValida;

		do {
			obstaculo.x = (int) (Math.random() * maxX) * incremento;
			obstaculo.y = (int) (Math.random() * maxY) * incremento;

			// Verifica se a maçã gerada não sobrepõe a cobra
			posicaoValida = true;
			for (Quadrado parte : corpoCobra) {
				if (parte.x == obstaculo.x && parte.y == obstaculo.y) {
					posicaoValida = false; // Se a maçã sobrepõe a cobra, gera uma nova posição
					break;
				}
			}
			
		} while (!posicaoValida);
	}
	
	private void gerarNovoMirtilo() {
		int maxX = larguraTabuleiro / incremento;
		int maxY = alturaTabuleiro / incremento;
		boolean posicaoValida;

		do {
			obstaculo2.x = (int) (Math.random() * maxX) * incremento;
			obstaculo2.y = (int) (Math.random() * maxY) * incremento;

			posicaoValida = true;
			for (Quadrado parte : corpoCobra) {
				if (parte.x == obstaculo2.x && parte.y == obstaculo2.y) {
					posicaoValida = false;
					break;
				}
			}
			
		} while (!posicaoValida);
	}
	
	private void gerarNovaBanana() {
		int maxX = larguraTabuleiro / incremento;
		int maxY = alturaTabuleiro / incremento;
		boolean posicaoValida;

		do {
			obstaculo3.x = (int) (Math.random() * maxX) * incremento;
			obstaculo3.y = (int) (Math.random() * maxY) * incremento;

			posicaoValida = true;
			for (Quadrado parte : corpoCobra) {
				if (parte.x == obstaculo3.x && parte.y == obstaculo3.y) {
					posicaoValida = false; 
					break;
				}
			}
			
		} while (!posicaoValida);
	}
	

	private void crescerCobra() {
		// Adiciona uma nova parte ao corpo da cobra na posição da última parte
		Quadrado cauda = corpoCobra.get(corpoCobra.size() - 1);
		Quadrado novaParte = new Quadrado(cauda.largura, cauda.altura, cauda.cor);
		novaParte.x = cauda.x;
		novaParte.y = cauda.y;
		corpoCobra.add(novaParte);
		
	}
	
		

	private void verificarColisoes() {
		// Verifica colisão com as bordas
		if (cobra.x < 0 || cobra.x >= larguraTabuleiro || cobra.y < 0 || cobra.y >= alturaTabuleiro) {

			if (colidirComBorda) {
				emJogo = false;
				JOptionPane.showMessageDialog(this, "Game Over! Bateu na borda.");
		
			}
		}

		// Verifica colisão com o próprio corpo
		for (int i = 1; i < corpoCobra.size(); i++) {
			if (cobra.x == corpoCobra.get(i).x && cobra.y == corpoCobra.get(i).y) {
				emJogo = false;
				JOptionPane.showMessageDialog(this, "Game Over! Colidiu com o próprio corpo.");
				break;
			}
		}

		// Verifica se a cobra comeu a maçã
		if (cobra.x == obstaculo.x && cobra.y == obstaculo.y) {
			placar++; // Incrementa o placar
			placarField.setText("Placar: " + placar); // Atualiza o placar na interface

			// Gera nova posição para a maçã
			gerarNovaMaca();

			// Aumenta a cobra
			crescerCobra();

			// Aumenta a dificuldade
			tempoAtualizacao = Math.max(20, tempoAtualizacao - 2); // Aumenta a velocidade
			
			 // Gera uma nova banana com 5% de chance
		    if (Math.random() < 0.05) {
		        gerarNovaBanana(); // Gera a banana
		      
		    }
			
			   // Verifica se o mirtilo deve aparecer com 20% de chance
	        if (Math.random() < 0.20) {
	        	gerarNovoMirtilo(); // Gera o mirtilo
	        }
	    }
			
		
		 if (cobra.x == obstaculo2.x && cobra.y == obstaculo2.y) {
		        // Decrementa o tamanho da cobra (remove a última parte)
		        if (corpoCobra.size() > 1) {
		            corpoCobra.remove(corpoCobra.size() - 1); 
		            
		        }	       
		        
		        // Reseta a posição do mirtilo 
		        obstaculo2.x = -10;  
		        obstaculo2.y = -10;
		    }
		 
		// Verifica colisão com a banana
		 if (cobra.x == obstaculo3.x && cobra.y == obstaculo3.y) {
		     tempoAtualizacao = Math.max(20, tempoAtualizacao + 2); // Diminui a velocidade
		     
		     // Reseta a posição da banana após ser comida
		     obstaculo3.x = -10;
		     obstaculo3.y = -10;
		     bananaComida = false; 
		 }
		
	}
}
