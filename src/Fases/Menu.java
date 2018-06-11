package Fases;

import Turbine.Texturas;
import Turbine.Nave;
import Turbine.OGL;
import Turbine.Ponto;
import Turbine.Ceu;
import Turbine.Obstaculo;
import Turbine.Camera;
import Turbine.Controle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Menu extends Fase {
    private Texturas texturas;
    private Ceu ceu;
    private Camera camera;
    private ArrayList<Obstaculo> obstaculos;
    private int direcao;
    private int selecionado;

    
    public Menu() {
        this.texturas = new Texturas();
        this.camera = new Camera();
        this.ceu = new Ceu();
        this.obstaculos = new ArrayList<>();
        this.direcao = 0;
        this.selecionado = 0;
    }
    
    // carrega todos os elementos da fase
    public void carregar(String diretorioRaiz) {
        // carrega todas as texturas necessárias
        this.texturas.carregarTextura("background", diretorioRaiz + "/src/turbine/Arquivos/Menu/background.png");
        this.texturas.carregarTextura("Um", diretorioRaiz + "/src/turbine/Arquivos/Menu/Um.png");

        // carrega o skybox
        this.ceu.setTextura(this.texturas.getTextura("background"));
        this.ceu.setCor(Color.white);

        
        Obstaculo obstaculo = new Obstaculo();
        obstaculo.setLocal(new Ponto(0d, 0d, 0d));
        obstaculo.atualizarForma();
        obstaculo.getForma().setDimensoes(new Ponto(1d, 0.5d, 0.1d));
        obstaculo.getForma().setTextura(this.texturas.getTextura("Um"));
        obstaculo.getForma().setCor(Color.white);
        obstaculo.setDirecao(new Ponto());
        obstaculo.setVelocidade(0d);
        obstaculo.atualizarColisor();
        obstaculo.getColisor().setDimensoes(obstaculo.getForma().getDimensoes());
        this.obstaculos.add(obstaculo);
        
        obstaculo = new Obstaculo();
        obstaculo.setLocal(new Ponto(2d, 0d, -0.5d));
        obstaculo.atualizarForma();
        obstaculo.getForma().setDimensoes(new Ponto(1d, 0.5d, 0.1d));
        obstaculo.getForma().setTextura(this.texturas.getTextura("Um"));
        obstaculo.getForma().setCor(Color.white);
        obstaculo.setDirecao(new Ponto());
        obstaculo.setVelocidade(0d);
        obstaculo.atualizarColisor();
        obstaculo.getColisor().setDimensoes(obstaculo.getForma().getDimensoes());
        this.obstaculos.add(obstaculo);
        
        obstaculo = new Obstaculo();
        obstaculo.setLocal(new Ponto(4d, 0d, -1d));
        obstaculo.atualizarForma();
        obstaculo.getForma().setDimensoes(new Ponto(1d, 0.5d, 0.1d));
        obstaculo.getForma().setTextura(this.texturas.getTextura("Um"));
        obstaculo.getForma().setCor(Color.white);
        obstaculo.setDirecao(new Ponto());
        obstaculo.setVelocidade(0d);
        obstaculo.atualizarColisor();
        obstaculo.getColisor().setDimensoes(obstaculo.getForma().getDimensoes());
        this.obstaculos.add(obstaculo);
        
        obstaculo = new Obstaculo();
        obstaculo.setLocal(new Ponto(6d, 0d, -1.5d));
        obstaculo.atualizarForma();
        obstaculo.getForma().setDimensoes(new Ponto(1d, 0.5d, 0.1d));
        obstaculo.getForma().setTextura(this.texturas.getTextura("Um"));
        obstaculo.getForma().setCor(Color.white);
        obstaculo.setDirecao(new Ponto());
        obstaculo.setVelocidade(0d);
        obstaculo.atualizarColisor();
        obstaculo.getColisor().setDimensoes(obstaculo.getForma().getDimensoes());
        this.obstaculos.add(obstaculo);
        
        // anexa a nave na câmera
        this.camera.anexarObjeto(this.obstaculos.get(this.selecionado));
    }
    
    // roda a física e a lógica
    public void atualizar(Double deltaTempo, Controle controle) {
        if (controle.direita) {
            if (this.direcao != 1)
                this.selecionado = Math.floorMod((this.selecionado + 1), this.obstaculos.size());
            
            this.direcao = 1;
        }
        
        else if (controle.esquerda)  {
            if (this.direcao != -1)
                this.selecionado = Math.floorMod((this.selecionado - 1), this.obstaculos.size());
            
            this.direcao = -1;
        }
        
        else
            this.direcao = 0;
        
        this.camera.anexarObjeto(this.obstaculos.get(this.selecionado));
        // atualiza a câmera
        this.camera.transicaoCamera(deltaTempo);
    }
    
    // desenha todos os elementos na tela
    public void desenhar(OGL ogl) {
        // desenha o skybox
        this.ceu.desenhar(ogl);
        
        // ajusta a observação para o novo local da câmera
        this.camera.ajustaObservacao(ogl);
        
        this.defineIluminacao(ogl);

        for (Obstaculo o: this.obstaculos) {
            o.desenhar(ogl);
        }
    }
    
    public void defineIluminacao(OGL ogl) {}
}
