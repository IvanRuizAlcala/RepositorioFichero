package index;

import java.util.HashMap;
import java.util.Set;

import modelo.repositorios.Keyable;

public class CreateMap implements Keyable<Long> {
	private HashMap<Long, Boolean> map;


	public CreateMap(Long key, Boolean value) {
		super();
		this.map = map;
		this.map.put(key, value);
	}


	public HashMap<Long, Boolean> getMap() {
		return map;
	}


	public void setMap(HashMap<Long, Boolean> map) {
		this.map = map;
	}
	

	@Override
	public boolean equalKey(Long keyable) {
		return map.containsKey(keyable);
	}


	public Long getKey() {
		return map.keySet().stream().findFirst().orElse(null);
	}

}
