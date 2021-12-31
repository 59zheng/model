package agent.place;

/**
 * @author suya
 *政府
 */
public class Government {
	/**
	 * 政府名称
	 */
	private String governmentName = "TaiYuan";
	/**
	 * 防控措施1 是否限制出行  1是
	 */
	private int isTravelTestrictions;
	/**
	 * 当前防控等级 0是低风险 1是中风险 2是高风险对应不同响应级别
	 */
	public int controlLevel;
	/**
	 * 防控措施2 是否严查佩戴口罩
	 */
	private int isCheckWearMask;
	/**
	 * 防控措施3 是否集体核酸检测
	 */
	private int isNAT;
	
	

	
	
	
	
	public Government() {
		super();
	}


	public int getIsNAT() {
		return isNAT;
	}
	public void setIsNAT(int isNAT) {
		this.isNAT = isNAT;
	}
	public String getGovernmentName() {
		return governmentName;
	}
	public void setGovernmentName(String governmentName) {
		this.governmentName = governmentName;
	}
	public int getIsTravelTestrictions() {
		return isTravelTestrictions;
	}
	public void setIsTravelTestrictions(int isTravelTestrictions) {
		this.isTravelTestrictions = isTravelTestrictions;
	}
	public int getControlLevel() {
		return controlLevel;
	}
	public void setControlLevel(int controlLevel) {
		this.controlLevel = controlLevel;
	}
	public int getIsCheckWearMask() {
		return isCheckWearMask;
	}
	public void setIsCheckWearMask(int isCheckWearMask) {
		this.isCheckWearMask = isCheckWearMask;
	}
 
	

}
