package br.com.codemain.nutrixpertai.entity;


import br.com.codemain.nutrixpertai.enums.anamnese.*;
import br.com.codemain.nutrixpertai.enums.GoalType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "anamnese")
public class Anamnese {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "anamnese")
    private User user;

    // OBJETIVOS

    @Column
    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    @Column(name = "goal_type_other")
    private String goalTypeOther;

    // PROBLEMAS DE SAÚDE

    @Column
    @Enumerated(EnumType.STRING)
    private HealthConditionType healthConditionType;

    @Column(name = "health_condition_other")
    private String healthConditionOther;

    // ALERGIAS

    @Column
    @Enumerated(EnumType.STRING)
    private AllergyIntoleranceType allergyIntoleranceType;

    @Column(name = "allergy_intolerance_other")
    private String allergyIntoleranceOther;

    // CIRURGIAS

    @Column
    @Enumerated(EnumType.STRING)
    private SurgeryType surgeryType;

    @Column(name = "surgery_type_other")
    private String surgeryTypeOther;

    //  ATIVIDADES FÍSICAS

    @Enumerated(EnumType.STRING)
    private PhysicalActivityType physicalActivityType;

    @Column(name = "physical_activity_other")
    private String physicalActivityOther;

    @Enumerated(EnumType.STRING)
    private PhysicalActivityFrequency physicalActivityFrequency;

    @Enumerated(EnumType.STRING)
    private PhysicalActivityDuration physicalActivityDuration;

    // QUALIDADE DO SONO

    @Column
    @Enumerated(EnumType.STRING)
    private SleepQuality sleepQuality;

    @Column
    @Enumerated(EnumType.STRING)
    private NightAwakeningFrequency nightAwakeningFrequency;

    // EVACUAÇÃO

    @Column
    @Enumerated(EnumType.STRING)
    private EvacuationFrequencyType evacuationFrequencyType;

    // ESTRESSE

    @Column
    @Enumerated(EnumType.STRING)
    private StressLevel stressLevel;

    // CONSUMO DE ÁCLOOL

    @Column
    @Enumerated(EnumType.STRING)
    private AlcoholConsumption alcoholConsumption;

    // TABAGISMO

    @Column
    private Boolean tabagism;

    // HIDRATAÇÃO

    @Column
    @Enumerated(EnumType.STRING)
    private Hydration hydration;

    @Column
    private Boolean continuousMedication;

    public Anamnese() {
    }

    public Anamnese(User user, GoalType goalType, String goalTypeOther,
                    HealthConditionType healthConditionType, AllergyIntoleranceType allergyIntoleranceType,
                    String allergyIntoleranceOther, SurgeryType surgeryType, String surgeryTypeOther,
                    PhysicalActivityType physicalActivityType, PhysicalActivityFrequency physicalActivityFrequency,
                    PhysicalActivityDuration physicalActivityDuration, String physicalActivityOther,
                    String healthConditionOther, SleepQuality sleepQuality,
                    NightAwakeningFrequency nightAwakeningFrequency, EvacuationFrequencyType evacuationFrequencyType,
                    StressLevel stressLevel, Boolean continuousMedication, Hydration hydration,
                    Boolean tabagism, AlcoholConsumption alcoholConsumption) {
        this.user = user;
        this.goalType = goalType;
        this.goalTypeOther = goalTypeOther;
        this.healthConditionType = healthConditionType;
        this.allergyIntoleranceType = allergyIntoleranceType;
        this.allergyIntoleranceOther = allergyIntoleranceOther;
        this.surgeryType = surgeryType;
        this.surgeryTypeOther = surgeryTypeOther;
        this.physicalActivityType = physicalActivityType;
        this.physicalActivityFrequency = physicalActivityFrequency;
        this.physicalActivityDuration = physicalActivityDuration;
        this.physicalActivityOther = physicalActivityOther;
        this.healthConditionOther = healthConditionOther;
        this.sleepQuality = sleepQuality;
        this.nightAwakeningFrequency = nightAwakeningFrequency;
        this.evacuationFrequencyType = evacuationFrequencyType;
        this.stressLevel = stressLevel;
        this.continuousMedication = continuousMedication;
        this.hydration = hydration;
        this.tabagism = tabagism;
        this.alcoholConsumption = alcoholConsumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AllergyIntoleranceType getAllergyIntoleranceType() {
        return allergyIntoleranceType;
    }

    public void setAllergyIntoleranceType(AllergyIntoleranceType allergyIntoleranceType) {
        this.allergyIntoleranceType = allergyIntoleranceType;
    }

    public String getHealthConditionOther() {
        return healthConditionOther;
    }

    public void setHealthConditionOther(String healthConditionOther) {
        this.healthConditionOther = healthConditionOther;
    }

    public HealthConditionType getHealthConditionType() {
        return healthConditionType;
    }

    public void setHealthConditionType(HealthConditionType healthConditionType) {
        this.healthConditionType = healthConditionType;
    }

    public String getGoalTypeOther() {
        return goalTypeOther;
    }

    public void setGoalTypeOther(String goalTypeOther) {
        this.goalTypeOther = goalTypeOther;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public String getAllergyIntoleranceOther() {
        return allergyIntoleranceOther;
    }

    public void setAllergyIntoleranceOther(String allergyIntoleranceOther) {
        this.allergyIntoleranceOther = allergyIntoleranceOther;
    }

    public SurgeryType getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(SurgeryType surgeryType) {
        this.surgeryType = surgeryType;
    }

    public String getSurgeryTypeOther() {
        return surgeryTypeOther;
    }

    public void setSurgeryTypeOther(String surgeryTypeOther) {
        this.surgeryTypeOther = surgeryTypeOther;
    }

    public PhysicalActivityType getPhysicalActivityType() {
        return physicalActivityType;
    }

    public void setPhysicalActivityType(PhysicalActivityType physicalActivityType) {
        this.physicalActivityType = physicalActivityType;
    }

    public String getPhysicalActivityOther() {
        return physicalActivityOther;
    }

    public void setPhysicalActivityOther(String physicalActivityOther) {
        this.physicalActivityOther = physicalActivityOther;
    }

    public PhysicalActivityFrequency getPhysicalActivityFrequency() {
        return physicalActivityFrequency;
    }

    public void setPhysicalActivityFrequency(PhysicalActivityFrequency physicalActivityFrequency) {
        this.physicalActivityFrequency = physicalActivityFrequency;
    }

    public PhysicalActivityDuration getPhysicalActivityDuration() {
        return physicalActivityDuration;
    }

    public void setPhysicalActivityDuration(PhysicalActivityDuration physicalActivityDuration) {
        this.physicalActivityDuration = physicalActivityDuration;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(SleepQuality sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public NightAwakeningFrequency getNightAwakeningFrequency() {
        return nightAwakeningFrequency;
    }

    public void setNightAwakeningFrequency(NightAwakeningFrequency nightAwakeningFrequency) {
        this.nightAwakeningFrequency = nightAwakeningFrequency;
    }

    public EvacuationFrequencyType getEvacuationFrequencyType() {
        return evacuationFrequencyType;
    }

    public void setEvacuationFrequencyType(EvacuationFrequencyType evacuationFrequencyType) {
        this.evacuationFrequencyType = evacuationFrequencyType;
    }

    public StressLevel getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(StressLevel stressLevel) {
        this.stressLevel = stressLevel;
    }

    public AlcoholConsumption getAlcoholConsumption() {
        return alcoholConsumption;
    }

    public void setAlcoholConsumption(AlcoholConsumption alcoholConsumption) {
        this.alcoholConsumption = alcoholConsumption;
    }

    public Boolean getTabagism() {
        return tabagism;
    }

    public void setTabagism(Boolean tabagism) {
        this.tabagism = tabagism;
    }

    public Hydration getHydration() {
        return hydration;
    }

    public void setHydration(Hydration hydration) {
        this.hydration = hydration;
    }

    public Boolean getContinuousMedication() {
        return continuousMedication;
    }

    public void setContinuousMedication(Boolean continuousMedication) {
        this.continuousMedication = continuousMedication;
    }
}
