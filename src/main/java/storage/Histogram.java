package storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Histogram<T> implements Iterable<T> {
	private HashMap<T, Integer> counts = new HashMap<T, Integer>();

	public Histogram() {}
	
	public Histogram(Histogram<T> other) {
		this.counts.putAll(other.counts);
		if (this.counts.size() != other.counts.size()) {
			throw new IllegalStateException("Huh? " + this.counts.size() + ": " + other.counts.size());
		}
	}
	
	public void bump(T value) {
		bumpBy(value, 1);
	}
	
	public void bumpBy(T value, int numBumps) {
		counts.put(value, getCountFor(value) + numBumps);
	}
	
	public int getCountFor(T value) {
		return counts.getOrDefault(value, 0);
	}
	
	public int getTotalCounts() {
		int total = 0;
		for (Entry<T, Integer> entry: counts.entrySet()) {
			total += entry.getValue();
		}
		return total;
	}
	
	@Override
	public Iterator<T> iterator() {
		return counts.keySet().iterator();
	}
	
	public int size() {return counts.size();}
	
	public T getPluralityWinner() {
		// TODO: Return the key with the highest count.
        Integer tempCount = Integer.MIN_VALUE;
        T tempT = null;
        for(T v: this.counts.keySet()){
            if(counts.get(v) > tempCount ){
                tempCount = counts.get(v);
                tempT =  v;
            }
        }
		return tempT;
	}

	public String toString() {
	    String result = "";
	    for (T thing: this) {
	        result += "(" + thing + ":" + getCountFor(thing) + ") ";
        }
	    return result;
    }
}
