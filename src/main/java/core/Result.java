package core;

import javafx.beans.property.*;

public class Result {
	public String label;
	public Double percent;
	public Integer success, failure;
	
	public Result(String label, int success, int failure) {
		this.label = label;
		this.success = success;
		this.failure = failure;
		this.percent = success / (double)(success + failure);
	}
	
	public String labelProperty() {return label;}
	public Double percentProperty() {return percent;}
	public Integer successProperty() {return success;}
	public Integer failureProperty() {return failure;}
}
