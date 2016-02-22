package org.garred.brewtour.controller;

import static java.lang.String.format;
import static org.garred.brewtour.application.command.GenericAddAggregateCallback.forCommand;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.Reader;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.application.command.GenericAddAggregateCallback;
import org.garred.brewtour.application.command.beer.AbstractBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.repository.BeerViewRepository;
import org.garred.brewtour.service.UserDetailsService;
import org.garred.brewtour.view.BeerUserCombinedView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(value = "beer")
public class BeerController extends AbstractRestController {

	private final CommandGateway commandGateway;
	private final BeerViewRepository beerRepo;
	private final UserDetailsService userService;

	public BeerController(CommandGateway commandGateway, BeerViewRepository beerRepo, UserDetailsService userService) {
		this.commandGateway = commandGateway;
		this.beerRepo = beerRepo;
		this.userService = userService;
	}

	@RequestMapping(value = "/addBeer", method = POST, produces="application/json")
	@ResponseBody
	public BeerId addlocation(@RequestBody AddBeerCommand addBeer) {
		final GenericAddAggregateCallback<BeerId> callback = forCommand(addBeer);
		this.commandGateway.sendAndWait(addBeer);
		return callback.identifier();
	}

	@RequestMapping(value = "{commandName}", method = POST, produces="application/json")
	@ResponseBody
	public BeerUserCombinedView modifyBeer(@PathVariable("commandName") String commandName, Reader reader) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings("unchecked")
		final Class<AbstractBeerCommand> commandType = (Class<AbstractBeerCommand>) Class.forName(format("org.garred.brewtour.application.command.beer.%sCommand",commandName));
		final AbstractBeerCommand command = this.objectMapper.readValue(reader, commandType);
		this.commandGateway.sendAndWait(command);
		return new BeerUserCombinedView(this.beerRepo.get(command.beerId), this.userService.getCurrentUserDetails());
	}

}
