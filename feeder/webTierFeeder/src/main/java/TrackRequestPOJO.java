import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;

/**
 * Created by Benjamin on 26/01/2016.
 */
public class TrackRequestPOJO {
	private String _id;
	private String targetSensor;
	private long dateStart;
	private long dateEnd;
	private long frequency;
	private long lastTimeUpdated;
	private boolean beingUpdated;

	public TrackRequestPOJO(@JsonProperty(value = "_id", required = false) String _id,
							@JsonProperty(value = "targetSensor", required = true) String targetSensor,
							@JsonProperty(value = "dateStart", required = true) long dateStart,
							@JsonProperty(value = "dateEnd", required = true) long dateEnd,
							@JsonProperty(value = "frequency", required = true) long frequency,
							@JsonProperty(value = "lastTimeUpdated", required = false) long lastTimeUpdated,
							@JsonProperty(value = "beingUpdated", required = false) boolean beingUpdated) {
		this._id = _id;
		this.targetSensor = targetSensor;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.frequency = frequency;
		this.lastTimeUpdated = lastTimeUpdated;
		this.beingUpdated = beingUpdated;
	}

	public boolean hasSameTargetThan(TrackRequestPOJO otherTrackingRequest) {
		return this.targetSensor.equals(otherTrackingRequest.targetSensor);
	}

	public String get_id() {
		if(_id == null) {
			return new ObjectId().toString();
		}
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTargetSensor() {
		return targetSensor;
	}

	public void setTargetSensor(String targetSensor) {
		this.targetSensor = targetSensor;
	}

	public long getDateStart() {
		return dateStart;
	}

	public void setDateStart(long dateStart) {
		this.dateStart = dateStart;
	}

	public long getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(long dateEnd) {
		this.dateEnd = dateEnd;
	}

	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	public long getLastTimeUpdated() {
		return lastTimeUpdated;
	}

	public void setLastTimeUpdated(long lastTimeUpdated) {
		this.lastTimeUpdated = lastTimeUpdated;
	}

	public boolean isBeingUpdated() {
		return beingUpdated;
	}

	public void setBeingUpdated(boolean beingUpdated) {
		this.beingUpdated = beingUpdated;
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "";
	}
}
