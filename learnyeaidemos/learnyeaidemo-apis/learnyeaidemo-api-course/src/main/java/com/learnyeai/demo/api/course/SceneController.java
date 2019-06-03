package com.learnyeai.demo.api.course;

import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jovanyfreamwork.cloud.saas.core.SceneFeignClient;
import cn.jovanyfreamwork.cloud.saas.data.Scene;

@RestController
@RequestMapping("/api/scenes")
public class SceneController {

	private SceneFeignClient client;

	@Autowired
	public SceneController(SceneFeignClient client) {
		this.client = client;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ping")
	public String ping() {
		return "Hello, world!";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PagedResources<Scene>> findAll(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int size,
			@RequestParam(required = false, defaultValue = "") String sort) {
		if (sort == null || sort.length() == 0) {
			return client.findAll(PageRequest.of(page, size));
		}
		String[] split = sort.split(",");
		List<Order> orders = new ArrayList<>();
		for (int i = 0; i < split.length; i += 2) {
			if (i + 1 >= split.length) {
				orders.add(Order.by(split[i]));
			} else {
				switch (split[i + 1].toLowerCase()) {
				case "asc":
					orders.add(Order.asc(split[i]));
					break;
				case "desc":
					orders.add(Order.desc(split[i]));
					break;
				default:
					throw new InvalidParameterException(
							MessageFormat.format("sort '{0}' isnot in ['asc', 'desc']", split[i + 1]));
				}
				orders.add(Order.by(split[0]));
			}
		}
		return client.findAll(PageRequest.of(page, size, Sort.by(orders)));
	}

}
