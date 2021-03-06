package com.lostcode.javalib.entities.systems;

import java.util.ArrayList;

import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.components.Component;

/**
 * An {@link EntitySystem} that processes {@link Entity Entities} which have
 * specified {@link Component Components}.
 * 
 * @author Natman64
 * 
 */
public abstract class ComponentSystem extends EntitySystem {

	private ArrayList<Class<? extends Component>> componentTypes;

	// region Initialization

	/**
	 * Makes a ComponentSystem.
	 * 
	 * @param requiredType
	 *            The first required Component type.
	 * @param otherTypes
	 *            Other required Component types.
	 */
	public ComponentSystem(Class<? extends Component> requiredType,
			Class<? extends Component>... otherTypes) {
		componentTypes = new ArrayList<Class<? extends Component>>();

		componentTypes.add(requiredType);

		for (Class<? extends Component> type : otherTypes) {
			componentTypes.add(type);
		}
	}

	// endregion

	// region Entity Management

	@Override
	public boolean canProcess(Entity e) {

		for (Class<? extends Component> type : componentTypes) {
			if (!e.hasComponent(type))
				return false;
		}

		return true;

	}

	// endregion

}
