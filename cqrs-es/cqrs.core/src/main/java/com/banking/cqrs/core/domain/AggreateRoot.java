package com.banking.cqrs.core.domain;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.banking.cqrs.core.events.BaseEvent;

public abstract class AggreateRoot {

	protected String id;

	private int version = -1;

	private final List<BaseEvent> changes = new ArrayList<>();

	private final Logger logger = Logger.getLogger(AggreateRoot.class.getName());

	public String getId() {
		return this.id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<BaseEvent> getUncommitedChanges() {
		return this.changes;
	}

	public void markChangesAsCommited() {
		this.changes.clear();
	}

	protected void applyChange(BaseEvent event, Boolean isNewEvent) {
		try {
			var method = getClass().getDeclaredMethod("apply", event.getClass());
			method.setAccessible(true);
			method.invoke(this, event);
		} catch (NoSuchMethodException ex) {
			logger.log(Level.WARNING,
					MessageFormat.format("apply method not found for {}", event.getClass().getName()));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "apply errors on aggregate");
		} finally {
			if (isNewEvent) {
				this.changes.add(event);
			}
		}
	}

	public void raiseEvent(BaseEvent event) {
		applyChange(event, true);
	}

	public void replayEvent(Iterable<BaseEvent> events) {
		events.forEach(event -> applyChange(event, false));
	}
}
