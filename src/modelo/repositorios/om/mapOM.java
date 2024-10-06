package modelo.repositorios.om;

import java.util.ArrayList;
import java.util.List;

import index.CreateMap;
import modelo.repositorios.Populable;

public class mapOM implements Populable<CreateMap> {
	 
	    public static List<CreateMap> createMap() {
	        List<CreateMap> mapa = new ArrayList<>();

	        mapa.add(new CreateMap(1L,true));
	        mapa.add(new CreateMap(2L,true));
	        mapa.add(new CreateMap(3L,true));
	        mapa.add(new CreateMap(4L,true));
	        mapa.add(new CreateMap(5L,true));
	        mapa.add(new CreateMap(6L,true));
	        mapa.add(new CreateMap(7L,true));
	        mapa.add(new CreateMap(8L,true));
	        mapa.add(new CreateMap(9L,true));
	        mapa.add(new CreateMap(10L,true));
	        mapa.add(new CreateMap(11L,true));
	       

	        return mapa;
	    }

		@Override
		public List<CreateMap> getElementos() {
			
			return createMap();
		}
	}

