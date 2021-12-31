package styles;

import java.awt.Color;

import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualizationOGL2D.ValueLayerStyleOGL;

public class ColorStyle implements ValueLayerStyleOGL{
	private ValueLayer valueLayer;

	@Override
	public Color getColor(double... coordinates) {

		double livability = valueLayer.get(coordinates);
		int index = 0;// 索引

		if (livability == -9999) {
			index = 0;
		} else if (livability ==1) {
			index = 1;
		} else if (livability ==2) {
			index = 2;
		} else if (livability ==3) {
			index = 3;
		} else if (livability ==4) {
			index = 4;
		} else if (livability ==5) {
			index = 5;
		} else if (livability ==6) {
			index = 6;
		} else if (livability ==7) {
			index = 7;
		} else if (livability ==8) {
			index = 8;
		} else if (livability ==9) {
			index = 9;
		} else if (livability ==0) {
			index = 10;
		} else {
			index = 1;
		}

		switch (index) {
		case 0:
			return Color.WHITE;//天蓝
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
			return new Color(204, 204, 204);//灰色
		case 7:
			return new Color(102, 204, 153);//绿色
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
		return 15.0f;
	}

	@Override
	public void init(ValueLayer layer) {
		this.valueLayer = layer;
	}
}
