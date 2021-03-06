package fr.unice.polytech.ecoknowledge.domain.model.challenges;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Badge)) {
			return false;
		}

		Badge badge = (Badge) obj;

		return image.equals(badge.image)
				&& reward.equals(badge.reward)
				&& name.equals(badge.name);
	}
}