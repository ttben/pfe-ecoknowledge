package fr.unice.polytech.ecoknowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Badge {

	private String image;
	private Integer reward;
	private String name;

	@JsonCreator
	public Badge(@JsonProperty("image") String image, @JsonProperty("reward") Integer reward,
				 @JsonProperty("name") String name) {
		this.image = image;
		this.reward = reward;
		this.name = name;
	}
}