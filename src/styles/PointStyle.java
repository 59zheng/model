package styles;

import java.awt.Color;

import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualizationOGL2D.ValueLayerStyleOGL;

/**
 * 0-9999
 * 
 * @author 
 *
 */
public class PointStyle implements ValueLayerStyleOGL {

	private ValueLayer valueLayer;

	@Override
	public Color getColor(double... coordinates) {

		double livability = valueLayer.get(coordinates);
		int index = 0;// 索引

		if (livability == -9999) {
			index = 0;
		} else if (livability >= 0 && livability <= 0.1) {
			index = 1;
		} else if (livability > 0.1 && livability <= 0.2) {
			index = 2;
		} else if (livability > 0.2 && livability <= 0.3) {
			index = 3;
		} else if (livability > 0.3 && livability <= 0.4) {
			index = 4;
		} else if (livability > 0.4 && livability <= 0.5) {
			index = 5;
		} else if (livability > 0.5 && livability <= 0.6) {
			index = 6;
		} else if (livability > 0.6 && livability <= 0.7) {
			index = 7;
		} else if (livability > 0.7 && livability <= 0.8) {
			index = 8;
		} else if (livability > 0.8 && livability <= 0.9) {
			index = 9;
		} else if (livability > 0.9) {
			index = 10;
		} else {
			index = 1;
		}

		switch (index) {
		case 0:
			return Color.BLACK;// 黑色
		case 1:
			return new Color(180, 255, 180);
		case 2:
			return new Color(0, 255, 0);
		case 3:
			return new Color(51, 153, 255);
		case 4:
			return new Color(1, 0, 252);
		case 5:
			return new Color(255, 255, 0);
		case 6:
			return new Color(255, 153, 51);
		case 7:
			return new Color(255, 153, 255);
		case 8:
			return new Color(253, 3, 189);
		case 9:
			return new Color(153, 51, 255);
		case 10:
			return new Color(204, 0, 0);
		}
		return Color.WHITE;
	}

	@Override
	public float getCellSize() {
		return 15.0f;
	}

	@Override
	public void init(ValueLayer layer) {
		this.valueLayer = layer;
	}

}
