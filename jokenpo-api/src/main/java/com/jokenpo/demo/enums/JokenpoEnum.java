package com.jokenpo.demo.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static java.util.Arrays.asList;

public enum JokenpoEnum {
	PEDRA(1), PAPEL(2), TESOURA(3);

	private Integer jokenpoId;
	private List<JokenpoEnum> loseTo;

	JokenpoEnum(int jokenpoId) {
		this.jokenpoId = jokenpoId;
	}

	static {
		PEDRA.setLoseTo(asList(PAPEL));
		PAPEL.setLoseTo(asList(TESOURA));
		TESOURA.setLoseTo(asList(PEDRA));
	}

	public int getJokenpoId() {
		return jokenpoId;
	}

	public void setJokenpoId(int jokenpoId) {
		this.jokenpoId = jokenpoId;
	}

	public static Optional<JokenpoEnum> valueOf(int value) {
		return Arrays.stream(values()).filter(jkpo -> jkpo.jokenpoId == value).findFirst();
	}

	public List<JokenpoEnum> getLoseTo() {
		return loseTo;
	}

	public void setLoseTo(List<JokenpoEnum> broke) {
		this.loseTo = broke;
	}

}
