package com.punchline.javalib.entities.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Component;

/**
 * Component wrapper for a LibGDX {@link com.badlogic.gdx.graphics.g2d.Sprite Sprite}.
 * @author Nathaniel
 *
 */
public class Sprite extends Component {

	private com.badlogic.gdx.graphics.g2d.Sprite sprite;
	
	/**
	 * Constructs a Sprite.
	 * @param texture The Sprite's {@link com.badlogic.gdx.graphics.Texture Texture}.
	 * @param source The Sprite's source rectangle.
	 * @param origin The Sprite's origin, in relation to it's bottom left corner.
	 */
	public Sprite(Texture texture, Rectangle source, Vector2 origin) {
		sprite = new com.badlogic.gdx.graphics.g2d.Sprite(
				texture, (int)source.x, (int)source.y, 
				(int)source.width, (int)source.height);
		
		sprite.setOrigin(origin.x, origin.y);
	}
	
	/**
	 * Constructs a Sprite with its origin at its center.
	 * @param texture The Sprite's {@link com.badlogic.gdx.graphics.Texture Texture}.
	 * @param source The Sprite's source rectangle.
	 */
	public Sprite(Texture texture, Rectangle source) {
		this(texture, source, new Vector2(source.width / 2, source.height / 2));
	}
	
	/**
	 * Constructs a Sprite with no source rectangle, and its origin at its center.
	 * @param texture The Sprite's {@link com.badlogic.gdx.graphics.Texture Texture}.
	 */
	public Sprite(Texture texture) {
		this(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()));
	}
	
	/**
	 * Sets the Sprite's position.
	 * @param position
	 */
	public void setPosition(Vector2 position) {
		sprite.setPosition(position.x, position.y);
	}
	
	/**
	 * Sets the Sprite's rotation, in degrees.
	 * @param degrees The desired rotation.
	 */
	public void setRotation(float degrees) {
		sprite.setRotation(degrees);
	}
	
	/**
	 * Draws the Sprite.
	 * @param spriteBatch The SpriteBatch for drawing.
	 */
	public void draw(SpriteBatch spriteBatch) {
		sprite.draw(spriteBatch);
	}
	
}
