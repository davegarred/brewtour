package org.garred.brewtour.application;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.application.command.user.RemoveRoleFromUserCommand;
import org.garred.brewtour.application.event.user.UserAddedEvent;
import org.garred.brewtour.application.event.user.UserRolesUpdatedEvent;
import org.garred.brewtour.domain.Hash;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

@SuppressWarnings("serial")
public class User extends AbstractAnnotatedAggregateRoot<LocationId> {

    @AggregateIdentifier
    private UserId userId;
    private String screenName;
    private String login;
    private Hash hash;
    private Set<String> roles = new HashSet<>();

    public User() {}

    public static User addUser(AddUserCommand command, UserId userId) {
    	final User user = new User();
    	user.apply(UserAddedEvent.fromCommand(command, userId));
    	return user;
    }

    public void addRole(AddRoleToUserCommand command) {
    	if(!this.roles.contains(command.role)) {
    		final Set<String> allRoles = new HashSet<>(this.roles);
    		allRoles.add(command.role);
    		apply(new UserRolesUpdatedEvent(command.userId, allRoles));
    	}
    }

    public void removeRole(RemoveRoleFromUserCommand command) {
    	if(this.roles.contains(command.role)) {
    		final Set<String> allRoles = new HashSet<>(this.roles);
    		allRoles.remove(command.role);
    		apply(new UserRolesUpdatedEvent(command.userId, allRoles));
    	}
	}

	@EventHandler
	public void on(UserAddedEvent event) {
		this.userId = event.userId;
		this.login = event.login;
		this.hash = event.hash;
	}

	@EventHandler
	public void on(UserRolesUpdatedEvent event) {
		this.roles = event.roles;
	}

}
