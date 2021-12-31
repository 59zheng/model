package styles;

import java.awt.Color;

import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualizationOGL2D.ValueLayerStyleOGL;

/**
 * 0-9999
 * 
 * @author QinShipeng
 *
 */
public class StreetDiagramStyle implements ValueLayerStyleOGL {

	private ValueLayer valueLayer;

	@Override
	public Color getColor(double... coordinates) {

		int index = (int) valueLayer.get(coordinates);

		switch (index) {
		case 0:
			return new Color(153, 204, 255);//天蓝
		case 1:
			return new Color(204, 255, 153);//草绿
		case 2:
			return new Color(255, 204, 204);//淡粉
		case 3:
			return new Color(255, 255, 204);//浅黄
		case 4:
			return new Color(204, 204, 255);//浅紫
		case 5:
			return new Color(255, 204, 153);//浅橙
		case 6:
			return new Color(102, 204, 153);//绿色
		case 7:
			return new Color(204, 204, 204);//灰色
		case 8:
			return new Color(204, 255, 255);//浅蓝
		case 9:
			return new Color(255, 153, 204);//深粉
		case 10:
			return new Color(255, 255,0);//亮黄
		case 90:
			return new Color(255, 255,0);//亮黄
			
		}
		return Color.WHITE;
	}

	@Override
	public float getCellSize() {
		return 1.0f;
	}

	@Override
	public void init(ValueLayer layer) {
		this.valueLayer = layer;
	}

}
