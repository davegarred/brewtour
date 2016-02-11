package org.garred.brewtour.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

public class AdminView extends AbstractView implements Entity<LocaleId> {

	public LocaleId id;
	public List<CommentView> comments = new ArrayList<>();

	@Override
	public LocaleId identifier() {
		return this.id;
	}

	public static class CommentView extends AbstractView {
		public LocationId locationId;
		public UserId userId;
		public String userScreenName;
		public String comment;
		public LocalDateTime time;

		public static CommentView newComment(LocationId locationId, UserId userId, String userScreenName, String comment, LocalDateTime time) {
			final CommentView view = new CommentView();
			view.locationId = locationId;
			view.userId = userId;
			view.userScreenName = userScreenName;
			view.comment = comment;
			view.time = time;
			return view;
		}
	}



}
