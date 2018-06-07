package Turbine;

import Fases.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Renderizador extends MouseAdapter implements GLEventListener, KeyListener {
    // caminho para o diretório base do programa
    private String diretorioRaiz;
    
    // atributos comuns
    private OGL ogl;
    private Relogio relogio;
    private Controle controle;
    
    // fase ativa
    private Fase fase;

    public Renderizador() {
        this.diretorioRaiz = System.getProperty("user.dir"); // guarda o caminho da raiz do executável
        
        // atributos comuns inicializados
        this.ogl = new OGL();
        this.relogio = new Relogio();
        this.controle = new Controle();
        
        // carrega a fase ativa
        this.fase = new Um();
    }
 
    public void init(GLAutoDrawable drawable) {
        this.ogl.glDrawable = drawable;
        this.ogl.gl = drawable.getGL().getGL2();
        this.ogl.glu = new GLU();
        this.ogl.glut = new GLUT();
        this.ogl.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.ogl.gl.glEnable(GL.GL_DEPTH_TEST);
        this.ogl.gl.glEnable(GL.GL_TEXTURE_2D);
        
        // carrega a fase
        this.fase.carregar(this.diretorioRaiz);
        
        // inicia o relógio
        this.relogio.update();
    }

    public void display(GLAutoDrawable drawable) {
        this.ogl.gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        this.ogl.gl.glLoadIdentity();

        // atualiza o relógio 
        this.relogio.update();
        
        // debug do controle
        //System.out.println(this.controle);
        
        // atualiza a física e a lógica da fase
        this.fase.atualizar(this.relogio.getDeltaTempo(), this.controle);
        
        // realiza todas as operações de desenho da fase
        this.fase.desenhar(this.ogl);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                this.controle.cima = true; break;
                
            case KeyEvent.VK_DOWN:
                this.controle.baixo = true; break;
                
            case KeyEvent.VK_RIGHT:
                this.controle.direita = true; break;
                
            case KeyEvent.VK_LEFT:
                this.controle.esquerda = true; break;
        }
        
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                this.controle.cima = false; break;
                
            case KeyEvent.VK_DOWN:
                this.controle.baixo = false; break;
                
            case KeyEvent.VK_RIGHT:
                this.controle.direita = false; break;
                
            case KeyEvent.VK_LEFT:
                this.controle.esquerda = false; break;
        }
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}