package Turbine;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import java.awt.Color;

// subforma que desenha uma esfera
public class Esfera extends Forma {
    private Ponto dimensoes; // distorções nos 3 eixos
    private Ponto local; // centro
    private Ponto rotacao; // eixo de rotação
    private Double angulo; // angulo de rotação
    private Texture textura; // textura da esfera
    private Color cor; // cor base da esfera
    
    // construtor padrão, cria uma esfera de tamanho 1, 1, 1 no local 0, 0, 0 e sem rotação
    public Esfera(){
        this.dimensoes = new Ponto(1d, 1d, 1d);
        this.local = new Ponto(0.0d, 0.0d, 0.0d);
        this.rotacao = new Ponto(0.0d, 0.0d, 0.0d);
        this.angulo = 0.0d;
        this.cor = new Color(255, 255, 255);
    }
    
    // construtor completo
    public Esfera(Ponto local, Ponto dimensoes, Ponto rotacao, Double angulo, Texture textura, Color cor) {
        this.local = local;
        this.dimensoes = dimensoes;
        this.rotacao = rotacao;
        this.angulo = angulo;
        this.textura = textura;
        this.cor = cor;
    }

    // desenha a esfera na tela
    public void desenhar(OGL ogl) {
        this.textura.enable(ogl.gl); // habilita a textura
        this.textura.bind(ogl.gl);
        
        ogl.gl.glPushMatrix();
            ogl.gl.glTranslated(this.local.x, this.local.y, this.local.z); // move a esfera para o local definido (a princípio está na origem)
            ogl.gl.glRotated(this.angulo, this.rotacao.x, this.rotacao.y, this.rotacao.z); // aplica a rotação definida
            ogl.gl.glScaled(this.dimensoes.x, this.dimensoes.y, this.dimensoes.z); // aplica a escala definida
            ogl.gl.glColor3d(this.cor.getRed() / 255d, this.cor.getGreen() / 255d, this.cor.getBlue() / 255d); // define a cor básica do objeto
            
            GLUquadric quadrica = ogl.glu.gluNewQuadric();
            ogl.glu.gluQuadricTexture(quadrica, true);
            ogl.glu.gluQuadricDrawStyle(quadrica, GLU.GLU_FILL);
            ogl.glu.gluQuadricNormals(quadrica, GLU.GLU_FLAT);
            ogl.glu.gluQuadricOrientation(quadrica, GLU.GLU_OUTSIDE);
            ogl.glu.gluSphere(quadrica, 1d, 30, 30);
            ogl.glu.gluDeleteQuadric(quadrica);
            ogl.gl.glEnd(); // termina o desenho
        ogl.gl.glPopMatrix();
        
        this.textura.disable(ogl.gl); // desabilita a textura
    }

    // escala a esfera em um certo valor
    public void escalar(Double v) {
        this.dimensoes.multiplicar(v);
    }
    
    // rotaciona a esfera em um certo valor em um certo eixo
    public void rotacionar(Double angulo, Ponto eixo) {
        // utilizar quaternions para combinar rotações
        this.angulo = angulo % 360;
        this.rotacao = eixo;
    }
    
    // movimenta a esfera de acordo com os valores de delta
    public void transladar(Ponto delta) {
        this.local.somar(delta);
    }
    
    // define a textura da esfera
    public void setTextura(Texture textura) {
        this.textura = textura;
    }
    
    // define a textura básica da esfera
    public void setCor(Color cor) {
        this.cor = cor;
    }
    
    // define o local da esfera "na mão"
    public void setLocal(Ponto p) {
        this.local = p;
    }
    
    // retorna o local da esfera
    public Ponto getLocal() {
        return this.local;
    }
    
    // define as dimensoes da esfera
    public void setDimensoes(Ponto p) {
        this.dimensoes = p;
    }
    
    // retorna as dimensoes da esfera
    public Ponto getDimensoes() {
        return this.dimensoes;
    }

    // retorna o eixo de rotação
    public Ponto getRotacao() {
        return rotacao;
    }

    // define o eixo de rotação
    public void setRotacao(Ponto rotacao) {
        this.rotacao = rotacao;
    }

    // retorna o ângulo de rotação
    public Double getAngulo() {
        return angulo;
    }

    // define o eixo de rotação
    public void setAngulo(Double angulo) {
        this.angulo = angulo;
    }
}
