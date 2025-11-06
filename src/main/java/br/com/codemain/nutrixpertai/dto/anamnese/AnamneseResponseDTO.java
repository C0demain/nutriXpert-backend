package br.com.codemain.nutrixpertai.dto.anamnese;

import br.com.codemain.nutrixpertai.enums.GoalType;
import br.com.codemain.nutrixpertai.enums.anamnese.*;

import java.util.UUID;

public record AnamneseResponseDTO(
        UUID id,
        GoalType goalType,
        String goalTypeOther,
        HealthConditionType healthConditionType,
        String healthConditionOther,
        AllergyIntoleranceType allergyIntoleranceType,
        String allergyIntoleranceOther,
        SurgeryType surgeryType,
        String surgeryTypeOther,
        PhysicalActivityType physicalActivityType,
        String physicalActivityOther,
        PhysicalActivityFrequency physicalActivityFrequency,
        PhysicalActivityDuration physicalActivityDuration,
        SleepQuality sleepQuality,
        NightAwakeningFrequency nightAwakeningFrequency,
        EvacuationFrequencyType evacuationFrequencyType,
        StressLevel stressLevel,
        AlcoholConsumption alcoholConsumption,
        Boolean tabagism,
        Hydration hydration,
        Boolean continuousMedication
) {
}
