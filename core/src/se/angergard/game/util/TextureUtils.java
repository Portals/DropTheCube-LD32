package se.angergard.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextureUtils {
	
	public static final Texture createTexture(Color color, int width, int height){
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		
		Texture texture = new Texture(pixmap);
		
		pixmap.dispose();
		
		return texture;
	}
	
	public static final TextureRegionDrawable createDrawable(Color color, int width, int height){
		return new TextureRegionDrawable(new TextureRegion(createTexture(color, width, height)));
	}
	
}
