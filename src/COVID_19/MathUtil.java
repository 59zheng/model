package COVID_19;

import java.util.Random;

import repast.simphony.relogo.ide.dynamics.NetLogoSystemDynamicsParser.intg_return;

import java.math.BigDecimal;

/**
 * 数学算法工具类
 */
public class MathUtil {
	/**
	 * 仅仅使用一个随机数生成器
	 */
	private static final Random randomGen = new Random();

	/**
	 * 标准正态分布化
	 * <p>
	 * 流动意愿标准化后判断是在0的左边还是右边从而决定是否流动。
	 * <p>
	 * 设X随机变量为服从正态分布，sigma是影响分布形态的系数 u值决定正态分布均值
	 * <p>
	 * <p>
	 * 推导： StdX = (X-u)/sigma X = sigma * StdX + u
	 *
	 * @param sigma
	 *            正态标准差sigma值 方差计算公式，每个值减去均值的平方除以n
	 * @param u
	 *            正态均值参数mu
	 * @return
	 */
	public static double stdGaussian(double sigma, double u) {
		// 生成的总是负值
		double X = randomGen.nextGaussian();
		return -(sigma * X + u);
	}

 
	public static Boolean randomSelect(double probability) {
		// 生成的总是负值
		float ratenum = randomGen.nextFloat();

		if (ratenum <= probability) {

			return true;
		} else {
			return false;
		}
	}
	
	
//	随机方差
	public static double getSig() {
//		Random	 random=	new Random();
//		 int i=	random.nextInt((int)SystemParameter.sigMax-(int)SystemParameter.sigMin);
		 
		return SystemParameter.sig;
	}
	
}
