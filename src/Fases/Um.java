package Fases;

import Turbine.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Um extends Fase {
    private Texturas texturas;
    private Ceu ceu;
    private Camera camera;
    private Nave nave;
    private ArrayList<Obstaculo> obstaculos;
    private Obstaculo chegada;
    private ArrayList<Obstaculo> chao;
    private boolean colide;
    
    public Um() {
        this.texturas = new Texturas();
        this.camera = new Camera();
        this.nave = new Nave();
        this.ceu = new Ceu();
        this.chegada = new Obstaculo();
        this.obstaculos = new ArrayList<>();
        this.chao = new ArrayList<>();
        this.colide = false;
    }
    
    // carrega todos os elementos da fase
    public void carregar(String diretorioRaiz) {
        // carrega todas as texturas necessárias
        this.texturas.carregarTextura("chegada_um", diretorioRaiz + "/src/turbine/Arquivos/chegada_um.png");
        this.texturas.carregarTextura("malha", diretorioRaiz + "/src/turbine/Arquivos/malha.png");
        this.texturas.carregarTextura("borda", diretorioRaiz + "/src/turbine/Arquivos/borda.png");

        
        // define a posição inicial da câmera
        this.camera.local.z = 800d;

        // carrega o skybox
        this.ceu.setTextura(this.texturas.getTextura("borda"));
        this.ceu.setCor(Color.white);
        
        // carrega a nave
        this.nave.setLocal(new Ponto(0d, 0d, 500d));
        this.nave.atualizarForma();
        this.nave.getForma().setDimensoes(new Ponto(1d, 0.1d, 1d));
        this.nave.getForma().setTextura(this.texturas.getTextura("borda"));
        this.nave.getForma().setCor(Color.white);
        this.nave.setDirecao(new Ponto(0d, 0d, -1d));
        this.nave.setVelocidade(250d); //900Km/h
        this.nave.atualizarColisor();
        this.nave.getColisor().setDimensoes(nave.getForma().getDimensoes()); // faz o colisor e a forma terem o mesmo tamanho
        
        // anexa a nave na câmera
        this.camera.anexarObjeto(nave);

        // gera os obstáculos
        for(int i = 0; i < 100; i++) {
            Obstaculo obstaculo = new Obstaculo();
            
            obstaculo.setLocal(new Ponto(
                    ThreadLocalRandom.current().nextDouble(-100d, 100d), 
                    50d, 
                    -i * 100d));
            obstaculo.atualizarForma();
            obstaculo.getForma().setDimensoes(new Ponto(10d, 100d, 10d));
            obstaculo.getForma().setTextura(this.texturas.getTextura("borda"));
            obstaculo.getForma().setCor(Color.white);
            obstaculo.setDirecao(new Ponto());
            obstaculo.setVelocidade(0d);
            obstaculo.atualizarColisor();
            obstaculo.getColisor().setDimensoes(obstaculo.getForma().getDimensoes());
            
            this.obstaculos.add(obstaculo);
        }
        
        // carrega a linha de chegada
        this.chegada.setLocal(new Ponto(0d, 50d, -10000d));
        this.chegada.atualizarForma();
        this.chegada.getForma().setDimensoes(new Ponto(100d, 100d, 10d));
        this.chegada.getForma().setTextura(this.texturas.getTextura("chegada_um"));
        this.chegada.getForma().setCor(Color.white);
        this.chegada.setDirecao(new Ponto());
        this.chegada.setVelocidade(0d);
        this.chegada.atualizarColisor();
        this.chegada.getColisor().setDimensoes(this.chegada.getForma().getDimensoes());
        
        
        for (int i = 0; i <= 10; i++) {
            // carrega o chão
            Obstaculo o = new Obstaculo();
            o.setLocal(new Ponto(0d, -3d, -1000d * i));
            o.atualizarForma();
            o.getForma().setDimensoes(new Ponto(100d, 2d, 500d));
            o.getForma().setTextura(this.texturas.getTextura("malha"));
            o.getForma().setCor(Color.white);
            o.setDirecao(new Ponto());
            o.setVelocidade(0d);
            o.atualizarColisor();
            o.getColisor().setDimensoes(o.getForma().getDimensoes());
            this.chao.add(o);
        }
        

    }
    
    // roda a física e a lógica
    public void atualizar(Double deltaTempo, Controle controle) {
        // roda a física
        this.nave.movimentar(controle, deltaTempo);
        this.nave.manterInercia(deltaTempo);
        this.nave.aplicarGravidade(0.1d, deltaTempo);
        this.nave.limitarAreaMovimento(new Ponto(-100d, 0d, 0d), new Ponto(100d, 100d, 0d));
        this.nave.atualizarHud();
        
        int pos = 0;
        for (Obstaculo o: this.obstaculos) {
            if (o.getLocal().z < this.nave.getLocal().z)
                pos++;
        }
        
        ((Texto) this.nave.getHud()).setTexto(((Texto) this.nave.getHud()).getTexto() + "\n#: " + pos);
        
        // verifica as colisões
        for (Obstaculo o: this.obstaculos) {
            if (!this.colide) {
                if (this.nave.getColisor().colideCom(o.getColisor())) {
                    this.colide = true;
                    this.camera.anexarObjeto(o);
                    // some com a nave da tela
                    this.nave.setLocal(new Ponto(0d, 0d, this.chegada.getLocal().z - this.chegada.getForma().getDimensoes().z  - 1d));
                }
            }
        }
        
        // verifica se o fim da fase foi alcançado
        if (!this.colide) {
            if (this.nave.getColisor().colideCom(this.chegada.getColisor())) {
                this.colide = true;
                // some com a nave da tela
                this.nave.setLocal(new Ponto(0d, 0d, this.chegada.getLocal().z - this.chegada.getForma().getDimensoes().z  - 1d));
                
                // muda a câmera para a linha de chegada
                this.camera.anexarObjeto(this.chegada);
            }
        }
        
        // atualiza a câmera
        this.camera.transicaoCamera(deltaTempo);
    }
    
    // desenha todos os elementos na tela
    public void desenhar(OGL ogl) {
        // desenha o skybox
        this.ceu.desenhar(ogl);
        
        // ajusta a observação para o novo local da câmera
        this.camera.ajustaObservacao(ogl);
        
         // desenha todos os objetos
        this.nave.desenhar(ogl);
        this.chegada.desenhar(ogl);
        for (Obstaculo o: this.obstaculos) {
            o.desenhar(ogl);
        }
        for (Obstaculo o: this.chao) {
            o.desenhar(ogl);
        }
    }
}
