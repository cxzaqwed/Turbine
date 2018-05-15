package turbine;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;


public abstract class Forma {
    private Texture textura;
    
    public abstract void desenha(GL2 gl2);
    
    public void carregarTextura(String caminho){
        // implementar ainda
    }
}