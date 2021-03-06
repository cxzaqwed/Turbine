package Turbine;

import com.jogamp.opengl.GL2;

public class Camera {
    // atributos temporariamente públicos
    public Ponto local; // local da câmera no espaço
    //public Ponto rotacao; // rotação na câmera, por ângulo nos 3 eixos. Talvez mude para um vetor de rotação e um ângulo
    public Ponto direcao;
    public Objeto anexo; // objeto anexado a essa câmera, de modo que ela o siga
    // teste
    
    // construtor padrão, cria a câmera na posição 0, 0, 0
    public Camera() {
        this.local = new Ponto();
        this.direcao = new Ponto();
    }
    
    // caso seja preciso alterar o local de uma câmera sem que ela esteja anexada a um objeto
    public void transladar(Ponto delta) {
        this.local.somar(delta);
    }
    
    // ajusta a perspectiva do jogo em relação a essa câmera
    public void ajustaObservacao(OGL ogl) {
        ogl.gl.glMatrixMode(GL2.GL_PROJECTION); // especifica sistema de coordenadas de projeção
        ogl.gl.glLoadIdentity(); // inicializa sistema de coordenadas de projeção
        
        ogl.glu.gluPerspective(120d, 1.77d, 0.2, 5000);// especifica a projeção perspectiva para 16:9, 120 graus de ângulo visão e 5000 metros de distância de visão
        ogl.gl.glMatrixMode(GL2.GL_MODELVIEW); // especifica sistema de coordenadas do modelo
        ogl.gl.glLoadIdentity(); // inicializa sistema de coordenadas do modelo
        ogl.glu.gluLookAt(this.local.x, this.local.y, this.local.z, this.direcao.x, this.direcao.y, this.direcao.z, 0, 1, 0); // especifica posição do observador e do alvo
    }
    
    
    // anexa um objeto a essa câmera para que ela o siga
    public void anexarObjeto(Objeto obj) {
        this.anexo = obj;
    }
    
    // transiciona essa câmera para o local indicado pelo objeto que ela deve seguir
    public void transicaoCamera(Double deltaTime) {
        this.local = anexo.getLocalCamera();
        this.direcao = this.anexo.getLocal();
//        
////         a câmera ficará travada no objeto caso a distância seja menor do que a distância percorrida pelo objeto em 2 frames + 50cm (no caso de o objeto estar parado)
//        if (this.local.getDistancia(anexo.getLocalCamera()) < 2 * this.anexo.getVelocidade() * deltaTime + 0.5d) {
//            this.local = anexo.getLocalCamera();
//       // caso a dustância seja maior, a câmera se aproximará do objeto a uma velocidade igual à soma da velocidade do objeto mais a distância entre eles
//       // dessa forma ela se aproximará mais rápido se estiver mais distante e mais devagar quando estiver perto
//       // em uma velocidade que é sempre maior do que a velocidade do objeto que ela irá seguir
//        } else {
//            Double velocidade = this.anexo.getVelocidade() + 2d * this.local.getDistancia(this.anexo.getLocal());
//            
//            
//            // calcula a direção na qual a câmera precisa se movimentar
//            Ponto vetorDirecao = new Ponto(this.anexo.getLocalCamera().x - this.local.x, this.anexo.getLocalCamera().y - this.local.y, this.anexo.getLocalCamera().z - this.local.z);
//            this.transladar(vetorDirecao.versor().escalar(velocidade * deltaTime));
//        }
//        
//        if (this.direcao.getDistancia(this.anexo.getLocal()) < 3d) {
//            this.direcao = this.anexo.getLocal();
//        } else {
//            Double velocidade = this.direcao.getDistancia(this.anexo.getLocal()) * 2d + 0.1d;
//            Ponto vetorDirecao = new Ponto(this.anexo.getLocal().x - this.direcao.x, this.anexo.getLocal().y - this.direcao.y, this.anexo.getLocal().z - this.direcao.z);
//            this.direcao.somar(vetorDirecao.escalar(deltaTime));
//        }
    }
}
