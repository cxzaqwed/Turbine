package turbine;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;



public class Esfera extends Forma {
    private Ponto dimensoes; // distorções nos 3 eixos
    private Ponto local; // centro
    private Ponto rotacao; // eixo de rotação
    private Double angulo; // angulo de rotação
    private Texture textura;
    
    public Esfera(){
        this.dimensoes = new Ponto(1d, 1d, 1d);
        this.local = new Ponto(0.0d, 0.0d, 0.0d);
        this.rotacao = new Ponto(0.0d, 0.0d, 0.0d);
        this.angulo = 0.0d;
    }
    
    public Esfera(Ponto excentricidade){
        this.dimensoes = excentricidade;
        this.local = new Ponto(0.0d, 0.0d, 0.0d);
        this.rotacao = new Ponto(0.0d, 0.0d, 0.0d);
        this.angulo = 0.0d;
    }

    public void desenhar(OGL ogl) {
        // desenha uma esfera comum e apica as operações necessárias
        this.textura.enable(ogl.gl);
        this.textura.bind(ogl.gl);
        
        ogl.gl.glPushMatrix();
            ogl.gl.glColor3f(1, 1, 1);
            ogl.gl.glTranslated(this.local.x, this.local.y, this.local.z);
            ogl.gl.glRotated(this.angulo, this.rotacao.x, this.rotacao.y, this.rotacao.z);
            ogl.gl.glScaled(this.dimensoes.x, this.dimensoes.y, this.dimensoes.z);
            //ogl.glut.glutSolidSphere(Constantes.METRO, 30, 30);
            GLUquadric quadrica = ogl.glu.gluNewQuadric();
            ogl.glu.gluQuadricTexture(quadrica, true);
            ogl.glu.gluQuadricDrawStyle(quadrica, GLU.GLU_FILL);
            ogl.glu.gluQuadricNormals(quadrica, GLU.GLU_FLAT);
            ogl.glu.gluQuadricOrientation(quadrica, GLU.GLU_OUTSIDE);
            ogl.glu.gluSphere(quadrica, 1d, 30, 30);
            ogl.glu.gluDeleteQuadric(quadrica);
            ogl.gl.glEnd();
        ogl.gl.glPopMatrix();
        
        this.textura.disable(ogl.gl);
    }

    public void escalar(Double v) {
        this.dimensoes.multiplicar(v);
    }
    
    public void rotacionar(Double angulo, Ponto eixo) {
        // utilizar quaternions para combinar rotações
        this.angulo = angulo % 360;
        this.rotacao = eixo;
    }
    
    public void transladar(Ponto delta) {
        this.local.somar(delta);
    }
    
    public void setTextura(Texture textura) {
        this.textura = textura;
    }
    
    public void setLocal(Ponto p) {
        this.local = p;
    }
    
    public Ponto getLocal() {
        return this.local;
    }
    
    public void setDimensoes(Ponto p) {
        this.dimensoes = p;
    }
    
    public Ponto getDimensoes() {
        return this.getDimensoes();
    }
}
