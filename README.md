# Snake Game 

## Índice
1. [Introdução](#introdução)
2. [Dupla](#dupla)
3. [Descrição do Projeto](#descrição-do-projeto)
4. [Diagrama de Classes](#diagrama-de-classes)
5. [Prints das Telas](#prints-das-telas)
6. [Descrição das Funcionalidades](#descrição-das-funcionalidades)

---

### Introdução
Este projeto é uma implementação do clássico jogo da cobrinha, onde o jogador controla uma cobra que se move em um tabuleiro, com o objetivo de comer maçãs para crescer, aumentar a pontuação e a dificuldade a cada maçã comida. Mirtilo e banana são frutas auxiliares que tem uma chance de ser gerada a cada maçã comida, eles ajudam o jogador a sobreviver por mais tempo, diminuindo o seu tamanho e reduzindo sua velocidade. A cobra deve evitar colisões com as paredes e com o próprio corpo, ou o jogo termina.

---

### Dupla
- **Caetano Bernardo Monteiro Souza 237100**
- **Luccas Abreu Silva 237201**

---

### Descrição do Projeto
O jogo foi desenvolvido em **Java** e possui uma interface gráfica simples que proporciona uma experiência divertida para o jogador. O projeto inclui as seguintes funcionalidades:
- Movimento contínuo da cobra.
- Detecção de colisão com as bordas e com o corpo.
- Ao comer a Maçã aumenta o tamanho e velocidade da cobra, acrescentando na pontuação.
- Ao comer o Mirtilo a cobra diminui de tamanho, mas não acrescenta na pontuação.
- Ao comer a Banana a velocidade é reduzida, mas não acrescenta na pontuação.
- Reposição da fruta em um local aleatório após ser comida.

---

### Diagrama de Classes
Diagrama que ilustra as classes principais do jogo e suas relações:

![Diagrama de Classes SnakeGame](https://github.com/user-attachments/assets/0586d250-9b81-42e0-ab48-00b7a010699a)

---

### Prints das Telas
A seguir, alguns prints das telas do jogo:

#### Tela Inicial
![imagem_2024-11-13_090043515](https://github.com/user-attachments/assets/81aeadbf-3cb9-49a8-8d60-2b5b29f73939)

#### Tela de Jogo
![image](https://github.com/user-attachments/assets/3ceeeea6-0812-495c-a7f5-16976175f39b)

---

### Descrição das Funcionalidades
Aqui estão as descrições das principais funcionalidades dos métodos de cada classe:

#### Classe `Tabuleiro`
- **Descrição:** A classe **`Tabuleiro`** representa o ambiente principal onde o jogo da cobrinha é executado. Ela gerencia os elementos principais do jogo, incluindo a cobra, os obstáculos (frutas) e as mecânicas de controle.

- **Principais Métodos:**
  - `public Tabuleiro()`: Construtor da classe, que inicializa os elementos da interface gráfica do jogo, como botões, painel de jogo, e define os atributos iniciais da cobra e do tabuleiro.
  - `private void Iniciar()`: Método responsável por iniciar a execução do jogo.
  - `private void Reiniciar()`: Reseta o estado do jogo, posicionando a cobra em seu local inicial e redefinindo os elementos.
  - `private void Pausar()`: Pausa o Jogo.
  - `private void moverCobra()`: Controla o movimento da cobra conforme a direção atual.
  - `private void gerarNovaMaca()`, `private void gerarNovoMirtilo()`, `private void gerarNovaBanana()`: Geram novas posições para frutas sem sobrepor a cobra.
  - `private void verificarColisoes()`: Verifica as colisões da cobra com as bordas, com o próprio corpo e com os obstáculos.

#### Classe `Quadrado`
- **Descrição:** A classe Quadrado representa cada parte da cobra e os obstáculos (frutas) como elementos retangulares no tabuleiro. Serve como unidade básica para modelar os elementos visuais.

- **Atributos:**
  - `public int x, y:` Representam as coordenadas da posição do quadrado no tabuleiro.
  - `public int largura, altura:` São as dimensões do quadrado.
  - `public Color cor:` Cor do quadrado, pode ser alterada dependendo da função do elemento cobra, maçã, mirtilo e banana.
 
- Principais Métodos:
  - `public Quadrado(int largura, int altura, Color cor):` Construtor da classe que define o tamanho e a cor do quadrado.

---

> *Nota:* Para executar o projeto, basta executar o arquivo `.jar` gerado ou usar a classe `Tabuleiro`.
