package fr.unice.polytech.ecoknowledge.domain.calculator;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 11/12/2015.
 */
public class Filter {

	private AbstractMap.SimpleEntry<Integer, Integer> weekMoment;
	private List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment;

	public Filter(AbstractMap.SimpleEntry<Integer, Integer> weekMoment, List<AbstractMap.SimpleEntry<Integer, Integer>> dayMoment) {
		this.weekMoment = weekMoment;
		this.dayMoment = dayMoment;
	}

	public List<Data> filter(List<Data> datas) {
		ArrayList<Data> result = new ArrayList<>();
		for (Data d : datas) {
			int day = d.getDate().getDayOfWeek();
			if (weekMoment.getKey() <= day && day <= weekMoment.getValue()) {
				for (AbstractMap.SimpleEntry<Integer, Integer> dayEntry : dayMoment) {
					int hour = d.getDate().getHourOfDay();
					if (dayEntry.getKey() <= hour && hour < dayEntry.getValue())
						result.add(d);
				}
			}
		}
		return result;
	}
}
