package pa.iscde.search.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultList {

	private List<MatchResult> matches;
	
	public ResultList() {
		matches = new ArrayList<>();
	}
	
	public List<MatchResult> getResultList() {
		return Collections.unmodifiableList(matches);
	}
	
	public void setResultList(List<MatchResult> matches) {
		this.matches = matches;
	}
}
