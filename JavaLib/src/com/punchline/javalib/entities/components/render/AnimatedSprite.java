package com.punchline.javalib.entities.components.render;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.components.ComponentManager;
import com.punchline.javalib.utils.SpriteSheet;

/**
 * A sprite that contains multiple Animations, each mapped to a String showing the state which that animation represents.
 * (Jumping, running, etc.)
 * @author Natman64
 *
 */
public class AnimatedSprite implements Renderable {

	//region Fields
	
	private Map<String, Animation> animations;
	private String state;
	
	private float stateTime = 0f;
	
	private Vector2 position;
	private float rotation;
	private float scaleX = 1f;
	private float scaleY = 1f;
	private Vector2 origin;
	
	//endregion
	
	//region Initialization
	
	public AnimatedSprite(SpriteSheet spriteSheet, String key, int frameCols, float frameDuration) {
		
		Animation right = new Animation(spriteSheet, key + "#Right", frameCols, 1, frameDuration);
		Animation down = new Animation(spriteSheet, key + "#Down", frameCols, 1, frameDuration);
		Animation left = new Animation(spriteSheet, key + "#Left", frameCols, 1, frameDuration);
		Animation up = new Animation(spriteSheet, key + "#Up", frameCols, 1, frameDuration);
		
		animations.put("Right", right);
		animations.put("Down", down);
		animations.put("Left", left);
		animations.put("Up", up);
		
		setState("Down", true);
		
	}
	
	/**
	 * Constructs an AnimatedSprite.
	 * @param keys The animation keys.
	 * @param animations The animation values. NOTE: This must have the same size as the keys array.
	 * @param frameWidth The width of each frame.
	 * @param frameHeight The height of each frame.
	 * @param initialState The key of the AnimatedSprite's initial state.
	 */
	public AnimatedSprite(String[] keys, Animation[] animations, int frameWidth, int frameHeight, String initialState) {
		
		this.animations = new HashMap<String, Animation>();
		
		for (int i = 0; i < keys.length; i++) {
			if (i < animations.length) {
				this.animations.put(keys[i], animations[i]);
			}
		}
		
		setState(initialState, true);
		
	}
	
	//endregion

	//region Events
	
	@Override
	public void onAdd(ComponentManager container) { }

	@Override
	public void onRemove(ComponentManager container) { }
	
	//endregion
	
	//region Accessors
	
	@Override
	public float getWidth() {
		TextureRegion region = getCurrentFrame(0f);
		return region.getRegionWidth();
	}

	@Override
	public float getHeight() {
		TextureRegion region = getCurrentFrame(0f);
		return region.getRegionHeight();
	}

	@Override
	public Vector2 getPosition() {
		return position.cpy();
	}
	
	@Override
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * @return The current animation state key.
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @param deltaSeconds The amount of seconds since this was last called.
	 * @return The current frame that needs to be drawn.
	 */
	public TextureRegion getCurrentFrame(float deltaSeconds) {
		Animation currentAnimation = animations.get(state);
		
		currentAnimation.setStateTime(stateTime += deltaSeconds);
		return currentAnimation.getCurrentFrame(0f); //Delta is already accounted for.
	}
	
	//endregion
	
	//region Mutators

	@Override
	public void setPosition(Vector2 position) {
		TextureRegion region = getCurrentFrame(0f);
		this.position = position.sub(new Vector2(region.getRegionWidth() / 2, region.getRegionHeight() / 2));
	}

	@Override
	public void setRotation(float degrees) {
		this.rotation = degrees;
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}
	
	/**
	 * Sets the current state key to the given key, as long as there is a corresponding animation.
	 * @param state The key to set.
	 * @param keepStateTime Whether the state time should be reset to 0.
	 */
	public void setState(String state, boolean keepStateTime) throws IllegalArgumentException {
		if (animations.containsKey(state)) {
			this.state = state;
			
			if (!keepStateTime) stateTime = 0f;
		} else {
			throw new IllegalArgumentException("The animations map does not contain the specified key.");
		}
	}

	//endregion

	//region Rendering
	
	@Override
	public void draw(SpriteBatch spriteBatch, float deltaSeconds) {
		TextureRegion region = getCurrentFrame(deltaSeconds);
		spriteBatch.draw(region, position.x, position.y, origin.x, origin.y, 
				region.getRegionWidth(), region.getRegionHeight(), scaleX, scaleY, rotation);
	}
	
	//endregion
	
}
