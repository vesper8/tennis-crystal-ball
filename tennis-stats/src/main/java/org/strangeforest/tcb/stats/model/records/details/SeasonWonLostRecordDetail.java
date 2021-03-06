package org.strangeforest.tcb.stats.model.records.details;

import static java.lang.String.*;

public abstract class SeasonWonLostRecordDetail extends WonLostRecordDetail implements SeasonRecordDetail<String> {

	private final int season;

	protected SeasonWonLostRecordDetail(int won, int lost, int season) {
		super(won, lost);
		this.season = season;
	}

	@Override public int getSeason() {
		return season;
	}

	@Override public String toDetailString() {
		return format("%4$d %1$d-%2$d/%3$d", wonLost.getWon(), wonLost.getLost(), wonLost.getTotal(), getSeason());
	}
}
