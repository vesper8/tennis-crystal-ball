package org.strangeforest.tcb.stats.web;

import java.math.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.actuate.endpoint.mvc.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.strangeforest.tcb.stats.controller.*;

@Component @VisitorSupport
public class VisitorsMvcEndpoint extends AbstractMvcEndpoint {

	@Autowired private VisitorRepository repository;

	public VisitorsMvcEndpoint() {
		super("/visitors", false);
	}

	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView visitors(
		@RequestParam(name = "stat", defaultValue = "HITS") VisitorStat stat,
		@RequestParam(name = "interval", defaultValue = "DAY") VisitorInterval interval,
		@RequestParam(name = "robots", defaultValue = "false") boolean robots
	) {
		Map<String, BigDecimal> countriesMap = repository.getVisitorsByCountry(stat, interval, robots);
		List<Object[]> countries = mapToDataArray(countriesMap, "Country", stat.getCaption());
		Map<String, BigDecimal> agentTypeMap = repository.getVisitorsByAgentType(stat, interval, robots);
		List<Object[]> agentTypes = mapToDataArray(agentTypeMap, "Agent Type", stat.getCaption());

		ModelMap modelMap = new ModelMap();
		modelMap.put("versions", BaseController.VERSIONS);
		modelMap.put("stat", stat);
		modelMap.put("interval", interval);
		modelMap.put("robots", robots);
		modelMap.put("stats", VisitorStat.values());
		modelMap.put("intervals", VisitorInterval.values());
		modelMap.put("countries", countries.toArray());
		modelMap.put("agentTypes", agentTypes.toArray());
		return new ModelAndView("manage/visitors", modelMap);
	}

	private static <K, V> List<Object[]> mapToDataArray(Map<K, V> map, String keyHeader, String valueHeader) {
		List<Object[]> array = new ArrayList<>();
		array.add(new Object[] {keyHeader, valueHeader});
		map.forEach((key, value) -> array.add(new Object[] {key, value}));
		return array;
	}
}