package gs.service;

import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

public class Geocoder {

	public double[] proj4(String[] point) {
		
		CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
		CRSFactory csFactory = new CRSFactory();
		
		CoordinateReferenceSystem fromRef = csFactory.createFromParameters("EPSG:5181", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs");
		// projection1 : *중부원점(GRS80)-falseY:60000
		//EPSG:5186
		//+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs

		CoordinateReferenceSystem toRef = csFactory.createFromParameters("EPSG:4019", "+proj=longlat +ellps=GRS80 +no_defs");
		// projection2 : *GRS80 경위도
		// EPSG:4019, EPSG:4737 (Korean 2000)
		// +proj=longlat +ellps=GRS80 +no_defs

		CoordinateTransform trans = ctFactory.createTransform(fromRef, toRef);
		
		ProjCoordinate fromPoint = new ProjCoordinate();
		fromPoint.x = Double.parseDouble(point[0]);
		fromPoint.y = Double.parseDouble(point[1]);
		
		ProjCoordinate toPoint = new ProjCoordinate();
		trans.transform(fromPoint, toPoint);

		double[] result = {toPoint.x, toPoint.y};
		
		return result;
	}
}
