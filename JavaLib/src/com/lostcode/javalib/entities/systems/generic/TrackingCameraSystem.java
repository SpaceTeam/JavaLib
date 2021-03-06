package com.lostcode.javalib.entities.systems.generic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.components.generic.Health;
import com.lostcode.javalib.entities.components.physical.Transform;
import com.lostcode.javalib.entities.systems.TagSystem;
import com.lostcode.javalib.utils.Convert;

/**
 * System for moving the camera to follow a given entity.
 * 
 * @author Natman64
 * @created Jul 26, 2013
 */
public class TrackingCameraSystem extends TagSystem {

	private Camera camera;
	private Rectangle bounds;
	private Vector2 offset;

	/**
	 * Constructs a TrackingCameraSystem.
	 * 
	 * @param tag
	 *            The tag of the entity for the camera to follow.
	 * @deprecated Use the other constructor that takes no bounds, bounds are
	 *             now obtained every tick from the world.
	 */
	@Deprecated
	public TrackingCameraSystem(String tag, Camera camera, Rectangle bounds) {
		super(tag);

		this.camera = camera;
		this.bounds = Convert.metersToPixels(bounds);

		this.offset = new Vector2();
	}

	/**
	 * Constructs a TrackingCameraSystem.
	 * 
	 * @param tag
	 *            The tag of the entity for the camera to follow.
	 * @param camera
	 *            The camera that this system controls.
	 */
	public TrackingCameraSystem(String tag, Camera camera) {
		super(tag);

		this.camera = camera;

		this.offset = new Vector2();
	}

	@Override
	protected void process(Entity e) {
		setPosition(e);

		clampCamera();
	}

	/**
	 * @return The camera's offset from the tracked Entity.
	 */
	public Vector2 getCameraOffset() {
		return offset.cpy();
	}

	/**
	 * Sets the camera's offset from the tracked Entity.
	 * 
	 * @param offset
	 */
	public void setCameraOffset(Vector2 offset) {
		this.offset = offset;
	}

	private void setPosition(Entity e) {
		if (e.hasComponent(Health.class)) {
			Health h = e.getComponent(Health.class);

			if (h.isEmpty()) {
				return; // Don't track dead entities
			}
		}

		Transform t = (Transform) e.getComponent(Transform.class);

		Vector2 pos = Convert.metersToPixels(t.getPosition());

		pos.add(offset);

		camera.position.set(pos.x, pos.y, 0f);
	}

	private void clampCamera() {
		bounds = Convert.metersToPixels(world.getBounds());

		float left = camera.position.x - camera.viewportWidth / 2;
		float bottom = camera.position.y - camera.viewportHeight / 2;
		float right = left + camera.viewportWidth;
		float top = bottom + camera.viewportHeight;

		if (left < bounds.x) {
			camera.position.x = bounds.x + camera.viewportWidth / 2;
		}

		if (top > bounds.y + bounds.height) {
			camera.position.y = bounds.y + bounds.height
					- camera.viewportHeight / 2;
		}

		if (right > bounds.x + bounds.width) {
			camera.position.x = bounds.x + bounds.width - camera.viewportWidth
					/ 2;
		}

		if (bottom < bounds.y) {
			camera.position.y = bounds.y + camera.viewportHeight / 2;
		}
	}

	@Override
	public void dispose() {
	}

}
