package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DBAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EEductation;

public class EEductationConverter extends DBAttributeConverter<EEductation>
		implements AttributeConverter<EEductation, Integer> {

}
